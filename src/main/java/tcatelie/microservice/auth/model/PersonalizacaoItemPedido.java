package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class PersonalizacaoItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao_personalizacao")
    private String descricaoPersonalizacao;

    @Column(name = "valor_personalizacao")
    private Double valorPersonalizacao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private ItemPedido itemPedido;

    @OneToOne(fetch = FetchType.LAZY)
    private Personalizacao personalizacao;

    @OneToOne(fetch = FetchType.LAZY)
    private OpcaoPersonalizacao opcaoPersonalizacao;
}
