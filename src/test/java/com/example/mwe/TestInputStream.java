package com.example.mwe;

import java.io.InputStream;

public class TestInputStream extends InputStream {

	private final long size;

	private long counter;

	public TestInputStream(long size) {
		this.size = size;
	}

	@Override
	public int read() {
		if (counter < size) {
			counter++;
			return 0;
		} else {
			return -1;
		}
	}
}