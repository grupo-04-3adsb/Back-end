package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.model.Material;
import tcatelie.microservice.auth.repository.MaterialRepository;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialResponseDTO cadastrar(MaterialRequestDTO dto){
        Material materialEntidade = MaterialMapper.toEntity(dto);

        try{
            Optional<Material> materialBuscado = materialRepository.findByNome(dto.getNome());
            if(materialBuscado.isPresent()){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }

            Material materialSalvo = materialRepository.save(materialEntidade);

            return MaterialMapper.toMaterialResponseDTO(materialSalvo);
        }catch (ServerErrorException e){
            throw e;
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

    public MaterialDetalhadoResponseDTO atualizar(MaterialRequestDTO dto, Integer id){

        try{
            if(materialRepository.findById(id).isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            Material materialEntidade = MaterialMapper.toEntity(dto);
            materialEntidade.setId(id);
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

            return repository.findById(idMaterial).orElseThrow(() ->
                    new IllegalArgumentException("Material não encontrado com o ID: " + idMaterial)
            );
        }
}
