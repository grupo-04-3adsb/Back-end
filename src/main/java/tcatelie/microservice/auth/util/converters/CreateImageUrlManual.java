package tcatelie.microservice.auth.util.converters;

import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.util.StringUtils;

public class CreateImageUrlManual {

    private String BLOB_STORAGE_URL;

    private String BLOB_STORAGE_TOKEN_SAS;

    public CreateImageUrlManual() {
        this.BLOB_STORAGE_URL = System.getenv("AZURE_BLOB_STORAGE_URL");
        this.BLOB_STORAGE_TOKEN_SAS = System.getenv("AZURE_BLOB_STORAGE_TOKEN_SAS");
    }

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

}
