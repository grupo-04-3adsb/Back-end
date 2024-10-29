package tcatelie.microservice.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genero {
    MASCULINO("Masculino", 1),
    FEMININO("Feminino", 2),
    OUTRO("Outro", 3);

    private final String descricao;
    private final int valor;

    public static Genero fromValue(int valor) {
        for (Genero gender : values()) {
            if (gender.getValor() == valor) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido: " + valor);
    }

    public static Genero fromDisplayName(String descricao) {
        for (Genero gender : values()) {
            if (gender.getDescricao().equalsIgnoreCase(descricao)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Nome desconhecido: " + descricao);
    }
}
