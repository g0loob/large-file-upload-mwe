package com.example.mwe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MweApplicationTests {

	public static final long MAX_FILE_UPLOAD_SIZE = 20L * 1024L * 1024L;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void uploadingSmallerFileShouldReturnCreatedStatusCode() {
		var file = new TestResource("smaller-file", MAX_FILE_UPLOAD_SIZE >>> 1);
		var responseEntity = uploadFile(file, Void.class);
		assertStatusCode(CREATED, responseEntity);
	}

	@Test
	public void uploadingMaxLimitFileShouldReturnCreatedStatusCode() {
		var file = new TestResource("max-limit-file", MAX_FILE_UPLOAD_SIZE);
		var responseEntity = uploadFile(file, Void.class);
		assertStatusCode(CREATED, responseEntity);
	}

	@Test
	public void uploadingLargeFilesShouldReturnPayloadTooLargeStatusCode() {
		var file = new TestResource("large-file", MAX_FILE_UPLOAD_SIZE << 1);
		var responseEntity = uploadFile(file, ApiError.class);
		assertStatusCode(PAYLOAD_TOO_LARGE, responseEntity);
	}

	private <T> ResponseEntity<T> uploadFile(TestResource fileToUpload, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		if (fileToUpload != null) {
			body.add("file", fileToUpload);
		}

		String url = "http://localhost:" + port + "/upload";
		HttpEntity<?> entity = new HttpEntity<>(body, headers);
		return restTemplate.exchange(url, POST, entity, responseType);
	}

	private void assertStatusCode(HttpStatus expectedStatusCode, ResponseEntity<?> responseEntity) {
		HttpStatus actualStatusCode = responseEntity.getStatusCode();
		assertEquals(expectedStatusCode, actualStatusCode, "Wrong status code");
	}
}
