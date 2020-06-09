package com.filestockstorageservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.filestockstorageservice.constants.Constant;
import com.filestockstorageservice.constants.LoggerConstants;
import com.filestockstorageservice.exception.FileHeaderNotFound;
import com.filestockstorageservice.exception.FileIsNotFoundException;
import com.filestockstorageservice.model.StockResponse;
import com.filestockstorageservice.routingConstant.RoutingConstant;
import com.filestockstorageservice.service.FileUploadService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The class FileUploadController
 * 
 * @author harsh.jain
 *
 */
@RestController

@RequestMapping(RoutingConstant.BASE_URL)
public class FileUploadController {

	/** The Constant LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

	/** The fileUploadService */
	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * For Single file Upload.
	 * 
	 * @param filePart
	 *
	 * @return Flux
	 * 
	 */
	@PostMapping(value = RoutingConstant.UPLOAD_SINGLE_FILEPART, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Mono<List<StockResponse>> upload(
			@RequestPart(value = RoutingConstant.File, required = false) FilePart filePart) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.UPLOAD_METHOD);
		Flux<StockResponse> latestFeed = null;
		if (filePart == null) {
			throw new FileHeaderNotFound(Constant.FILE_HEADER_NOT_FOUND);
		}
		if (filePart.filename().isEmpty()) {
			throw new FileIsNotFoundException(Constant.FILE_NOT_FOUND);
		}
		latestFeed = fileUploadService.readBySingleFile(filePart)
				.doOnError(error -> new FileIsNotFoundException(Constant.FILE_NOT_FOUND));
		return latestFeed.distinct().collectList()
				.doOnError(er -> LOGGER.error(LoggerConstants.ERROR_WHILE_FETCH_TO_CONTROLLER + er));
	}

	/**
	 * For Multiple file Upload.
	 * 
	 * @param filePartMap
	 * 
	 * @return Flux
	 * 
	 */
	@PostMapping(value = RoutingConstant.UPLOAD_MULTIPLE_FILEPART, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Mono<List<StockResponse>> uploadFileMap(@Valid @RequestBody Mono<MultiValueMap<String, Part>> filePartMap) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.UPLOAD_MULTIPLE_FILE_METHOD);
		Flux<StockResponse> latestFeed = fileUploadService.readByMultipleFile(filePartMap);
		return latestFeed.distinct().collectList()
				.doOnError(er -> LOGGER.error(LoggerConstants.ERROR_WHILE_FETCH_MULTIPLE_FILE_TO_CONTROLLER + er));
	}
}
