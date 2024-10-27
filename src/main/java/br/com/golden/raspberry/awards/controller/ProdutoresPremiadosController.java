package br.com.golden.raspberry.awards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.golden.raspberry.awards.dto.ProdutoresPremiadosDTO;
import br.com.golden.raspberry.awards.service.GetProdutoresPremiadosService;
import br.com.golden.raspberry.awards.service.UploadProdutoresPremiadosService;

@RestController
@RequestMapping("/api/produtores-premiados")
public class ProdutoresPremiadosController {

	@Autowired
	private UploadProdutoresPremiadosService produtoresPremiadosService;
	@Autowired
	private GetProdutoresPremiadosService getProdutoresPremiadosService;

	@PostMapping()
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			produtoresPremiadosService.uploadFile(file);
			return ResponseEntity.status(HttpStatus.OK) //
					.body("Arquivo importado com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST) //
					.body(e.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<ProdutoresPremiadosDTO> getProdutoresPremiados() {
		return ResponseEntity.status(HttpStatus.OK) //
				.body(getProdutoresPremiadosService.getProdutoresPremiados());
	}

}
