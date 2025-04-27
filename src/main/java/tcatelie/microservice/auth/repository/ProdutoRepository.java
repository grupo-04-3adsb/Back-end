package tcatelie.microservice.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tcatelie.microservice.auth.dto.kpi.ProdutoKPIDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.model.Categoria;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.model.Subcategoria;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>, JpaSpecificationExecutor<Produto> {

  Optional<Produto> findByNomeAndIdNot(String nome, Integer idProduto);

  Optional<Produto> findByNome(String nome);

  Optional<Produto> findBySkuAndIdNot(String sku, Integer idProduto);

  Boolean existsByNome(String nome);

  Boolean existsBySku(String nome);

  List<Produto> findByCategoria_IdCategoria(Integer idCategoria);

  List<Produto> findBySubcategoria_IdSubcategoria(Integer idSubcategoria);

  List<Produto> findByCategoria_NomeCategoria(String nomeCategoria);

  List<Produto> findBySubcategoria_NomeSubcategoria(String nomeSubcategoria);

  List<Produto> findAllByNomeIn(List<String> nomesProdutos);

  List<Produto> findAllByIdIn(List<Integer> idsProdutos);

  Integer countByCategoria_IdCategoria(Integer idCategoria);

  Integer countBySubcategoria_IdSubcategoria(Integer idSubcategoria);

  Integer countByCategoria_NomeCategoria(String nomeCategoria);

  Integer countBySubcategoria_NomeSubcategoria(String nomeSubcategoria);

  Integer countByNomeIn(List<String> nomesProdutos);

  @Query("SELECT COUNT(p) FROM Produto p JOIN p.materiaisProduto mp WHERE mp.material.id = ?1")
  Integer countQtdMateriaisProduto(Integer idMaterial);

  Page<Produto> findByMateriaisProduto_Material_IdMaterial(Integer idMaterial, Pageable pageable);

  Page<Produto> findByNomeContainingIgnoreCaseOrSkuContainingIgnoreCase(String nome, String sku, Pageable pageable);

  Page<Produto> findByCategoriaInOrSubcategoriaIn(List<Categoria> categorias, List<Subcategoria> subcategorias, PageRequest pageRequest);

  @Query("""
        SELECT new tcatelie.microservice.auth.dto.kpi.ProdutoKPIDTO(
            p.id,
            p.nome,
            c.nomeCategoria,
            s.nomeSubcategoria,
            SUM(ip.quantidade),
            p.dthrCadastro,
            p.urlImagemPrincipal
        ) 
        FROM Pedido pe
        JOIN pe.itens ip
        JOIN ip.produto p
        JOIN p.categoria c
        JOIN p.subcategoria s
        WHERE pe.status = :status
        GROUP BY p.id, p.nome, c.nomeCategoria, s.nomeSubcategoria, p.dthrCadastro, p.urlImagemPrincipal
    """)
  Page<ProdutoKPIDTO> buscarProdutosMaisVendidos(
          StatusPedido status,
          Pageable pageable
  );

}
