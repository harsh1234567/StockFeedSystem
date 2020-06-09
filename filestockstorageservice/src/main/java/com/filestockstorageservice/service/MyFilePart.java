package com.filestockstorageservice.service;

import java.nio.file.Path;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MyFilePart implements FilePart {

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpHeaders headers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<DataBuffer> content() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String filename() {
		// TODO Auto-generated method stub
		return "data.csv";
	}

	@Override
	public Mono<Void> transferTo(Path dest) {
		// TODO Auto-generated method stub
		return null;
	}

}
