package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.response.DepoimentosResponseDTO;
import tcatelie.microservice.auth.mapper.DepoimentoMapper;
import tcatelie.microservice.auth.repository.DepoimentoRepository;

@Service
@RequiredArgsConstructor
public class DepoimentoService {

  private final DepoimentoRepository depoimentoRepository;

  public Page<DepoimentosResponseDTO> buscaPaginada(Pageable page){
    return depoimentoRepository.findAll(page).map(DepoimentoMapper.INSTANCE::toDTO);
  }

}
