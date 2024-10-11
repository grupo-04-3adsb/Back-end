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
import tcatelie.microservice.auth.mapper.ProdutoMapper;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.ImagensProdutoRepository;
import tcatelie.microservice.auth.repository.OpcaoPersonalizacaoRepository;
import tcatelie.microservice.auth.repository.PersonalizacaoRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.specification.ProdutoSpecification;

import java.io.IOException;
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

        produto.getPersonalizacoes().forEach(p -> {
            p.setProduto(produto);
            p.getOpcoes().forEach(o -> o.setPersonalizacao(p));
        });

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
}
