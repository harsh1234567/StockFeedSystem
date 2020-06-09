package com.filestockstorageservice.service;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;

import com.filestockstorageservice.model.StockResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface FileUploadService
 * 
 * @author harsh.jain
 *
 */
public interface FileUploadService {

	/**
	 * 
	 * 
	 * @param filePart
	 * 
	 * @return
	 */
	Flux<StockResponse> readBySingleFile(FilePart filePart);

	/**
	 * @param filePartMap
	 * 
	 * @return
	 */
	Flux<StockResponse> readByMultipleFile(Mono<MultiValueMap<String, Part>> filePartMap);

}
