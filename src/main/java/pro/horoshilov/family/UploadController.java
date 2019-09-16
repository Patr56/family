package pro.horoshilov.family;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class UploadController {

	private Logger logger = LoggerFactory.getLogger(UploadController.class);

	@PostMapping("/upload")
	public String upload(@RequestParam("file") final MultipartFile file,
			@RequestParam("name") final String name,
			@RequestParam("email") final String email
	) {
		try {
			storeFile(file.getInputStream(), file.getOriginalFilename());
		}
		catch (IOException e) {
			final String message = String.format("error: %s", e.getMessage());
			logger.error(message, e);
			return message;
		}

		return "uploaded";
	}

	private void storeFile(final InputStream inputStream, final String name) {
		try {
			Files.copy(inputStream, Paths.get(name));
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}