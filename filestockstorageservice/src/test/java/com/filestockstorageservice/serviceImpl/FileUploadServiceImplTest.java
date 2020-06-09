package com.filestockstorageservice.serviceImpl;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.codec.multipart.FilePart;

import com.filestockstorageservice.fileconvertor.FileConvertor;
import com.filestockstorageservice.model.StockDetails;
import com.filestockstorageservice.model.StockRecords;
import com.filestockstorageservice.model.StockResponse;
import com.filestockstorageservice.service.MyFilePart;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadServiceImplTest {

	@InjectMocks
	FileUploadServiceImpl fileUploadServiceImpl;

	@Mock
	FileConvertor fileConvertor;

	@Test
	public void readBySingleFileTest() {
		File file = new File(new File("").getAbsolutePath(),"resource/data.csv");
        FilePart filePart1 = mock(FilePart.class);
		Mockito.when(fileConvertor.createArchiveFile(Mockito.any())).thenReturn(Flux.just((stockRecords())));
		FilePart myFilePart =(FilePart) new MyFilePart().transferTo(file); 
		Flux<StockResponse> res = fileUploadServiceImpl.readBySingleFile(myFilePart);
		StepVerifier.create(res).assertNext(response -> {
			Assert.assertNotNull(response.getSymbol());
		}).verifyComplete();
}

	private StockRecords stockRecords() {
		StockRecords stockRecords = new StockRecords();
		List<StockDetails> listOfSymbols = new ArrayList<StockDetails>();
		stockRecords.setSymbolName("TATAMOTOR");
		stockRecords.setSymbolId("3");
		StockDetails stockDetails = new StockDetails();
		stockDetails.setOpenPrice("90");
		stockDetails.setHighestPrice("100");
		stockDetails.setLastPrice("80");
		stockDetails.setLow("100");
		stockDetails.setCurrentPrice("91");
		stockDetails.setQuantity("100");
		stockRecords.setLatestFeed(stockDetails);
		listOfSymbols.add(stockDetails);
		stockRecords.setListOfSymbols(listOfSymbols);
		return stockRecords;
	}
}
