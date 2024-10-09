package tcatelie.microservice.auth.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {

	@Bean
	public Drive googleDriveService() throws IOException {
		InputStream credentialsStream = getClass().getResourceAsStream("/credentials/service-account.json");

		GoogleCredential credential = GoogleCredential.fromStream(credentialsStream)
				.createScoped(Collections.singleton(DriveScopes.DRIVE));

		return new Drive.Builder(credential.getTransport(), credential.getJsonFactory(), credential)
				.setApplicationName("E-commerce App").build();
	}
}
