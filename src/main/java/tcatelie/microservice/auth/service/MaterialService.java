package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import tcatelie.microservice.auth.dto.filter.MaterialFiltroDTO;
import tcatelie.microservice.auth.dto.request.MaterialRequestDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialDetalhadoResponseDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialResponseDTO;
import tcatelie.microservice.auth.dto.revison.ProdutoRevisaoResponseDTO;
import tcatelie.microservice.auth.mapper.MaterialMapper;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.model.MaterialProduto;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.observer.Observer;
import tcatelie.microservice.auth.repository.MaterialProdutoRepository;
import tcatelie.microservice.auth.repository.MaterialRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.specification.MaterialSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final ProdutoRepository produtoRepository;
    private final MaterialProdutoRepository materialProdutoRepository;

    public MaterialResponseDTO cadastrar(MaterialRequestDTO dto) {
        Material materialEntidade = MaterialMapper.toEntity(dto);

        Optional<Material> materialBuscado = materialRepository.findByNomeMaterial(dto.getNome());
        if (materialBuscado.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um material com o nome informado");
        }

        validarPrecos(dto);

        Material materialSalvo = materialRepository.save(materialEntidade);

        return MaterialMapper.toMaterialResponseDTO(materialSalvo);
    }

    public List<MaterialResponseDTO> buscar() {

        try {
            List<Material> materiais = materialRepository.findAll();

            return materiais.stream().map(MaterialMapper::toMaterialResponseDTO).toList();
        } catch (ServerErrorException e) {
            throw e;
        }
    }

    public MaterialDetalhadoResponseDTO buscarPorId(Integer id) {

        try {
            Optional<Material> materialBuscado = materialRepository.findById(id);

            if (materialBuscado.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return MaterialMapper.toMaterialDetalhadoResponseDTO(materialBuscado.get());
        } catch (ServerErrorException e) {
            throw e;
        }

    }

    public MaterialDetalhadoResponseDTO atualizar(MaterialRequestDTO dto, Integer id) {
        Material materialEntidade = materialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Double precoAntigo = materialEntidade.getPrecoUnitario();

        materialEntidade.setNomeMaterial(dto.getNome());
        materialEntidade.setDescricao(dto.getDescricao());
        materialEntidade.setPrecoUnitario(dto.getPreco());

        validarPrecos(dto);

        Material materialSalvo = materialRepository.save(materialEntidade);

        if (!precoAntigo.equals(dto.getPreco())) {
            List<Produto> produtos = materialEntidade.getProdutos().stream()
                    .map(MaterialProduto::getProduto)
                    .collect(Collectors.toList());

            produtos.forEach(produto -> {
                materialEntidade.addObserver((Observer) produto);
                produto.setRepository(produtoRepository);
            });

            materialEntidade.notifyObservers("O preço do material " + materialEntidade.getNomeMaterial() + " foi alterado.");
        }

        return MaterialMapper.toMaterialDetalhadoResponseDTO(materialSalvo);
    }

    public void deletar(Integer id) {

        try {
            if (materialRepository.findById(id).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            Integer qtdProdutos = materialProdutoRepository.countByMaterial_IdMaterial(id);

            if (qtdProdutos > 0) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível excluir um material que está vinculado a um produto, total de produtos vinculados: " + qtdProdutos);
            }

            materialRepository.deleteById(id);
        } catch (ServerErrorException e) {
            throw e;
        }

    }

    public Material findById(Integer idMaterial) throws IllegalArgumentException {
        if (idMaterial == null) {
            throw new IllegalArgumentException("Id do material não informado");
        }

        return materialRepository.findById(idMaterial).orElseThrow(() ->
                new IllegalArgumentException("Material não encontrado com o ID: " + idMaterial)
        );
    }

    public Page<MaterialDetalhadoResponseDTO> pesquisarPorNome(String nome, Pageable pageRequest) {
        return materialRepository.findByNomeMaterialContainingIgnoreCase(nome, pageRequest)
                .map(MaterialMapper::toMaterialDetalhadoResponseDTO);
    }

    public Page<MaterialDetalhadoResponseDTO> filtrar(Pageable pageable, MaterialFiltroDTO filtro) {
        return materialRepository.findAll(MaterialSpecification.filtrar(filtro), pageable).map(material -> {
            MaterialDetalhadoResponseDTO responseDTO = MaterialMapper.toMaterialDetalhadoResponseDTO(material);
            responseDTO.setQtdProdutos(produtoRepository.countQtdMateriaisProduto(material.getIdMaterial()));
            return responseDTO;
        });
    }

    public Page<ProdutoRevisaoResponseDTO> getRevisoes(Pageable pageable, Integer idMaterial, Double precoUnitarioNovo) {

        Material material = findById(idMaterial);

        Page<MaterialProduto> materiaisProduto = materialProdutoRepository.findByMaterial_IdMaterial(idMaterial, pageable);
        Page<Produto> produtos = materiaisProduto.map(MaterialProduto::getProduto);

        return produtos.map(p -> {
            ProdutoRevisaoResponseDTO produto = new ProdutoRevisaoResponseDTO();
            produto.setIdProduto(p.getId());
            produto.setNomeProduto(p.getNome());
            produto.setMargemDeLucroNova(calcularMargemDeLucro(p, idMaterial, precoUnitarioNovo));
            produto.setMargemDeLucroAntiga(p.getMargemLucro());
            return produto;
        });

    }

    private Double calcularMargemDeLucro(Produto produto, Integer idMaterial, Double precoUnitarioNovo) {
        Double custoTotal = 0.0;

        for (MaterialProduto materialProduto : produto.getMateriaisProduto()) {
            Material material = materialProduto.getMaterial();
            Integer quantidadeNecessaria = materialProduto.getQtdMaterialNecessario();

            if (material.getIdMaterial().equals(idMaterial)) {
                custoTotal += precoUnitarioNovo * quantidadeNecessaria;
            } else {
                custoTotal += material.getPrecoUnitario() * quantidadeNecessaria;
            }
        }

        Double precoVendaComDesconto = produto.getPreco() * (1 - produto.getDesconto());

        return ((precoVendaComDesconto - custoTotal) / precoVendaComDesconto) * 100;
    }

    private void validarPrecos(MaterialRequestDTO dto) {
        if (dto.getPrecoPacote() != null &&
                dto.getUnidadesPorPacote() == null ||
                dto.getPrecoPacote() == null &&
                        dto.getUnidadesPorPacote() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para informar o preço do pacote é necessário informar a quantidade de unidades por pacote e vice-versa!");
        }

        if (dto.getPrecoPacote() != null &&
                dto.getUnidadesPorPacote() != null) {
            double precoUnitario = dto.getPrecoPacote() / dto.getUnidadesPorPacote();
            if (precoUnitario != dto.getPreco()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço unitário inserido não corresponde ao preço unitário do pacote!");
            }
        }
    }

}
