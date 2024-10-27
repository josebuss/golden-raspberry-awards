package br.com.golden.raspberry.awards.validator;

import java.util.List;

import br.com.golden.raspberry.awards.entity.FilmeEntity;

public interface FilmeValidator {

	void validate(FilmeEntity entities);
	
	void validateCsvHeader(List<String> header);

}
