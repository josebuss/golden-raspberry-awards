package br.com.golden.raspberry.awards.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import br.com.golden.raspberry.awards.dto.ProdutoresPremiadosDTO;

public interface UploadProdutoresPremiadosService {

	void uploadFile(MultipartFile file) throws IOException;
	
}
