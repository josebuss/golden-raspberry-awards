package br.com.golden.raspberry.awards.util;

import static io.micrometer.common.util.StringUtils.isBlank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.classfile.instruction.SwitchCase;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import br.com.golden.raspberry.awards.entity.FilmeEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CsvUtility {
	
	public static final String TYPE = "text/csv";
	public static final CSVFormat DEFAULT_FORMAT = CSVFormat.DEFAULT //
			.withDelimiter(';') //
			.withIgnoreEmptyLines() //
			.withFirstRecordAsHeader() //
			.withIgnoreHeaderCase() //
			.withTrim();

	public static boolean noCsvFormat(MultipartFile file) {
		return !TYPE.equals(file.getContentType());
	}

	public static Boolean convertToBoolean(String value) {
		if (isBlank(value)) {
			return false;
		}
		switch (value) {
		case "true":
		case "yes":
			return true;
		case "false":
		case "no":
			return false;
		default:
			return null;
		}
	}

	public static Integer convertToInteger(String value) {
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
