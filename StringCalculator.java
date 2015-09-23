package stringcalculate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

	private static final String REGEX_MULTIPLE_DELIMITERS = "\\[(.+?)\\]";
	private static final int IGNORED_MAX_VALUE = 1000;
	//private static final String USER_DEFINED_DELIMITERS_PATTERN = "^//(.)\n(.*)";
	//private static final String MULTI_USER_DEFINED_DELIMITERS_PATTERN = "^//((.)|(\\[(.+)\\]))\n(.*)";
	private static final String MULTI_USER_DEFINED_DELIMITERS_PATTERN = "^//((.)|(\\[(.+?)\\])+)\n(.*)";
	private static final String DEFAULT_DELIMITERS = ",|\n";
	private static final int REGEX_GROUP_USER_DEFINED_SINGLE_DELIMITER = 2;
	private static final int REGEX_GROUP_GIVEN_NUMBERS = 5;

	//main function of the class 
	public int Add(String input) {
		//Matcher m = Pattern.compile(USER_DEFINED_DELIMITERS_PATTERN).matcher(input);
		Matcher m = Pattern.compile(MULTI_USER_DEFINED_DELIMITERS_PATTERN).matcher(input);
		String delimiters = getDelimiters(m);
		String numbers = extractNumbers(input, m.reset());
		
		return getSummary(getAllNumbers(numbers,delimiters));
	}

	private String extractNumbers(String numbers, Matcher m) {
		if (m.find()) {
			return m.group(REGEX_GROUP_GIVEN_NUMBERS);
		}
		return numbers;
	}

	private String getDelimiters(Matcher m) {
		if (m.find()) {
			return getUserDefinedDelimiters(m);
		}
		return DEFAULT_DELIMITERS;
	}

	private String getUserDefinedDelimiters(Matcher m) {
		if(hasUserDefinedSingleDelimiter(m))
			return getSingleUserDefinedDelimiter(m);
		else
			return getNonSingleUserDefinedDelimiter(m);
	}

	private String getNonSingleUserDefinedDelimiter(Matcher m) {
		String delimiters = quote(m, 1);
		Matcher delim = Pattern.compile(REGEX_MULTIPLE_DELIMITERS).matcher(delimiters);
		StringBuffer sb = new StringBuffer();
		
		while (delim.find()) {
			getMultipleDelimiters(delim, sb);
		}
		
		return sb.toString();
	}

	private void getMultipleDelimiters(Matcher delim, StringBuffer sb) {
		if (sb.length() == 0) {
			sb.append(quote(delim, 1));
		} else {
			sb.append("|").append(quote(delim, 1));
		}
	}

	private String quote(Matcher delim, int group) {
		return Pattern.quote(delim.group(group));
	}

	private String getSingleUserDefinedDelimiter(Matcher m) {
		return quote(m, REGEX_GROUP_USER_DEFINED_SINGLE_DELIMITER);
	}

	private boolean hasUserDefinedSingleDelimiter(Matcher m) {
		return m.group(REGEX_GROUP_USER_DEFINED_SINGLE_DELIMITER) != null;
	}

	private String[] getAllNumbers(String numbers, String delimiter) {
		return (numbers.isEmpty()) ? (new String[0]) : numbers.split(delimiter);
	}

	private int getSummary(String[] allNumbers) {
		int sum = 0;
		StringBuffer error = new StringBuffer();
		for (String number : allNumbers) {
			int num = parseInteger(number, error);
			sum += num;
		}
		
		//if the number is less than zero, the error will throw exception
		throwRuntimeExceptionIfNegatives(error);
		return sum;
	}

	private void throwRuntimeExceptionIfNegatives(StringBuffer error) {
		if(error.length() > 0) {
			throw new RuntimeException("Negatives not allowed:" + error.toString());
		}
	}

	private int parseInteger(String numbers, StringBuffer error) {
		int num = Integer.parseInt(numbers);
		//the negative number is not allowed and raise exception
		if (num < 0) {
			error.append(" " + numbers);
		}
		return checkMaximumNumberIgnored(num);
	}

	private int checkMaximumNumberIgnored(int num) {
		//if the number is greater than 1000, should ignore it
		return (num > IGNORED_MAX_VALUE) ? 0 : num;
	}

}
