package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tcatelie.microservice.auth.model.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>, JpaSpecificationExecutor<Produto> {

    Boolean existsByNome(String nome);
    Boolean existsBySku(String nome);

    List<Produto> findByCategoria_IdCategoria(Integer idCategoria);
    List<Produto> findBySubcategoria_IdSubcategoria(Integer idSubcategoria);
    List<Produto> findByCategoria_NomeCategoria(String nomeCategoria);
    List<Produto> findBySubcategoria_NomeSubcategoria(String nomeSubcategoria);
    List<Produto> findAllByNomeIn(List<String> nomesProdutos);

    Integer countByCategoria_IdCategoria(Integer idCategoria);
    Integer countBySubcategoria_IdSubcategoria(Integer idSubcategoria);
    Integer countByCategoria_NomeCategoria(String nomeCategoria);
    Integer countBySubcategoria_NomeSubcategoria(String nomeSubcategoria);
    Integer countByNomeIn(List<String> nomesProdutos);

}
