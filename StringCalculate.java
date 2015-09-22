package stringcalculate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculate {

	private static final String USER_DEFINED_DELIMITERS_PATTERN = "^//(.)\n(.*)";
	private static final String DEFAULT_DELIMITERS = ",|\n";
	private static final int REGEX_GROUP_NUMBER_OF_USER_DEFINED_DELIMITER = 1;
	private static final int REGEX_GROUP_NUMBER_OF_NUMBER_STRING = 2;

	public int run(String numbers) throws Exception {
		Matcher m = Pattern.compile(USER_DEFINED_DELIMITERS_PATTERN).matcher(numbers);
		String delimiters = getDelimiters(m);
		String numberString = getNumberString(numbers, m.reset());
		
		return getSum(getAllNumbers(numberString,delimiters));
	}

	private String getNumberString(String numbers, Matcher m) {
		if (m.find()) {
			return m.group(REGEX_GROUP_NUMBER_OF_NUMBER_STRING);
		}
		return numbers;
	}

	private String getDelimiters(Matcher m) {
		if (m.find()) {
			return Pattern.quote(m.group(REGEX_GROUP_NUMBER_OF_USER_DEFINED_DELIMITER));
		}
		return DEFAULT_DELIMITERS;
	}

	private String[] getAllNumbers(String numbers, String delimiter) {
		if (numbers.isEmpty()) {
			return new String[0];
		}
		return numbers.split(delimiter);
	}

	private int getSum(String[] allNumbers) throws Exception {
		int sum = 0;
		StringBuffer error = new StringBuffer();
		for (String number : allNumbers) {
			int num = toInt(number, error);
			sum += num;
		}
		
		if(error.length() > 0) {
			throw new RuntimeException("Negatives not allowed: " + error.toString());
		}
		return sum;
	}

	private int toInt(String numbers, StringBuffer error) {
		int num = Integer.parseInt(numbers);
		if (num < 0) {
			error.append(" " + numbers);
		}
		if (num > 1000) {
			return 0;
		}
		
		return num;
	}

}
