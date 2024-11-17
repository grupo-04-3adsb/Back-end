package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.filter.CustoOutrosFiltroDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.mapper.CustoOutrosMapper;
import tcatelie.microservice.auth.model.CustoOutros;
import tcatelie.microservice.auth.repository.CustosOutrosRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustoOutrosService {

    private final CustosOutrosRepository repository;
    private final CustoOutrosMapper mapper;

    private CustoOutros findById(Integer id) {
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
}
