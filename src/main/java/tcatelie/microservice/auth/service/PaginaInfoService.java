package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.response.PaginaInfoResponseDTO;
import tcatelie.microservice.auth.mapper.PaginaInfoMapper;
import tcatelie.microservice.auth.repository.PaginaInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaginaInfoService {

  private final PaginaInfoRepository paginaInfoRepository;

  public Page<PaginaInfoResponseDTO> buscaPaginasFiltradas(String destino, Pageable pagina) {
    return paginaInfoRepository.findByDestino(destino, pagina).map(
            paginaInfo -> PaginaInfoMapper.INSTANCE.toResponseDTO(paginaInfo)
    );
  }
}
