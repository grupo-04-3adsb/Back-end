package tcatelie.microservice.auth.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageService {

  private static final String BLOB_CONTAINER_SAS_URL = "https://tcateliestorage.blob.core.windows.net/images?sv=2024-11-04&ss=bf&srt=sco&sp=rwdlaciytfx&se=2026-01-02T00:35:09Z&st=2025-04-06T16:35:09Z&spr=https&sig=GkEUWUV%2FoA7aWKyfV1jOicMdGqhafBIJVo%2B9Y23Wop4%3D";

  public String uploadFile(MultipartFile file) throws IOException {
    BlobContainerClient containerClient = new BlobContainerClientBuilder()
            .endpoint(BLOB_CONTAINER_SAS_URL)
            .buildClient();

    BlobHttpHeaders headers = new BlobHttpHeaders()
            .setContentType(file.getContentType());

    BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());

    blobClient.upload(file.getInputStream(), file.getSize(), true);
    blobClient.setHttpHeaders(headers);

    return blobClient.getBlobUrl();
  }
}
