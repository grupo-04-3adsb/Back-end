package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.request.MaterialProdutoRequestDTO;
import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.MaterialProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.mapper.ProdutoMapper;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.ImagensProdutoRepository;
import tcatelie.microservice.auth.repository.OpcaoPersonalizacaoRepository;
import tcatelie.microservice.auth.repository.PersonalizacaoRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoMapper mapper;
    private final ProdutoRepository repository;
    private final CategoriaService categoriaService;
    private final SubcategoriaService subcategoriaService;
    private final MaterialService materialService;
    private final ImagensProdutoRepository imagensProdutoRepository;
    private final PersonalizacaoRepository personalizacaoRepository;
    private final OpcaoPersonalizacaoRepository opcaoPersonalizacaoRepository;

    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO requestDTO) {
        validarRequest(requestDTO);
        verificarProdutoExistente(requestDTO.getNome());

        Categoria categoria = categoriaService.findByNome(requestDTO.getCategoria().getNome());
        Subcategoria subcategoria = subcategoriaService.findByNome(requestDTO.getSubcategoria().getNomeSubcategoria());
        List<Material> materiais = obterMateriais(requestDTO.getMateriais());

        Produto produto = criarProduto(requestDTO, categoria, subcategoria, materiais);
        produto.getPersonalizacoes().forEach(p -> {
            p.setProduto(produto);
            p.getOpcoes().forEach(o -> {
                o.setPersonalizacao(p);
            });
        });
        produto.getImagensAdicionais().forEach(img -> {
            img.setProduto(produto);
        });

        Produto produtoSalvo = repository.save(produto);

        return montarProdutoResponseDTO(produtoSalvo, materiais, requestDTO.getMateriais());
    }

    private void validarRequest(ProdutoRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("O corpo da requisição não foi informado");
        }
    }

    private void verificarProdutoExistente(String nome) {
        if (repository.existsByNome(nome)) {
            throw new IllegalArgumentException("Já existe um produto cadastrado com esse nome.");
        }
    }

    private List<Material> obterMateriais(List<MaterialProdutoRequestDTO> materialRequests) {
        return materialRequests.stream()
                .map(m -> materialService.findById(m.getIdMaterial()))
                .collect(Collectors.toList());
    }

    private Produto criarProduto(ProdutoRequestDTO requestDTO, Categoria categoria, Subcategoria subcategoria, List<Material> materiais) {
        Produto produto = mapper.toProduto(requestDTO);
        produto.setCategoria(categoria);
        produto.setSubcategoria(subcategoria);
        produto.setImagensAdicionais(requestDTO.getImagensAdicionais().stream()
                .map(img -> new ImagensProduto(img.getUrl()))
                .collect(Collectors.toList()));
        produto.setMateriaisProduto(montarMateriaisProduto(requestDTO.getMateriais(), materiais, produto));
        return produto;
    }

    private List<MaterialProduto> montarMateriaisProduto(List<MaterialProdutoRequestDTO> materialRequests, List<Material> materiais, Produto produto) {
        return materialRequests.stream()
                .map(m -> {
                    Material materialEncontrado = materiais.stream()
                            .filter(material -> material.getIdMaterial() == m.getIdMaterial())
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Material não encontrado com ID: " + m.getIdMaterial()));
                    return new MaterialProduto(materialEncontrado, produto, m.getQtdMaterialNecessaria());
                })
                .collect(Collectors.toList());
    }

    private ProdutoResponseDTO montarProdutoResponseDTO(Produto produto, List<Material> materiais, List<MaterialProdutoRequestDTO> materialRequests) {
        ProdutoResponseDTO produtoResponseDTO = mapper.toResponseDTO(produto);
        List<MaterialProdutoResponseDTO> materialProdutoResponseDTOS = materiais.stream()
                .map(m -> criarMaterialProdutoResponseDTO(m, materialRequests))
                .collect(Collectors.toList());
        produtoResponseDTO.setMateriais(materialProdutoResponseDTOS);
        produtoResponseDTO.setPrecoProducao(calcularPrecoTotalProducao(materialProdutoResponseDTOS));
        return produtoResponseDTO;
    }

    private MaterialProdutoResponseDTO criarMaterialProdutoResponseDTO(Material material, List<MaterialProdutoRequestDTO> materialRequests) {
        MaterialProdutoResponseDTO responseDTO = new MaterialProdutoResponseDTO();
        responseDTO.setIdMaterial(material.getIdMaterial());
        responseDTO.setNomeMaterial(material.getNomeMaterial());
        responseDTO.setEstoque(material.getEstoque());
        responseDTO.setPrecoUnitario(material.getPrecoUnitario());

        MaterialProdutoRequestDTO materialRequest = materialRequests.stream()
                .filter(req -> req.getIdMaterial().equals(material.getIdMaterial()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Material não encontrado com o ID: " + material.getIdMaterial()));

        responseDTO.setQtdParaProducao(materialRequest.getQtdMaterialNecessaria());
        double custoProducao = materialRequest.getQtdMaterialNecessaria() * material.getPrecoUnitario();
        responseDTO.setCustoProducao(custoProducao);
        return responseDTO;
    }

    private double calcularPrecoTotalProducao(List<MaterialProdutoResponseDTO> materialProdutoResponseDTOS) {
        return materialProdutoResponseDTOS.stream()
                .mapToDouble(MaterialProdutoResponseDTO::getCustoProducao)
                .sum();
    }

    public ProdutoResponseDTO buscarProdutoPorId(Integer id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + id));

        List<MaterialProduto> materiaisProduto = produto.getMateriaisProduto();

        List<MaterialProdutoResponseDTO> materialProdutoResponseDTOS = materiaisProduto.stream()
                .map(this::criarMaterialProdutoResponseDTO)
                .collect(Collectors.toList());

        double precoProducaoTotal = calcularPrecoTotalProducao(materialProdutoResponseDTOS);

        ProdutoResponseDTO produtoResponseDTO = mapper.toResponseDTO(produto);
        produtoResponseDTO.setMateriais(materialProdutoResponseDTOS);
        produtoResponseDTO.setPrecoProducao(precoProducaoTotal);

        return produtoResponseDTO;
    }

    private MaterialProdutoResponseDTO criarMaterialProdutoResponseDTO(MaterialProduto materialProduto) {
        Material material = materialProduto.getMaterial();
        MaterialProdutoResponseDTO responseDTO = new MaterialProdutoResponseDTO();

        responseDTO.setIdMaterial(material.getIdMaterial());
        responseDTO.setNomeMaterial(material.getNomeMaterial());
        responseDTO.setEstoque(material.getEstoque());
        responseDTO.setPrecoUnitario(material.getPrecoUnitario());

        Integer qtdParaProducao = materialProduto.getQtdMaterialNecessario();
        responseDTO.setQtdParaProducao(qtdParaProducao);
        double custoProducao = qtdParaProducao * material.getPrecoUnitario();
        responseDTO.setCustoProducao(custoProducao);

        return responseDTO;
    }

}

