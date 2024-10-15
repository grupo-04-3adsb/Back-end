package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.filter.ProdutoFiltroDTO;
import tcatelie.microservice.auth.dto.request.MaterialProdutoRequestDTO;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.MaterialProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.mapper.ImagensAdicionaisMapper;
import tcatelie.microservice.auth.mapper.OpcaoPersonalizacaoMapper;
import tcatelie.microservice.auth.mapper.PersonalizacaoMapper;
import tcatelie.microservice.auth.mapper.ProdutoMapper;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.ImagensProdutoRepository;
import tcatelie.microservice.auth.repository.OpcaoPersonalizacaoRepository;
import tcatelie.microservice.auth.repository.PersonalizacaoRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.specification.ProdutoSpecification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO requestDTO) {
        validarRequest(requestDTO);
        verificarExistencia(requestDTO.getNome(), requestDTO.getSku());
        logger.info(requestDTO.toString());

        Produto produto = criarProdutoComRelacoes(requestDTO);
        Produto produtoSalvo = repository.save(produto);
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

        List<String> filesIds = new ArrayList<>();

        produtoExistente.setNome(requestDTO.getNome());
        produtoExistente.setDescricao(requestDTO.getDescricao());
        produtoExistente.setSku(requestDTO.getSku());
        produtoExistente.setPreco(requestDTO.getPrecoVenda());
        produtoExistente.setDesconto(requestDTO.getDesconto());
        produtoExistente.setMargemLucro(requestDTO.getMargemLucro());
        produtoExistente.setPersonalizavel(requestDTO.getIsPersonalizavel());
        produtoExistente.setDimensao(requestDTO.getDimensao());

        if(!produtoExistente.getUrlImagemPrincipal().equals(requestDTO.getUrlProduto())) {
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

        List<Personalizacao> personalizacoesParaRemover = personalizacoesExistentes.stream()
                .filter(pExistente -> personalizacoesRecebidas.stream()
                        .noneMatch(pRecebida -> pRecebida.getIdPersonalizacao() != null
                                && pRecebida.getIdPersonalizacao().equals(pExistente.getIdPersonalizacao())))
                .collect(Collectors.toList());

        personalizacoesParaRemover.forEach(p -> p.getOpcoes().forEach(o -> filesIds.add(o.getIdImgDrive())));
        produtoExistente.getPersonalizacoes().removeAll(personalizacoesParaRemover);
        personalizacoesRecebidas.forEach(p -> {
            if (p.getIdPersonalizacao() == null) {
                p.setProduto(produtoExistente);
                produtoExistente.getPersonalizacoes().add(p);
            } else {
                Personalizacao personalizacaoExistente = personalizacaoRepository.findById(p.getIdPersonalizacao())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personalização não encontrada com ID: " + p.getIdPersonalizacao()));

                personalizacaoExistente.setNomePersonalizacao(p.getNomePersonalizacao());
                personalizacaoExistente.setTipoPersonalizacao(p.getTipoPersonalizacao());

                List<OpcaoPersonalizacao> opcoesExistentes = personalizacaoExistente.getOpcoes();
                List<OpcaoPersonalizacao> opcoesRecebidas = p.getOpcoes();

                List<OpcaoPersonalizacao> opcoesParaRemover = opcoesExistentes.stream()
                        .filter(oExistente -> opcoesRecebidas.stream()
                                .noneMatch(oRecebida -> oRecebida.getIdOpcaoPersonalizacao() != null
                                        && oRecebida.getIdOpcaoPersonalizacao().equals(oExistente.getIdOpcaoPersonalizacao())))
                        .collect(Collectors.toList());

                opcoesParaRemover.forEach(o -> filesIds.add(o.getIdImgDrive()));
                personalizacaoExistente.getOpcoes().removeAll(opcoesParaRemover);

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

    public ProdutoResponseDTO desativarProduto(Integer idProduto){
        Produto produto = repository.findById(idProduto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com o ID: " + idProduto));
        produto.setProdutoAtivo(false);
        Produto produtoDesativado = repository.save(produto);
        return montarProdutoResponseDTO(produtoDesativado, produto.getMateriaisProduto().stream()
                .map(mp -> new MaterialProdutoRequestDTO(mp.getMaterial().getIdMaterial(), mp.getQtdMaterialNecessario()))
                .collect(Collectors.toList()));
    }

}
