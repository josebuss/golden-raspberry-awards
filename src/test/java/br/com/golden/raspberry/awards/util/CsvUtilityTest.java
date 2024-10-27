package br.com.golden.raspberry.awards.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class CsvUtilityTest {

	@Test
	void testNoCsvFormat() {
		var multipartFile = new MockMultipartFile("file","teste", "text/csv", "".getBytes());
		assertFalse(CsvUtility.noCsvFormat(multipartFile));
		
		multipartFile = new MockMultipartFile("file","teste", "text/txt", "".getBytes());
		assertTrue(CsvUtility.noCsvFormat(multipartFile));
	}
	
	@Test
	void testConvertToBoolean() {
		assertTrue(CsvUtility.convertToBoolean("true"));
		assertTrue(CsvUtility.convertToBoolean("yes"));
		
		assertFalse(CsvUtility.convertToBoolean("false"));
		assertFalse(CsvUtility.convertToBoolean("no"));
		assertFalse(CsvUtility.convertToBoolean(null));
		assertFalse(CsvUtility.convertToBoolean(""));
		
		assertNull(CsvUtility.convertToBoolean("outra_coisa"));
	}
	@Test
	void testConvertToInteger() {
		assertEquals(1, CsvUtility.convertToInteger("1"));
		assertNull(CsvUtility.convertToInteger(""));
		assertNull(CsvUtility.convertToInteger(null));
	}

}
