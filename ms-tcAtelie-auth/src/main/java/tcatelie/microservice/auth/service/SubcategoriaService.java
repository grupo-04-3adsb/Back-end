package tcatelie.microservice.auth.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.request.SubcategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.mapper.SubcategoriaMapper;
import tcatelie.microservice.auth.model.Subcategoria;
import tcatelie.microservice.auth.repository.SubcategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubcategoriaService {

    private final SubcategoriaRepository repository;
    private final SubcategoriaMapper mapper;
    private final CategoriaService categoriaService;

    public SubcategoriaResponseDTO cadastrarSubcategoria(SubcategoriaRequestDTO requestDTO) {
        validarRequest(requestDTO);
        Subcategoria subCategoriaEntidade = mapper.toSubcategoria(requestDTO);

        Optional<Subcategoria> subCategoriaBuscada = repository.findByNomeSubcategoria(requestDTO.getNomeSubcategoria());
        if(subCategoriaBuscada.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Subcategoria SubCategoriaSalva = repository.save(subCategoriaEntidade);
        return mapper.toSubcategoriaResponse(SubCategoriaSalva);
    }

    private void validarRequest(SubcategoriaRequestDTO requestDTO) {
        if (requestDTO == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O corpo da requisição não foi informado");
    }

    public Subcategoria findByNome(String nome) throws IllegalArgumentException {
        if (StringUtils.isBlank(nome)) {
            throw new IllegalArgumentException("Nome da subcategoria não informado");
        }

        return repository.findByNomeSubcategoria(nome).orElseThrow(() ->
                new IllegalArgumentException("Subcategoria não encontrada com o nome: " + nome)
        );
    }

    public Page<SubcategoriaResponseDTO> pesquisarPorNome(String nome, Integer idCategoria,Pageable pageable) {
        return repository.findByNomeSubcategoriaContainingIgnoreCaseAndCategoria(nome, categoriaService.findById(idCategoria), pageable).map(c -> mapper.toSubcategoriaResponse(c));
    }

    public Subcategoria findById(Integer id) throws IllegalArgumentException{
        return repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Sub-Categoria não encontrada com o id: " + id)
        );
    }

    public List<SubcategoriaResponseDTO> ListarSubscategorias() {
        List<Subcategoria> subCategorias = repository.findAll();
        return subCategorias.stream()
                .map(mapper::toSubcategoriaResponse).toList();
    }

    public SubcategoriaResponseDTO atualizar(@Valid SubcategoriaRequestDTO requestDTO, Integer id){
        if(repository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Subcategoria entidade = mapper.toSubcategoria(requestDTO);
        entidade.setIdSubcategoria(id);
        entidade.setNomeSubcategoria(requestDTO.getNomeSubcategoria());
        Subcategoria subCategoriaSalva = repository.save(entidade);

        return mapper.toSubcategoriaResponse(subCategoriaSalva);
    }

    public void deletar(Integer id) {
        if(repository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
