package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.request.ProdutoRequestDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.model.Produto;

public class ProdutoMapper {

    private final ProdutoMapper produtoMapper = new ProdutoMapper();

    public Produto toEntity(ProdutoRequestDTO requestDTO) throws Exception {
        Produto produto = new Produto();
        produto.setNome(requestDTO.getNome());
        produto.setPreco(requestDTO.getPreco());
        produto.setDescricao(requestDTO.getDescricao());
        produto.setMaterial(requestDTO.getMaterial());
        produto.setDimensao(requestDTO.getDimensao());
        produto.setUrl_produto(requestDTO.getUrl_produto());

        return produto;
    }

    public ProdutoResponseDTO toDTO(Produto entity) throws Exception {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setPreco(entity.getPreco());
        dto.setDescricao(entity.getDescricao());
        dto.setMaterial(entity.getMaterial());
        dto.setUrl_produto(entity.getUrl_produto());

        return dto;
    }

}
