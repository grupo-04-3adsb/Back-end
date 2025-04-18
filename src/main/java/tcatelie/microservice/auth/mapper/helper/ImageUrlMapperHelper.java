package tcatelie.microservice.auth.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.util.CreateImageUrl;

@Component
@RequiredArgsConstructor
public class ImageUrlMapperHelper {

  private final CreateImageUrl createImageUrl;

  @Named("formatImageUrl")
  public String formatImageUrl(String virtualPath) {
    return createImageUrl.getCompleteImageUrl(virtualPath);
  }
}