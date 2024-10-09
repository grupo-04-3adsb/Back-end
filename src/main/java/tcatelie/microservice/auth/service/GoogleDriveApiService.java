package tcatelie.microservice.auth.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class GoogleDriveApiService {

    private Drive driveService;

    @Getter
    private final String rootFolderId = "16yN_yD1JbDVQUssFowEu1DuUVfrgwJkq";

    public GoogleDriveApiService(Drive driveService) {
        this.driveService = driveService;
    }

    public String findFolderByName(String folderName, String parentFolderId) throws IOException {
        String query = String.format("mimeType='application/vnd.google-apps.folder' and name='%s' and '%s' in parents", folderName, parentFolderId);

        FileList result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id, name)")
                .execute();

        if (result.getFiles().isEmpty()) {
            return null;
        }

        return result.getFiles().get(0).getId();
    }

    public String createFolder(String folderName, String parentFolderId) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setParents(Collections.singletonList(parentFolderId));

        File folder = driveService.files().create(fileMetadata)
                .setFields("id")
                .execute();

        return folder.getId();
    }


    public String getPublicUrl(String fileId) {
        return "https://drive.google.com/uc?id=" + fileId;
    }

    public void makeFilePublic(String fileId) throws IOException {
        Permission publicPermission = new Permission()
                .setType("anyone")
                .setRole("reader");

        driveService.permissions().create(fileId, publicPermission).execute();
    }

    public String uploadFileToFolder(java.io.File file, String fileName, String parentFolderId) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(parentFolderId));

        FileContent mediaContent = new FileContent("image/jpeg", file);

        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        makeFilePublic(uploadedFile.getId());

        return uploadedFile.getId();
    }


    public void deleteFileOrFolder(String fileId) throws IOException {
        driveService.files().delete(fileId).execute();
    }

    public void deleteFolderAndContents(String folderId) throws IOException {
        FileList fileList = driveService.files().list()
                .setQ("'" + folderId + "' in parents")
                .setFields("files(id)")
                .execute();

        for (File file : fileList.getFiles()) {
            deleteFileOrFolder(file.getId());
        }

        deleteFileOrFolder(folderId);
    }
}
