package com.filestockstorageservice;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.filestockstorageservice.service.FileUploadService;

@SpringBootTest
class FilestockstorageserviceApplicationTests {
	
	@MockBean
	FileUploadService fileUploadService;
	
  //  Mockito.when(resourceRepository.save(Mockito.any())).thenReturn(getMonoResource());

	@Test
	void contextLoads() {
		
		File file = new File("test/resources/shared/uploads/blank.csv");
		//TemporaryFile temporaryFile = new TemporaryFile(file);

		/*
		 * //MultipartFormData.FilePart filePath = new MultipartFormData.FilePart(
		 * "file", "file.csv", file);
		 */
		}

}
