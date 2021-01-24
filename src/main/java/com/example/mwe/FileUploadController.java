package com.example.mwe;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileUploadController {

	@PostMapping(
			value = "/upload",
			produces = { "application/json" },
			consumes = { "multipart/form-data" }
	)
	public ResponseEntity<Void> uploadFile(@Valid @RequestPart(value = "file") MultipartFile file) {
		log.info("Received file with size {}. Processing...", file.getSize());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
