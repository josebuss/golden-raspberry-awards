package br.com.golden.raspberry.awards.validator;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.golden.raspberry.awards.entity.FilmeEntity;
import br.com.golden.raspberry.awards.repository.FilmeRepository;
import br.com.golden.raspberry.awards.util.ValidationException;

@Component
public class FilmeValidatorImpl implements FilmeValidator {

	private static List<String> CSV_HEADER = List.of("year", "title", "studios", "producers", "winner");

	@Autowired
	private FilmeRepository filmeRepository;

	@Override
	public void validate(FilmeEntity entity) {
		try {
			checkIsNull(entity.getAno(), "year");
			checkIsNull(entity.getTitulo(), "title");
			checkIsNull(entity.getStudios(), "studios");
			checkIsNull(entity.getProdutor(), "producers");
			checkIsNull(entity.getVencedor(), "winner");

			validarAno(entity.getAno());

			if (filmeRepository.existsByProdutorAndTitulo(entity.getProdutor(), entity.getTitulo())) {
				throw new ValidationException(format("Produtor {0} já cadastrado para o filme {1}",
						entity.getProdutor(), entity.getTitulo()));
			}
		} catch (Exception e) {
			throw new ValidationException(format("Erro na linha {0}: {1}", entity.getLinha(), e.getMessage()), e);
		}
	}

	@Override
	public void validateCsvHeader(List<String> header) {
		List<String> camposFaltantes = CSV_HEADER.stream() //
				.map(campo -> header.contains(campo) ? null : campo) //
				.filter(Objects::nonNull) //
				.collect(toList());
		if (!isEmpty(camposFaltantes)) {
			throw new ValidationException(
					"Existem campos não informados no arquivo: " + String.join(", ", camposFaltantes));
		}
	}

	private void validarAno(Integer ano) {
		if (ano.toString().length() != 4) {
			throw new ValidationException("O ano deve conter 4 caracteres");

		}
	}

	private void checkIsNull(Object value, String msg) {
		if ((value instanceof String && isBlank((String)value)) || isNull(value)) {
			throw new ValidationException(format("Campo {0} não foi informado", msg));
		}
	}

}
