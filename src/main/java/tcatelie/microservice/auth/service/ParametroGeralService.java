package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.request.ParametroGeralRequestDTO;
import tcatelie.microservice.auth.dto.response.ParametroGeralResponseDTO;
import tcatelie.microservice.auth.mapper.ParametroGeralMapper;
import tcatelie.microservice.auth.model.ParametroGeral;
import tcatelie.microservice.auth.repository.ParametroGeralRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParametroGeralService {

  private final ParametroGeralRepository parametroGeralRepository;
  private final ProdutoRepository produtoRepository;
  private final CalculaPrecoService calculaPrecoService;

  public ParametroGeral findByName(String name) {
    return parametroGeralRepository.findById(name).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parametro [%s] geral não encontrado!".formatted(name))
    );
  }

  public Page<ParametroGeralResponseDTO> findAll(Pageable pageRequest) {
    return parametroGeralRepository.findAll(pageRequest).map(ParametroGeralMapper.INSTANCE::toResponseDTO);
  }

  public void save(ParametroGeralRequestDTO parametroGeral) {
    ParametroGeral parametro = ParametroGeralMapper.INSTANCE.toEntity(parametroGeral);

    if (parametroGeralRepository.existsByNome(parametro.getNome())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um parâmetro geral com esse nome");
    }

    parametroGeralRepository.save(parametro);
  }

  public void update(ParametroGeralRequestDTO parametroGeral) {
    ParametroGeral parametro = ParametroGeralMapper.INSTANCE.toEntity(parametroGeral);

    ParametroGeral parametroAtual = findByName(parametro.getNome());
    parametroAtual.setTipo(parametro.getTipo());
    parametroAtual.setValor(parametro.getValor());
    parametroAtual.setDescricao(parametro.getDescricao());

    parametroGeralRepository.save(parametroAtual);
    if(parametro.getNome().equals("PROJECAO_VENDAS")){
      produtoRepository.findAll().forEach(p -> {
        double novoValor = calculaPrecoService.calcularPrecoProduto(p);
        p.setPreco(novoValor);
        produtoRepository.save(p);
      });
    }

  }

  public void delete(String name) {
    ParametroGeral parametro = findByName(name);
    parametroGeralRepository.delete(parametro);
  }

  public Page<ParametroGeralResponseDTO> findAllPaginated(Pageable pageable) {
    return parametroGeralRepository.findAll(pageable).map(ParametroGeralMapper.INSTANCE::toResponseDTO);
  }

}
