package tcatelie.microservice.auth.dto.kpi;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChartDTO {

  private List<String> labels;

  private List<DatasetDTO> datasets;
}
