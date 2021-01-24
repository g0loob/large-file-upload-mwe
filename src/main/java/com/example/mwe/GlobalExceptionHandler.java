package com.example.mwe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	protected ResponseEntity<ApiError> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		return new ResponseEntity<>(new ApiError(e.getMessage()), PAYLOAD_TOO_LARGE);
	}
}
