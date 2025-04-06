package tcatelie.microservice.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebhookPayloadDTO {

  private String action;

  @JsonProperty("api_version")
  private String apiVersion;

  private Map<String, String> data;

  @JsonProperty("date_created")
  private String dateCreated;

  private String id;

  @JsonProperty("live_mode")
  private boolean liveMode;

  private String type;

  @JsonProperty("user_id")
  private Long userId;
}
