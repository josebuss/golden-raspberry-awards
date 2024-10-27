package br.com.golden.raspberry.awards.service;

import static br.com.golden.raspberry.awards.util.Utils.getLineMessageSeparator;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.golden.raspberry.awards.converter.FilmeCsvConverter;
import br.com.golden.raspberry.awards.repository.FilmeRepository;
import br.com.golden.raspberry.awards.util.CsvUtility;
import br.com.golden.raspberry.awards.util.ValidationException;
import br.com.golden.raspberry.awards.validator.FilmeValidator;

@Service
public class UploadProdutoresPremiadosServiceImpl implements UploadProdutoresPremiadosService {

	private static final int MAX_ERRORS = 10;
	
	@Autowired
	private FilmeRepository filmeRepository;
	@Autowired
	private FilmeValidator filmeValidator;
	

	@Transactional
	@Override
	public void uploadFile(MultipartFile file) throws IOException {
		if (CsvUtility.noCsvFormat(file)) {
			throw new ValidationException("O arquivo fornecido não possui um formato válido (.csv)");
		}
		try (var converter = new FilmeCsvConverter(file.getInputStream())) {
			filmeValidator.validateCsvHeader(converter.getHeaderNames());

			var erros = new ArrayList<String>();
			for (var entity : converter.csvToEntities()) {
				try {
					filmeValidator.validate(entity);
					filmeRepository.save(entity);
				} catch (Exception e) {
					erros.add(e.getMessage());
					if (erros.size() >= MAX_ERRORS) {
						break;
					}
				}
			}
			if (!isEmpty(erros)) {
				throw new ValidationException("O arquivo importado contém erros, favor verificar: " //
						+ getLineMessageSeparator() + String.join(getLineMessageSeparator(), erros));
			}
		}
	}

}
