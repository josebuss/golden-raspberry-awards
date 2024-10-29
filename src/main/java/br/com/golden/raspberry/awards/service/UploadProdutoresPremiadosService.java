package br.com.golden.raspberry.awards.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface UploadProdutoresPremiadosService {

	void uploadFile(MultipartFile file) throws IOException;
	
	void initialLoad() throws IOException;
	
}
