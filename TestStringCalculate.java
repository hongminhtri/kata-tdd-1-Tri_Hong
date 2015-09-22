package stringcalculate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestStringCalculate {
	private StringCalculate stringCalculate;
	
	@Before
	public void createStringCalculate() {
		// TODO Auto-generated method stub
		stringCalculate = new StringCalculate();
	}
	
	@Test
	public void shouldReturnZeroOnEmptyString() throws Exception {
		assertEquals(0, stringCalculate.run(""));
	}

	@Test
	public void shouldReturnOneNumber() throws Exception {
		assertEquals(1, stringCalculate.run("1"));
	}
	
	@Test
	public void shouldReturnSumForTwoNubmers() throws Exception {
		assertEquals(3, stringCalculate.run("1,2"));
	}
	
	@Test
	public void shouldReturnSumForMultipleNumbers() throws Exception {
		assertEquals(6, stringCalculate.run("1,2,3"));
	}
	
	@Test
	public void shouldHandleNewLine() throws Exception {
		assertEquals(6, stringCalculate.run("1\n2,3"));
	}
	
	@Test
	public void shouldSupportDifferentDelimiters() throws Exception {
		assertEquals(5, stringCalculate.run("//;\n2;3"));
	}
	
	@Rule public ExpectedException expectedEx = ExpectedException.none();
	@Test
	public void shouldThrowExceptionWhenNegatives() throws Exception {
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("Negatives not allowed: -1 -2");
		stringCalculate.run("-1,-2");
	}
	
	@Test
	public void shouldIgnoreNumbersBiggerThan1000() throws Exception {
		assertEquals(2, stringCalculate.run("1001,2"));
	}
}
