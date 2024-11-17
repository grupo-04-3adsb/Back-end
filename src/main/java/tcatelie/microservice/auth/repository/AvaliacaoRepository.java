package tcatelie.microservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tcatelie.microservice.auth.model.Avaliacao;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer>, JpaSpecificationExecutor<Avaliacao> {

    List<Avaliacao> findByProduto_Id(Integer produtoId);

    List<Avaliacao> findByUsuario_IdUsuario(Integer usuarioId);

    Integer countByProduto_IdAndAvaliacaoAprovadaTrue(Integer produtoId);

    Integer countByProduto_Id(Integer produtoId);

    Integer countByUsuario_IdUsuario(Integer usuarioId);

    Integer countByAvaliacaoAprovada(Boolean avaliacaoAprovada);

    @Query("SELECT AVG(a.notaAvaliacao) FROM Avaliacao a WHERE a.produto.id = :produtoId AND a.avaliacaoAprovada = true")
    Double calcularMediaNotaAprovada(@Param("produtoId") Integer produtoId);
}
