package com.filestockstorageservice.fileconvertor;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;

import com.filestockstorageservice.model.StockRecords;

import reactor.core.publisher.Flux;

/**
 * The class FileConvertor
 * 
 * @author harsh.jain
 *
 */
@Component
public class FileConvertor {

	/** The File */
	private FileType file;

	public void setFile(FileType file) {
		this.file = file;
	}

	/**
	 * The create archive file .
	 * 
	 * @param filePart
	 * 
	 * @return Flux
	 * 
	 */
	public Flux<StockRecords> createArchiveFile(FilePart FilePart) {

		return file.fileReader(FilePart);
	}

	/**
	 * Instantiates a new FileConvertor.
	 */
	private FileConvertor() {
		super();
	}

}
