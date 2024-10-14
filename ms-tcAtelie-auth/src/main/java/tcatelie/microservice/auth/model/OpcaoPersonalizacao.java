package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpcaoPersonalizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_opcao_personalizacao")
    private Integer idOpcaoPersonalizacao;

    @Column(name = "nome_opcao")
    private String nomeOpcao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "acrescimo_opcao")
    private Double acrescimoOpcao;

    @Column(name = "url_img_opcao")
    private String urlImagemOpcao;

    @Column(name = "id_img_drive")
    private String idImgDrive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_personalizacao")
    private Personalizacao personalizacao;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dthrCadastro;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dthrAtualizacao;

    @PrePersist
    protected void onCreate() {
        this.dthrCadastro = LocalDateTime.now();
        this.dthrAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dthrAtualizacao = LocalDateTime.now();
    }
}

