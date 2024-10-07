package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import tcatelie.microservice.auth.dto.request.MaterialRequestDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialDetalhadoResponseDTO;
import tcatelie.microservice.auth.dto.response.material.MaterialResponseDTO;
import tcatelie.microservice.auth.mapper.MaterialMapper;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.repository.MaterialRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialResponseDTO cadastrar(MaterialRequestDTO dto) {
        Material materialEntidade = MaterialMapper.toEntity(dto);

        try {
            Optional<Material> materialBuscado = materialRepository.findByNomeMaterial(dto.getNome());
            if (materialBuscado.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }

            Material materialSalvo = materialRepository.save(materialEntidade);

            return MaterialMapper.toMaterialResponseDTO(materialSalvo);
        } catch (ServerErrorException e) {
            throw e;
        }
    }

    public List<MaterialResponseDTO> buscar(){

        try{
            List<Material> materiais = materialRepository.findAll();

            return materiais.stream().map(MaterialMapper::toMaterialResponseDTO).toList();
        }catch (ServerErrorException e){
            throw e;
        }
    }

    public MaterialDetalhadoResponseDTO buscarPorId(Integer id){

        try{
            Optional<Material> materialBuscado = materialRepository.findById(id);

            if(materialBuscado.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return MaterialMapper.toMaterialDetalhadoResponseDTO(materialBuscado.get());
        }catch (ServerErrorException e){
            throw e;
        }

    }

    /*
    * Este método de PUT está sobrescrevendo as entidades no banco,
    * você está gerando uma nova entidade a partir do DTO, porém no DTO
    * não tem todos os campos na entidade como o de relacionamento, desta
    * forma quando você for salvar ira remover os relacionamentos com outras entidades,
    * minha sugestão Munas é você no material entidade ser igual a entidade no findById,
    * depois você só altera os campos necessários com base no DTO sem interferir nos outros
     * */
    public MaterialDetalhadoResponseDTO atualizar(MaterialRequestDTO dto, Integer id){

        try{
            if(materialRepository.findById(id).isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            Material materialEntidade = MaterialMapper.toEntity(dto);
            materialEntidade.setIdMaterial(id);
            Material materialSalvo = materialRepository.save(materialEntidade);

            return MaterialMapper.toMaterialDetalhadoResponseDTO(materialSalvo);
        }catch (ServerErrorException e){
            throw e;
        }
    }

    public void deletar(Integer id){

        try{
            if(materialRepository.findById(id).isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            materialRepository.deleteById(id);
        }catch (ServerErrorException e){
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
}
