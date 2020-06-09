package com.filestockstorageservice.fileconvertor;

import org.springframework.http.codec.multipart.FilePart;

import com.filestockstorageservice.model.StockRecords;

import reactor.core.publisher.Flux;

/**
 * The interface FileType
 * 
 * @author harsh.jain
 *
 */
public interface FileType {

	/**
	 * @param filePart
	 * 
	 * @return
	 * 
	 */
	public Flux<StockRecords> fileReader(FilePart filePart);

}
