package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "custo_outros")
public class CustoOutros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_custo_outros")
    private Integer idCustoOutros;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private Double valor;
}
