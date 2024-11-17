package tcatelie.microservice.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.filter.ProdutoFiltroDTO;
import tcatelie.microservice.auth.dto.request.MaterialProdutoRequestDTO;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.request.ProdutosUpdateRequestDTO;
import tcatelie.microservice.auth.dto.response.MaterialProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.MercadoLivreProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.mapper.ImagensAdicionaisMapper;
import tcatelie.microservice.auth.mapper.OpcaoPersonalizacaoMapper;
import tcatelie.microservice.auth.mapper.PersonalizacaoMapper;
import tcatelie.microservice.auth.mapper.ProdutoMapper;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.observer.EmailNotificacao;
import tcatelie.microservice.auth.observer.Observer;
import tcatelie.microservice.auth.observer.ProdutoObserver;
import tcatelie.microservice.auth.repository.ImagensProdutoRepository;
import tcatelie.microservice.auth.repository.OpcaoPersonalizacaoRepository;
import tcatelie.microservice.auth.repository.PersonalizacaoRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.specification.ProdutoSpecification;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoMapper mapper;
    private final ImagensAdicionaisMapper imagensAdicionaisMapper;
    private final PersonalizacaoMapper personalizacaoMapper;
    private final OpcaoPersonalizacaoMapper opcaoPersonalizacaoMapper;
    private final ProdutoRepository repository;
    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;
    private final MaterialService materialService;
    private final ImagensProdutoRepository imagensProdutoRepository;
    private final PersonalizacaoRepository personalizacaoRepository;
    private final OpcaoPersonalizacaoRepository opcaoPersonalizacaoRepository;
    private final GoogleDriveApiService googleDriveApiService;
    private final ProdutoObserver produtoObserver;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO requestDTO) {
        validarRequest(requestDTO);
        verificarExistencia(requestDTO.getNome(), requestDTO.getSku());
        logger.info(requestDTO.toString());

        Produto produto = criarProdutoComRelacoes(requestDTO);
        produto.setProdutoAtivo(true);
        Produto produtoSalvo = repository.save(produto);


        Observer emailNotificacao = new EmailNotificacao("claudio.araujofo@sptech.school", emailService);
        produtoObserver.addObserver(emailNotificacao);

        produtoObserver.cadastrarProduto("Um novo produto foi cadastrado com sucesso.", produtoSalvo);

        return montarProdutoResponseDTO(produtoSalvo, requestDTO.getMateriais());
    }

    private void validarRequest(ProdutoRequestDTO requestDTO) {
        if (requestDTO == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O corpo da requisição não foi informado");
    }

    private void verificarExistencia(String nome, String sku) {
        if (repository.existsByNome(nome))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto já cadastrado com esse nome.");
        if (repository.existsBySku(sku))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto já cadastrado com esse sku.");
    }

    private Produto criarProdutoComRelacoes(ProdutoRequestDTO requestDTO) {
        Categoria categoria = categoriaService.findByNome(requestDTO.getCategoria().getNome());
        Subcategoria subcategoria = subcategoriaService.findByNome(requestDTO.getSubcategoria().getNomeSubcategoria());
        List<Material> materiais = obterMateriais(requestDTO.getMateriais());

        Produto produto = mapper.toProduto(requestDTO);
        produto.setCategoria(categoria);
        produto.setSubcategoria(subcategoria);
        produto.setImagensAdicionais(requestDTO.getImagensAdicionais().stream()
                .map(img -> new ImagensProduto(img.getUrl())).collect(Collectors.toList()));
        produto.setMateriaisProduto(montarMateriaisProduto(requestDTO.getMateriais(), materiais, produto));

        List<Personalizacao> listaPersonalizacoes = requestDTO.getPersonalizacoes().stream()
                .map(personalizacaoMapper::toPersonalizacaoWithOpcoes)
                .collect(Collectors.toList());

        listaPersonalizacoes.forEach(p -> {
            p.setProduto(produto);
            p.getOpcoes().forEach(o -> o.setPersonalizacao(p));
        });

        produto.setPersonalizacoes(listaPersonalizacoes);

        produto.getImagensAdicionais().forEach(img -> img.setProduto(produto));
        return produto;
    }

    private List<Material> obterMateriais(List<MaterialProdutoRequestDTO> materialRequests) {
        return materialRequests.stream()
                .map(m -> materialService.findById(m.getIdMaterial()))
                .collect(Collectors.toList());
    }

    private List<MaterialProduto> montarMateriaisProduto(List<MaterialProdutoRequestDTO> materialRequests, List<Material> materiais, Produto produto) {
        return materialRequests.stream()
                .map(m -> new MaterialProduto(materiais.stream()
                        .filter(mat -> mat.getIdMaterial().equals(m.getIdMaterial()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material não encontrado com ID: " + m.getIdMaterial())),
                        produto, m.getQtdMaterialNecessaria()))
                .collect(Collectors.toList());
    }

    private ProdutoResponseDTO montarProdutoResponseDTO(Produto produto, List<MaterialProdutoRequestDTO> materialRequests) {
        ProdutoResponseDTO produtoResponseDTO = mapper.toResponseDTO(produto);
        List<MaterialProdutoResponseDTO> materiais = produto.getMateriaisProduto().stream()
                .map(mp -> criarMaterialProdutoResponseDTO(mp.getMaterial(), materialRequests))
                .collect(Collectors.toList());
        produtoResponseDTO.setMateriais(materiais);
        produtoResponseDTO.setPrecoProducao(calcularPrecoTotalProducao(materiais));
        return produtoResponseDTO;
    }

    private MaterialProdutoResponseDTO criarMaterialProdutoResponseDTO(Material material, List<MaterialProdutoRequestDTO> materialRequests) {
        if (materialRequests == null || materialRequests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista de materialRequests não pode estar vazia ou nula.");
        }

        MaterialProdutoRequestDTO request = materialRequests.stream()
                .filter(req -> req.getIdMaterial().equals(material.getIdMaterial()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material não encontrado com ID: " + material.getIdMaterial()));

        double custoProducao = request.getQtdMaterialNecessaria() * material.getPrecoUnitario();
        return new MaterialProdutoResponseDTO(
                request.getQtdMaterialNecessaria(),
                custoProducao,
                material.getIdMaterial(),
                material.getNomeMaterial(),
                material.getEstoque(),
                material.getPrecoUnitario()
        );
    }

    private double calcularPrecoTotalProducao(List<MaterialProdutoResponseDTO> materiais) {
        return materiais.stream().mapToDouble(MaterialProdutoResponseDTO::getCustoProducao).sum();
    }

    public ProdutoResponseDTO buscarProdutoPorId(Integer id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com o ID: " + id));

        produto.setPersonalizacoes(produto.getPersonalizacoes().stream()
                .filter(Personalizacao::getPersonalizacaoAtiva)
                .collect(Collectors.toList()));

        return montarProdutoResponseDTO(produto, produto.getMateriaisProduto().stream()
                .map(mp -> new MaterialProdutoRequestDTO(mp.getMaterial().getIdMaterial(), mp.getQtdMaterialNecessario()))
                .collect(Collectors.toList()));
    }


    public Page<ProdutoResponseDTO> buscarTodosProdutosPaginados(Pageable pageable, ProdutoFiltroDTO produtoFiltroDTO) {
        Page<Produto> produtos = repository.findAll(ProdutoSpecification.filtrar(produtoFiltroDTO), pageable);

        return produtos.map(produto ->
                montarProdutoResponseDTO(produto, produto.getMateriaisProduto().stream()
                        .map(mp -> new MaterialProdutoRequestDTO(mp.getMaterial().getIdMaterial(), mp.getQtdMaterialNecessario()))
                        .collect(Collectors.toList()))
        );
    }

    public ProdutoResponseDTO atualizarProduto(Integer idProduto, ProdutoRequestDTO requestDTO) throws IOException {
        Produto produtoExistente = repository.findById(idProduto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com o ID: " + idProduto));

        validarRequest(requestDTO);

        if (repository.findByNomeAndIdNot(requestDTO.getNome(), idProduto).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto já cadastrado com esse nome.");
        }
        if (repository.findBySkuAndIdNot(requestDTO.getSku(), idProduto).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto já cadastrado com esse sku.");
        }

        List<String> filesIds = new ArrayList<>();

        produtoExistente.setNome(requestDTO.getNome());
        produtoExistente.setDescricao(requestDTO.getDescricao());
        produtoExistente.setSku(requestDTO.getSku());
        produtoExistente.setPreco(requestDTO.getPrecoVenda());
        produtoExistente.setDesconto(requestDTO.getDesconto());
        produtoExistente.setMargemLucro(requestDTO.getMargemLucro());
        produtoExistente.setPersonalizavel(requestDTO.getIsPersonalizavel());
        produtoExistente.setDimensao(requestDTO.getDimensao());
        produtoExistente.setPeso(requestDTO.getPeso());

        if (!produtoExistente.getUrlImagemPrincipal().equals(requestDTO.getUrlProduto())) {
            filesIds.add(produtoExistente.getIdImgDrive());
            produtoExistente.setUrlImagemPrincipal(requestDTO.getUrlProduto());
        }

        Categoria categoria = categoriaService.findByNome(requestDTO.getCategoria().getNome());
        Subcategoria subcategoria = subcategoriaService.findByNome(requestDTO.getSubcategoria().getNomeSubcategoria());

        produtoExistente.setCategoria(categoria);
        produtoExistente.setSubcategoria(subcategoria);


        List<ImagensProduto> imagensExistentes = produtoExistente.getImagensAdicionais();
        List<ImagensProduto> imagensRecebidas = requestDTO.getImagensAdicionais().stream()
                .map(imagensAdicionaisMapper::toModel)
                .collect(Collectors.toList());

        List<ImagensProduto> imagensParaRemover = imagensExistentes.stream()
                .filter(imgExistente -> imagensRecebidas.stream()
                        .noneMatch(imgRecebida -> imgRecebida.getIdImagem() != null
                                && imgRecebida.getIdImagem().equals(imgExistente.getIdImagem())))
                .collect(Collectors.toList());

        imagensParaRemover.stream().forEach(img -> filesIds.add(img.getIdImgDrive()));

        produtoExistente.getImagensAdicionais().removeIf(img ->
                imagensParaRemover.stream().anyMatch(i -> i.getIdImagem().equals(img.getIdImagem())));

        imagensRecebidas.forEach(img -> {
            if (img.getIdImagem() == null) {
                img.setProduto(produtoExistente);
                produtoExistente.getImagensAdicionais().add(img);
            }
        });

        List<Personalizacao> personalizacoesExistentes = produtoExistente.getPersonalizacoes();
        List<Personalizacao> personalizacoesRecebidas = requestDTO.getPersonalizacoes().stream()
                .map(personalizacaoMapper::toPersonalizacaoWithOpcoes)
                .collect(Collectors.toList());

        personalizacoesExistentes.forEach(pExistente -> {
            boolean aindaAtiva = personalizacoesRecebidas.stream()
                    .anyMatch(pRecebida -> pRecebida.getIdPersonalizacao() != null
                            && pRecebida.getIdPersonalizacao().equals(pExistente.getIdPersonalizacao()));
            pExistente.setPersonalizacaoAtiva(aindaAtiva);
        });

        personalizacoesRecebidas.forEach(p -> {
            if (p.getIdPersonalizacao() == null) {
                p.setProduto(produtoExistente);
                p.setPersonalizacaoAtiva(true);
                produtoExistente.getPersonalizacoes().add(p);
            } else {
                Personalizacao personalizacaoExistente = personalizacaoRepository.findById(p.getIdPersonalizacao())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personalização não encontrada com ID: " + p.getIdPersonalizacao()));

                personalizacaoExistente.setNomePersonalizacao(p.getNomePersonalizacao());
                personalizacaoExistente.setTipoPersonalizacao(p.getTipoPersonalizacao());
                personalizacaoExistente.setPersonalizacaoAtiva(true); // Certifica-se de que ela está ativa

                List<OpcaoPersonalizacao> opcoesExistentes = personalizacaoExistente.getOpcoes();
                List<OpcaoPersonalizacao> opcoesRecebidas = p.getOpcoes();

                opcoesExistentes.forEach(oExistente -> {
                    boolean opcaoAindaAtiva = opcoesRecebidas.stream()
                            .anyMatch(oRecebida -> oRecebida.getIdOpcaoPersonalizacao() != null
                                    && oRecebida.getIdOpcaoPersonalizacao().equals(oExistente.getIdOpcaoPersonalizacao()));
                    if (!opcaoAindaAtiva) {
                        filesIds.add(oExistente.getIdImgDrive());
                    }
                });
                opcoesExistentes.removeIf(o -> opcoesRecebidas.stream()
                        .noneMatch(oRecebida -> oRecebida.getIdOpcaoPersonalizacao() != null
                                && oRecebida.getIdOpcaoPersonalizacao().equals(o.getIdOpcaoPersonalizacao())));

                opcoesRecebidas.forEach(o -> {
                    if (o.getIdOpcaoPersonalizacao() == null) {
                        personalizacaoExistente.getOpcoes().add(o);
                    } else {
                        OpcaoPersonalizacao opcaoExistente = opcoesExistentes.stream()
                                .filter(op -> op.getIdOpcaoPersonalizacao().equals(o.getIdOpcaoPersonalizacao()))
                                .findFirst()
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opção de personalização não encontrada com ID: " + o.getIdOpcaoPersonalizacao()));

                        opcaoExistente.setNomeOpcao(o.getNomeOpcao());
                        opcaoExistente.setDescricao(o.getDescricao());
                        opcaoExistente.setUrlImagemOpcao(o.getUrlImagemOpcao());
                        opcaoExistente.setAcrescimoOpcao(o.getAcrescimoOpcao());
                    }
                });
            }
        });


        atualizarMateriais(produtoExistente, requestDTO.getMateriais());

        Produto produtoAtualizado = repository.save(produtoExistente);

        return montarProdutoResponseDTO(produtoAtualizado, requestDTO.getMateriais());
    }


    private void atualizarMateriais(Produto produtoExistente, List<MaterialProdutoRequestDTO> materiaisRecebidos) {
        List<MaterialProduto> materiaisExistentes = produtoExistente.getMateriaisProduto();

        List<MaterialProduto> novosMateriaisProduto = materiaisRecebidos.stream()
                .map(materialDTO -> {
                    Material material = materialService.findById(materialDTO.getIdMaterial());
                    return new MaterialProduto(material, produtoExistente, materialDTO.getQtdMaterialNecessaria());
                })
                .collect(Collectors.toList());

        materiaisExistentes.removeIf(materialExistente ->
                novosMateriaisProduto.stream()
                        .noneMatch(novoMaterial -> novoMaterial
                                .getMaterial()
                                .getIdMaterial()
                                .equals(materialExistente
                                        .getMaterial()
                                        .getIdMaterial()))
        );

        for (MaterialProduto novoMaterial : novosMateriaisProduto) {
            Optional<MaterialProduto> existenteOpt = materiaisExistentes.stream()
                    .filter(existente -> existente.getMaterial().getIdMaterial().equals(novoMaterial.getMaterial().getIdMaterial()))
                    .findFirst();

            if (existenteOpt.isPresent()) {
                MaterialProduto existente = existenteOpt.get();
                existente.setQtdMaterialNecessario(novoMaterial.getQtdMaterialNecessario());
            } else {
                produtoExistente.getMateriaisProduto().add(novoMaterial);
            }
        }
    }

    public ProdutoResponseDTO desativarProduto(Integer idProduto) {
        Produto produto = repository.findById(idProduto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com o ID: " + idProduto));
        produto.setProdutoAtivo(false);
        Produto produtoDesativado = repository.save(produto);
        return montarProdutoResponseDTO(produtoDesativado, produto.getMateriaisProduto().stream()
                .map(mp -> new MaterialProdutoRequestDTO(mp.getMaterial().getIdMaterial(), mp.getQtdMaterialNecessario()))
                .collect(Collectors.toList()));
    }

    public MercadoLivreProdutoResponseDTO[] ordenarProdutosMercadoLivrePrecoDecrescente() throws Exception {
        String urlApiExterna = "https://api.mercadolibre.com/sites/MLB/search?q=produtos-personalizados";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(urlApiExterna, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response.getBody());

        List<MercadoLivreProdutoResponseDTO> produtos = new ArrayList<>();

        JsonNode resultsArray = jsonResponse.get("results");
        if (resultsArray != null && resultsArray.isArray()) {
            for (JsonNode produtoNode : resultsArray) {
                MercadoLivreProdutoResponseDTO produto = new MercadoLivreProdutoResponseDTO();

                produto.setId(produtoNode.get("id").asText());
                produto.setTitle(produtoNode.get("title").asText());
                produto.setCategoryId(produtoNode.get("category_id").asText());
                produto.setCondition(produtoNode.get("condition").asText());
                produto.setPrice(BigDecimal.valueOf(produtoNode.get("price").asDouble()));
                produto.setCurrencyId(produtoNode.get("currency_id").asText());
                produto.setThumbnail(produtoNode.get("thumbnail").asText());
                produto.setPermalink(produtoNode.get("permalink").asText());
                produto.setAvailableQuantity(produtoNode.get("available_quantity").asInt());

                produtos.add(produto);
            }
        }

        MercadoLivreProdutoResponseDTO[] v = new MercadoLivreProdutoResponseDTO[produtos.toArray().length];
        produtos.toArray(v);
        for (int i = 0; i < v.length - 1; i++) {
            for (int j = i + 1; j < v.length; j++) {
                if (v[j].getPrice().compareTo(v[i].getPrice()) == 1) {
                    MercadoLivreProdutoResponseDTO temp = v[i];
                    v[i] = v[j];
                    v[j] = temp;
                }
            }
        }
        return v;
    }

    public void exportarCSVListaProdutos(String filePath) {
        List<Produto> listaProdutos = repository.findAll();

        FileWriter file = null;
        Formatter saida = null;


        try {
            file = new FileWriter(filePath);
            saida = new Formatter(file);

            saida.format("%s;%s;%s;%s;%s;%s\n", "id", "nome", "preço", "dimensão", "categoria", "subcategoria");

            for (Produto produto : listaProdutos) {
                saida.format("%d;%s;%.2f;%s;%s;%s\n", produto.getId(), produto.getNome(), produto.getPreco(), produto.getDimensao(), produto.getCategoria().getNomeCategoria(), produto.getSubcategoria().getNomeSubcategoria());
            }
        } catch (Exception e) {
            logger.error("Erro ao gravar o arquivo");
        } finally {
            saida.close();
            try {
                file.close();
            } catch (IOException e) {
                logger.error("Erro ao fechar o arquivo");
            }
        }

    }

    public ProdutoResponseDTO buscarProdutoPorNomePesquisaBinaria(String nome) {
        List<Produto> produtos = repository.findAll();

        produtos.sort(Comparator.comparing(Produto::getNome));

        return buscarProdutoRecursivo(produtos, nome, 0, produtos.size() - 1);
    }

    private ProdutoResponseDTO buscarProdutoRecursivo(List<Produto> produtos, String nome, int inicio, int fim) {
        if (inicio > fim) {
            return null;
        }

        int meio = (inicio + fim) / 2;
        Produto produtoMeio = produtos.get(meio);

        int comparacao = produtoMeio.getNome().compareTo(nome);

        if (comparacao == 0) {
            return mapper.toResponseDTO(produtoMeio);
        } else if (comparacao < 0) {
            return buscarProdutoRecursivo(produtos, nome, meio + 1, fim);
        } else {
            return buscarProdutoRecursivo(produtos, nome, inicio, meio - 1);
        }
    }

    public void atualizarCategoriaSubcategoriaDoProduto(ProdutosUpdateRequestDTO dto) {

        List<Produto> produtos = new ArrayList<>();
        if (dto.getNomesProdutos().size() == 1 & dto.getNomesProdutos().get(0).equals("TODOS")) {
            produtos = repository.findByCategoria_NomeCategoria(dto.getCategoriaAntiga());
        } else {
            produtos = repository.findAllByNomeIn(dto.getNomesProdutos());
        }

        Categoria categoria = categoriaService.findByNome(dto.getNomeCategoria());
        Subcategoria subcategoria = subcategoriaService.findByNome(dto.getNomeSubcategoria());

        produtos.forEach(produto -> {
            produto.setCategoria(categoria);
            produto.setSubcategoria(subcategoria);
        });

        repository.saveAll(produtos);
    }

    public Page<ProdutoResponseDTO> buscarProdutosPorIdMaterial(Integer idMaterial, Pageable pageable) {
        Page<Produto> produtos = repository.findByMateriaisProduto_Material_IdMaterial(idMaterial, pageable);
        return produtos.map(produto -> montarProdutoResponseDTO(produto, produto.getMateriaisProduto().stream()
                .map(mp -> new MaterialProdutoRequestDTO(mp.getMaterial().getIdMaterial(), mp.getQtdMaterialNecessario()))
                .collect(Collectors.toList())));
    }
}
