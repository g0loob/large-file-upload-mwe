package com.example.mwe;

import java.io.InputStream;

import org.springframework.core.io.AbstractResource;

public class TestResource extends AbstractResource {

	private final String filename;
	private final long size;

	public TestResource(String filename, long size) {
		this.filename = filename;
		this.size = size;
	}

	@Override
	public String getDescription() {
		return filename;
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public InputStream getInputStream() {
		return new TestInputStream(size);
	}
}