package tcatelie.microservice.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Status {
    BLOQUEADO("BLOQUEADO", 1),
    HABILITADO("HABILITADO", 2);

    private String descricao;
    private int valor;
}
