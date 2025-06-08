package tcatelie.microservice.auth.dto.kpi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjecaoVendaDTO {

  private Integer expectativa;
  private Integer quantidade;
  private Double porcentagem;
}
