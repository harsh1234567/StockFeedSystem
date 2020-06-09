package com.filestockstorageservice.serviceImpl;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.filestockstorageservice.constants.Constant;
import com.filestockstorageservice.constants.LoggerConstants;
import com.filestockstorageservice.exception.FileIsNotFoundException;
import com.filestockstorageservice.exception.InValidFileFound;
import com.filestockstorageservice.fileconvertor.CsvReader;
import com.filestockstorageservice.fileconvertor.FileConvertor;
import com.filestockstorageservice.fileconvertor.TextReader;
import com.filestockstorageservice.model.StockRecords;
import com.filestockstorageservice.model.StockResponse;
import com.filestockstorageservice.repository.StockDetailRepository;
import com.filestockstorageservice.service.FileUploadService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The Class FileUploadServiceImpl.
 * 
 * @author harsh.jain
 *
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

	/** The topicStockList */
	@Value(value = "${message.topic.stock.list}")
	private String topicStockList;

	/** The Constant LOGGER */
	static final Logger LOGGER = LoggerFactory.getLogger(FileUploadServiceImpl.class);

	/** The fileConvertor. */
	@Autowired
	private FileConvertor fileConvertor;

	/** stockDetailRepository */
	@Autowired
	private StockDetailRepository stockDetailRepository;

	@Autowired
	KafkaTemplate<String, StockResponse> kafkaJsontemplate;

	/**
	 * process the single file
	 *
	 * @param filePart
	 * 
	 * @return the Flux
	 */
	@Override
	public Flux<StockResponse> readBySingleFile(FilePart filePart) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.READ_BY_SINGLE_FILE_METHOD);
		if (filePart.filename().isEmpty()) {
			throw new FileIsNotFoundException(Constant.FILE_NOT_FOUND);
		}
		String extensions = StringUtils.getFilenameExtension(filePart.filename());
		if (extensions.equalsIgnoreCase(Constant.CSV)) {
			LOGGER.info(LoggerConstants.CREATE_CSV_FILE);
			fileConvertor.setFile(new CsvReader());
		} else if (extensions.equalsIgnoreCase(Constant.TXT)) {
			LOGGER.info(LoggerConstants.CREATE_JSON_FILE);
			fileConvertor.setFile(new TextReader());
		}else {
			throw new InValidFileFound("invalid file");
		}
		Flux<StockRecords> stockRecords = fileConvertor.createArchiveFile(filePart).switchIfEmpty(x -> {
			LOGGER.error(Constant.ERROR_FILE_PROCESSING);
		});
		return persistStock(stockRecords).switchIfEmpty(x -> {
			LOGGER.error(LoggerConstants.ERORR_DATA_SAVE, LoggerConstants.ERROR_OCCURRED_PERSISTING_DATA);
		});
	}

	/**
	 * process the Multiple file
	 *
	 * @param filePartMap
	 * @return the Flux
	 */
	@Override
	public Flux<StockResponse> readByMultipleFile(Mono<MultiValueMap<String, Part>> filePartMap) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.READ_BY_MUTIPLE_FILE);
		return filePartMap
				.flatMapIterable(map -> map.keySet().stream().filter(key -> key.equals(Constant.FILES))
						.flatMap(key -> map.get(key).stream().filter(part -> part instanceof FilePart))
						.collect(Collectors.toList()))
				.flatMap(part -> readBySingleFile((FilePart) part))
				.doOnError(error -> new FileIsNotFoundException(Constant.FILE_NOT_FOUND));
	}

	/**
	 * persist stock into database
	 *
	 * @param stockDetails
	 * 
	 * @return the Flux.
	 */
	private Flux<StockResponse> persistStock(Flux<StockRecords> stockRecords) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.SAVE_INFO);
		return stockRecords.flatMap(stocks -> {
			LOGGER.info(String.format(Constant.FETCH_STOCK_DATA, stocks));
			stockDetailRepository.saveStockRecords(stocks);
			return preparestockResponse(stocks);
		}).onErrorResume((a) -> {
			LOGGER.error(String.format(Constant.FETCH_STOCK_DATA, stockRecords));
			return Mono.empty();
		});

	}

	/**
	 * persist prepare stock data before return response.
	 *
	 * @param stockRecords
	 * 
	 * @return the Mono.
	 */
	private Mono<StockResponse> preparestockResponse(StockRecords stockRecords) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.PREPARE_STOCK_RESPONSE);
		StockResponse stockResponse = new StockResponse();
		stockResponse.setLatestFeed((stockRecords.getListOfSymbols().get(0)));
		stockResponse.setSymbol(stockRecords.getSymbolName());
		stockResponse.setSymbolId(stockRecords.getSymbolId());
		sendEvent(stockResponse);
		return Mono.just(stockResponse);
	}

	/**
	 * send data through kafka event .
	 *
	 * @param stockResponse
	 * 
	 * @return .
	 */
	private void sendEvent(StockResponse stockResponse) {
		LOGGER.info(LoggerConstants.METHOD_INVOCATION, LoggerConstants.SEND_EVENT);
		kafkaJsontemplate.send(topicStockList, stockResponse);
	}

}
