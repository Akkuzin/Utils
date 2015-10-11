package aaa.format;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.trim;

public class SafeParse {

	public static <T> T parse(String str, T defaultValue, Function<String, T> parser) {
		try {
			return parser.apply(trim(str));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Integer parseInt(String str, Integer defaultValue) {
		return parse(str, defaultValue, Integer::parseInt);
	}

	public static Long parseLong(String str, Long defaultValue) {
		return parse(str, defaultValue, Long::parseLong);
	}

	public static Byte parseByte(String str, Byte defaultValue) {
		return parse(str, defaultValue, Byte::parseByte);
	}

	public static BigInteger parseBigInteger(String str, BigInteger defaultValue) {
		return parse(str, defaultValue, BigInteger::new);
	}

	public static BigDecimal parseBigDecimal(String str, BigDecimal defaultValue) {
		return parse(str, defaultValue, BigDecimal::new);
	}
}
