package aaa.web.html;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.replace;

public class HtmlUtils {

	static Map.Entry<String, String> entry(String key, String value) {
		return new AbstractMap.SimpleEntry<>(key, value);
	}

	@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
	@AllArgsConstructor
	public enum StringToHtmlType {
		PLAIN_HTML_TEXT(asList(	entry("&", "&amp;"),
								entry("<", "&lt;"),
								entry(">", "&gt;"),
								entry("\"", "&quot;"),
								entry("\r\n", "<br>"),
								entry("\n", "<br>"),
								entry("\r", "<br>"),
								entry("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"))),
		INPUT_VALUE(Collections.EMPTY_LIST),
		BYPASS_TAGS(asList(	entry("\r\n", "<br>"),
							entry("\n", "<br>"),
							entry("\r", "<br>"),
							entry("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")));

		List<Map.Entry<String, String>> rules;
	}

	/** Автоматическое экранирование/замена недопустимых символов */
	public static String doReplace(String value, StringToHtmlType type) {
		String result = value;
		if (isNotEmpty(result) && type.rules != null) {
			for (Map.Entry<String, String> entry : type.rules) {
				result = replace(result, entry.getKey(), entry.getValue());
			}
		}
		return result;
	}

}
