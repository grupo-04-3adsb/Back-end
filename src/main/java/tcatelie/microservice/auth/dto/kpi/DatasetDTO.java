package tcatelie.microservice.auth.dto.kpi;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatasetDTO<T> {
  private String label;
  private List<T> data;
}
