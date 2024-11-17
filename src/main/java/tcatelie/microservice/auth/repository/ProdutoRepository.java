package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tcatelie.microservice.auth.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>, JpaSpecificationExecutor<Produto> {

    Optional<Produto> findByNomeAndIdNot(String nome, Integer idProduto);

    Optional<Produto> findBySkuAndIdNot(String sku, Integer idProduto);

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

    @Query("SELECT COUNT(p) FROM Produto p JOIN p.materiaisProduto mp WHERE mp.material.id = ?1")
    Integer countQtdMateriaisProduto(Integer idMaterial);

    Page<Produto> findByMateriaisProduto_Material_IdMaterial(Integer idMaterial, Pageable pageable);

}
