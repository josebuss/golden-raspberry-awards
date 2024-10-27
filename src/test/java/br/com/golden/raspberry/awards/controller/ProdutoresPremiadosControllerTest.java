package br.com.golden.raspberry.awards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import br.com.golden.raspberry.awards.Application;
import br.com.golden.raspberry.awards.repository.FilmeRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Application.class)
class ProdutoresPremiadosControllerTest {

	@Autowired
	private ProdutoresPremiadosController controller;
	@Autowired
	private FilmeRepository filmeRepository;

	@Test
	void testSucesso() {
		filmeRepository.deleteAll();

		controller.uploadFile(getFile("testSucesso.csv"));

		var all = filmeRepository.findAll();
		assertEquals(472, all.size());

		var produtoresPremiados = controller.getProdutoresPremiados();

		var min = produtoresPremiados.getBody().getMin();
		assertEquals(3, min.size());
		assertEquals("Joel Silver", min.get(0).getProducer());
		assertEquals(1, min.get(0).getInterval());
		assertEquals(1990, min.get(0).getPreviousWin());
		assertEquals(1991, min.get(0).getFollowingWin());

		assertEquals("Menahem Golan", min.get(1).getProducer());
		assertEquals(1, min.get(1).getInterval());
		assertEquals(1986, min.get(1).getPreviousWin());
		assertEquals(1987, min.get(1).getFollowingWin());

		assertEquals("Yoram Globus", min.get(2).getProducer());
		assertEquals(1, min.get(2).getInterval());
		assertEquals(1986, min.get(2).getPreviousWin());
		assertEquals(1987, min.get(2).getFollowingWin());

		var max = produtoresPremiados.getBody().getMax();
		assertEquals(1, max.size());
		assertEquals("Matthew Vaughn", max.get(0).getProducer());
		assertEquals(13, max.get(0).getInterval());
		assertEquals(2002, max.get(0).getPreviousWin());
		assertEquals(2015, max.get(0).getFollowingWin());
	}

	@Test
	void testErroCampoFaltando() {
		var output = controller.uploadFile(getFile("testErroCampoFaltando.csv"));
		assertEquals(BAD_REQUEST, output.getStatusCode());
		assertTrue(output.getBody().equals("Existem campos não informados no arquivo: producers, winner"));
	}

	@Test
	void testErroArquivoVazio() {
		var output = controller.uploadFile(getFile("testErroArquivoVazio.csv"));
		assertEquals(BAD_REQUEST, output.getStatusCode());
		assertTrue(output.getBody().contains("Existem campos não informados no arquivo"));
	}

	@Test
	void testErroValidacaoEntity() {
		var output = controller.uploadFile(getFile("testErroValidacaoEntity.csv"));
		assertEquals(BAD_REQUEST, output.getStatusCode());
		assertTrue(output.getBody().contains("Erro na linha 2: O ano deve conter 4 caracteres"));
		assertTrue(output.getBody().contains("Erro na linha 3: Campo year não foi informado"));
		assertTrue(output.getBody().contains("Erro na linha 4: Campo year não foi informado"));
		assertTrue(output.getBody().contains("Erro na linha 5: Produtor Allan Carr já cadastrado para o filme Can't Stop the Music"));
		assertTrue(output.getBody().contains("Erro na linha 6: Campo title não foi informado"));
		assertTrue(output.getBody().contains("Erro na linha 7: Campo studios não foi informado"));
		assertTrue(output.getBody().contains("Erro na linha 9: Campo winner não foi informado"));
	}

	private MultipartFile getFile(String nomeArquivo) {
		var resource = new ClassPathResource("importacao/" + nomeArquivo);
		try {
			return new MockMultipartFile("file", "testSucesso.csv", "text/csv", resource.getInputStream());
		} catch (IOException e) {
			Assertions.fail("Erro ao abrir o arquivo");
		}
		return null;
	}
}