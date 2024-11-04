package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.*;
import tcatelie.microservice.auth.observer.Observable;
import tcatelie.microservice.auth.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class Material implements Observable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Integer idMaterial;

    @Column(name = "nome_material", unique = true)
    private String nomeMaterial;

    @Column(name = "preco_unitario")
    private Double precoUnitario;

    @Column(name = "estoque")
    private Integer estoque;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "material", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MaterialProduto> produtos;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dthrCadastro;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dthrAtualizacao;

    @Transient
    private List<Observer> observers = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dthrCadastro = LocalDateTime.now();
        this.dthrAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dthrAtualizacao = LocalDateTime.now();
    }


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message, Object object) {
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            for (MaterialProduto mp : produtos) {
                observer.update(message, mp.getProduto());
            }
        }
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
        notifyObservers("O preço do material " + this.nomeMaterial + " foi atualizado para " + precoUnitario);
    }

}
