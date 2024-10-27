package br.com.golden.raspberry.awards.util;

import static io.micrometer.common.util.StringUtils.isBlank;

import org.apache.commons.csv.CSVFormat;
import org.springframework.web.multipart.MultipartFile;

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
