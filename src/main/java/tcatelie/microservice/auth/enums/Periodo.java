package tcatelie.microservice.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Periodo {
  SEMANAL("Semanal"),
  MENSAL("Mensal"),
  ANUAL("Anual");

  private String descricao;

}
