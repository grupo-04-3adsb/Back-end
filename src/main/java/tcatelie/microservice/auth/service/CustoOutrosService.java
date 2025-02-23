package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.filter.CustoOutrosFiltroDTO;
import tcatelie.microservice.auth.dto.request.CustoOutrosRequestDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.mapper.CustoOutrosMapper;
import tcatelie.microservice.auth.model.CustoOutros;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.repository.CustosOutrosRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustoOutrosService {

    private final CustosOutrosRepository repository;
    private final CustoOutrosMapper mapper;
    private final ProdutoRepository produtoRepository;
    private final CalculaPrecoService calculaPrecoService;

    public CustoOutros findById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Custo de outros não encontrado")
        );
    }

    public Page<CustoOutrosResponseDTO> findAllPaginado(
            CustoOutrosFiltroDTO filtro, Pageable page
    ) {
        return repository.findAll(filtro, page).map(mapper::toResponseDTO);
    }

    public List<CustoOutrosResponseDTO> findAll() {
        return repository.findAll().stream().map(mapper::toResponseDTO).toList();
    }

    public void cadastrarCustoOutro(CustoOutrosRequestDTO custo){
        if(StringUtils.isBlank(custo.getDescricao()) || custo.getValor() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Descrição e valor são obrigatórios");
        }
        CustoOutros custoOutros = mapper.toEntity(custo);

        List<Produto> produtos = produtoRepository.findAll();

        repository.save(custoOutros);

        produtos.forEach(produto -> {
            double novoPreco = calculaPrecoService.calcularPrecoNovoCustoOutro(produto, custo.getValor());
            produto.setPreco(novoPreco);
            produtoRepository.save(produto);
        });
    }

    public void editarCustoOutro(Integer id, CustoOutrosRequestDTO custo){
        CustoOutros custoOutros = findById(id);
        if(StringUtils.isNotBlank(custo.getDescricao())){
            custoOutros.setDescricao(custo.getDescricao());
        }

        boolean isPrecoDiferente = custo.getValor() != null && !custo.getValor().equals(custoOutros.getValor());
        if(custo.getValor() != null) {
            custoOutros.setValor(custo.getValor());
        }

        repository.save(custoOutros);

        if(isPrecoDiferente){
            List<Produto> produtos = produtoRepository.findAll();
            produtos.forEach(produto -> {
                double novoPreco = calculaPrecoService.calcularPrecoComCustoOutroEditado(produto, custoOutros);
                produto.setPreco(novoPreco);
                produtoRepository.save(produto);
            });
        }
    }

    public void removerCustoOutro(Integer id){
        CustoOutros custoOutros = findById(id);
        repository.delete(custoOutros);

        List<Produto> produtos = produtoRepository.findAll();
        produtos.forEach(produto -> {
            double novoPreco = calculaPrecoService.calcularSemCustoOutro(produto, custoOutros);
            produto.setPreco(novoPreco);
            produtoRepository.save(produto);
        });
    }

}
