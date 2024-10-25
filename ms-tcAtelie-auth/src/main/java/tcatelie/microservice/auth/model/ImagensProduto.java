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
public class ImagensProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagem")
    private Integer idImagem;

    @Column(name = "url_img_adicional")
    private String urlImgAdicional;

    @Column(name = "id_img_drive")
    private String idImgDrive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_produto")
    private Produto produto;


    @Column(name = "data_hora_cadastro")
    private LocalDateTime dthrCadastro;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dthrAtualizacao;

    public ImagensProduto(String urlImgAdicional){
        this.urlImgAdicional = urlImgAdicional;
    }

    public ImagensProduto(String urlImgAdicional, Integer idImagem){
        this.urlImgAdicional = urlImgAdicional;
        this.idImagem = idImagem;
    }

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
