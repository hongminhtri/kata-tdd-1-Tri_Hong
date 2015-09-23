package stringcalculate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestStringCalculator {
	private StringCalculator stringCalculator;
	
	@Before
	public void createStringCalculate() {
		// TODO Auto-generated method stub
		stringCalculator = new StringCalculator();
	}
	
	@Test
	public void shouldReturnZeroOnEmptyString() {
		assertEquals(0, stringCalculator.Add(""));
	}

	@Test
	public void shouldReturnOneNumber() {
		assertEquals(1, stringCalculator.Add("1"));
	}
	
	@Test
	public void shouldReturnSumForTwoNubmers() {
		assertEquals(3, stringCalculator.Add("1,2"));
	}
	
	@Test
	public void shouldReturnSumForMultipleNumbers() {
		assertEquals(6, stringCalculator.Add("1,2,3"));
	}
	
	@Test
	public void shouldHandleNewLine() {
		assertEquals(6, stringCalculator.Add("1\n2,3"));
	}
	
	@Test
	public void shouldSupportDifferentDelimiters() {
		assertEquals(5, stringCalculator.Add("//;\n2;3"));
	}
	
	@Rule public ExpectedException expectedEx = ExpectedException.none();
	@Test
	public void shouldThrowExceptionWhenNegatives() {
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("Negatives not allowed: -1 -2");
		stringCalculator.Add("-1,-2");
	}
	
	@Test
	public void shouldIgnoreNumbersBiggerThanLimit() {
		assertEquals(2, stringCalculator.Add("1001,2"));
	}
	
	@Test
	public void shouldHandleMultipleDelimitersWithAnyLength() {
		assertEquals(3, stringCalculator.Add("//[,,,]\n1,,,2"));
	}
	
	@Test
	public void shouldHandleMultipleDelimiters() {
		assertEquals(6, stringCalculator.Add("//[,][;;]\n1,2;;3"));
	}
}
