package com.filestockstorageservice.fileconvertor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;

import com.filestockstorageservice.constants.Constant;
import com.filestockstorageservice.constants.LoggerConstants;
import com.filestockstorageservice.model.ProcessStocks;
import com.filestockstorageservice.model.StockDetails;
import com.filestockstorageservice.model.StockRecords;
import com.filestockstorageservice.util.CommonUtil;

import reactor.core.publisher.Flux;

/**
 * The Class JsonReader
 * 
 * @author harsh.jain
 *
 */
public class TextReader implements FileType {

	static final Logger LOGGER = LoggerFactory.getLogger(TextReader.class);

	/**
	 * file reader
	 * 
	 * @param filePart
	 * 
	 * @return Flux
	 * 
	 */
	@Override
	public Flux<StockRecords> fileReader(FilePart filePart) {
		return this.processJsonReader(filePart);
	}

	/**
	 * @param The filePart
	 * 
	 * @return
	 * 
	 */
	private Flux<StockRecords> processJsonReader(FilePart filePart) {
		return filePart.content().map(dataBuffer -> {
			byte[] bytes = new byte[dataBuffer.readableByteCount()];
			dataBuffer.read(bytes);
			DataBufferUtils.release(dataBuffer);
			return new String(bytes, StandardCharsets.UTF_8);
		}).map(this::processDataToObject).flatMapIterable(Function.identity());

	}

	private List<StockRecords> processDataToObject(String getData) {
		String getDataLine[] = getData.split(Constant.PROCESS_SPILT);
		String[] parseGetDataLine = Arrays.copyOfRange(getDataLine, 1, getDataLine.length);
		LOGGER.info(LoggerConstants.PROCESS_DATA_START);
		List<StockRecords> stockList = Arrays.stream(parseGetDataLine).map((process) -> {
			StockRecords stockRecords = new StockRecords();
			try {
				stockRecords.setListOfSymbols(new ArrayList<StockDetails>());
				String processStream[] = process.split(Constant.COMMA_SPLIT);
				StockDetails stockDetails = new StockDetails();
				ProcessStocks processStocks = CommonUtil.processStocks(processStream);
				stockDetails.setOpenPrice(processStocks.getOpenPrice());
				stockDetails.setHighestPrice(processStocks.getHighestPrice());
				stockDetails.setLow(processStocks.getLow());
				stockDetails.setQuantity(processStocks.getQuantity().replaceAll("\\.0*$", ""));
				stockDetails.setLastPrice(processStocks.getLastPrice());
				stockDetails.setCurrentPrice(processStocks.getCurrentPrice());
				stockDetails.setTimeStamp(CommonUtil.currentTimeStamp());
				stockRecords.getListOfSymbols().add(stockDetails);
				stockRecords.setSymbolName(processStocks.getSymbolName());
				stockRecords.setSymbolId(processStocks.getSymbolId());
				String metaId = CommonUtil.getUniqueId(
						processStocks.getSymbolName().replace(Constant.REMOVE_QUOTES, Constant.EMPTY),
						processStocks.getSymbolId());
				stockRecords.setMetaId(metaId);

				LOGGER.info(LoggerConstants.PROCESS_DATA_IN_PROCESSING + process);

			} catch (Exception e) {
				LOGGER.error(LoggerConstants.PROCESS_DATA_IN_COMPLETE + e.getMessage());

			}
			return stockRecords;

		}).collect(Collectors.toList());
		LOGGER.info(LoggerConstants.PROCESS_DATA_SUCCESS);

		return stockList;
	}

	/**
	 * Instantiates a new JsonReader.
	 */
	public TextReader() {
		super();
	}

}
