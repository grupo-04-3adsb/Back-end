package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.response.ValoresResponseDTO;
import tcatelie.microservice.auth.mapper.ValoresMapper;
import tcatelie.microservice.auth.repository.ValoresRepository;

@Service
@RequiredArgsConstructor
public class ValoresService {

  private final ValoresRepository valoresRepository;

  public Page<ValoresResponseDTO> buscaValoresPaginado(Pageable page) {
    return valoresRepository.findAll(page).map(ValoresMapper.INSTANCE::toDTO);
  }
}
