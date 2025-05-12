package tcatelie.microservice.auth.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
public class CreateImageUrl {

  @Value("${azure.blob.storage.url}")
  private String BLOB_STORAGE_URL;

  @Value("${azure.blob.storage.token.sas}")
  private String BLOB_STORAGE_TOKEN_SAS;

  public boolean isAzureUrl(String url) {
    return url != null && url.startsWith(BLOB_STORAGE_URL);
  }

  public String getCompleteImageUrl(String virtualPath) {

    if (StringUtils.isEmpty(virtualPath)) {
      return null;
    }

    if (!isAzureUrl(virtualPath)) {
      return virtualPath;
    }

    return String.format(
            "%s?%s",
            virtualPath,
            BLOB_STORAGE_TOKEN_SAS
    );
  }

  public String limparParametrosDaUrl(String url) {
    if (url == null) return null;
    int index = url.indexOf('?');
    return (index >= 0) ? url.substring(0, index) : url;
  }
}
