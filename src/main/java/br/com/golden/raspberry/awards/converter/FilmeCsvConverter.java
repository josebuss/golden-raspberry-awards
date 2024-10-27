package br.com.golden.raspberry.awards.converter;

import static br.com.golden.raspberry.awards.util.CsvUtility.convertToBoolean;
import static br.com.golden.raspberry.awards.util.CsvUtility.convertToInteger;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVParser;

import br.com.golden.raspberry.awards.entity.FilmeEntity;
import br.com.golden.raspberry.awards.util.CsvUtility;
import br.com.golden.raspberry.awards.util.ValidationException;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

public class FilmeCsvConverter implements Closeable {

	private static final List<String> DEFAULT_PRODUCERS = List.of("");
	private InputStream input;
	private BufferedReader reader;
	private CSVParser csvParser;
	@Getter
	private List<String> headerNames;

	public FilmeCsvConverter(InputStream inputStream) throws IOException {
		this.input = inputStream;
		this.reader = new BufferedReader(new InputStreamReader(this.input, StandardCharsets.UTF_8));
		this.csvParser = new CSVParser(reader, CsvUtility.DEFAULT_FORMAT);
		headerNames = csvParser.getHeaderNames();
	}

	@Override
	public void close() throws IOException {
		csvParser.close();
		reader.close();
		input.close();
	}

	public List<FilmeEntity> csvToEntities() {
		try {
			return csvParser.getRecords().stream() //
					.flatMap(rec -> {
						var produtores = getProdutores(rec.get("producers"));
						return produtores.stream() //
								.map(produtor -> {
									var entity = new FilmeEntity();
									entity.setAno(convertToInteger(rec.get("year")));
									entity.setTitulo(rec.get("title"));
									entity.setStudios(rec.get("studios"));
									entity.setVencedor(convertToBoolean(rec.get("winner")));
									entity.setProdutor(produtor);
									entity.setLinha(rec.getRecordNumber());
									return entity;
								});
					}).collect(toList());
		} catch (Exception e) {
			throw new ValidationException("CSV data is failed to parse: " + e.getMessage());
		}
	}

	private static List<String> getProdutores(String value) {
		return Arrays.asList(value.split(",|\\s+and\\s+")) //
				.stream() //
				.map(String::trim) //
				.filter(StringUtils::isNotBlank)//
				.collect(collectingAndThen(toList(), list -> list.isEmpty() ? DEFAULT_PRODUCERS : list));
	}

}
