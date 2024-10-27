package br.com.golden.raspberry.awards.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import br.com.golden.raspberry.awards.converter.FilmeCsvConverter;
import br.com.golden.raspberry.awards.entity.FilmeEntity;
import br.com.golden.raspberry.awards.repository.FilmeRepository;
import br.com.golden.raspberry.awards.util.ValidationException;
import br.com.golden.raspberry.awards.validator.FilmeValidator;

@ExtendWith(MockitoExtension.class)
class UploadProdutoresPremiadosServiceImplTest {

	@InjectMocks
	private UploadProdutoresPremiadosServiceImpl service;
	@Mock
	private FilmeRepository filmeRepository;
	@Mock
	private FilmeValidator filmeValidator;

	@Test
	void testSucesso() throws IOException {
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(multipartFile.getContentType()).thenReturn("text/csv");
		when(multipartFile.getInputStream()).thenReturn(InputStream.nullInputStream());

		try (MockedConstruction<FilmeCsvConverter> mocked = mockConstruction(FilmeCsvConverter.class,
				(converter, context) -> {
					when(converter.getHeaderNames())
							.thenReturn(List.of("year", "title", "studios", "producers", "winner"));
					when(converter.csvToEntities()).thenReturn(List.of(mock(FilmeEntity.class)));

				})) {

			service.uploadFile(multipartFile);
		}

		verify(filmeValidator).validate(any());
		verify(filmeRepository).save(any());
	}

	@Test
	void testErroValidade() throws IOException {
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(multipartFile.getContentType()).thenReturn("text/csv");
		when(multipartFile.getInputStream()).thenReturn(InputStream.nullInputStream());

		Mockito.doThrow(new ValidationException("Erro")).when(filmeValidator).validate(any());

		try (MockedConstruction<FilmeCsvConverter> mocked = mockConstruction(FilmeCsvConverter.class,
				(converter, context) -> {
					when(converter.getHeaderNames())
							.thenReturn(List.of("year", "title", "studios", "producers", "winner"));
					when(converter.csvToEntities()).thenReturn(List.of(mock(FilmeEntity.class)));

				})) {

			assertThrows(ValidationException.class, () -> service.uploadFile(multipartFile));
		}

		verify(filmeValidator).validate(any());
		verifyNoInteractions(filmeRepository);
	}

	@Test
	void testErroValidadeMaxErros() throws IOException {
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(multipartFile.getContentType()).thenReturn("text/csv");
		when(multipartFile.getInputStream()).thenReturn(InputStream.nullInputStream());

		Mockito.doThrow(new ValidationException("Erro")).when(filmeValidator).validate(any());

		try (MockedConstruction<FilmeCsvConverter> mocked = mockConstruction(FilmeCsvConverter.class,
				(converter, context) -> {
					when(converter.getHeaderNames())
							.thenReturn(List.of("year", "title", "studios", "producers", "winner"));
					FilmeEntity filmeEntity = mock(FilmeEntity.class);
					when(converter.csvToEntities()).thenReturn(List.of(filmeEntity, filmeEntity, filmeEntity,
							filmeEntity, filmeEntity, filmeEntity, filmeEntity, filmeEntity, filmeEntity, filmeEntity));

				})) {

			assertThrows(ValidationException.class, () -> service.uploadFile(multipartFile));
		}

		verify(filmeValidator, times(10)).validate(any());
		verifyNoInteractions(filmeRepository);
	}

	@Test
	void testErroValidadeFormat() throws IOException {
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(multipartFile.getContentType()).thenReturn("text/txt");

		assertThrows(ValidationException.class, () -> service.uploadFile(multipartFile));

		verifyNoInteractions(filmeValidator);
		verifyNoInteractions(filmeRepository);
	}

}
