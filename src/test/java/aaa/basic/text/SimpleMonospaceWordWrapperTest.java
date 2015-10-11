package aaa.basic.text;

import aaa.basis.text.SimpleMonospaceWordWrapper;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class SimpleMonospaceWordWrapperTest {

	@SuppressWarnings("nls")
	@Test
	public void splitByMaxLengthTest() {
		assertEquals(asList("aaa", "aaa", "aaa", "aa"), SimpleMonospaceWordWrapper.builder()
				.delimiter(" ")
				.maxLength(MAX_LENGTH_FOR_SPLIT_3)
				.separatorChars("\n\r \t")
				.build()
				.apply("aaa\naaa\naaa\naa"));

		assertEquals(asList("aaa", "aaa", "aaa", "aa"), SimpleMonospaceWordWrapper.builder()
				.delimiter(" ")
				.maxLength(MAX_LENGTH_FOR_SPLIT_3)
				.separatorChars("\n\r \t")
				.build()
				.apply("aaaaaaaaaaa"));

		assertEquals(asList("aa bb", "aaaa", "a", "aaaa"), SimpleMonospaceWordWrapper.builder()
				.delimiter(" ")
				.maxLength(MAX_LENGTH_FOR_SPLIT_5)
				.separatorChars("\n\r \t")
				.build()
				.apply("aa bb aaaa a aaaa"));
	}

	static final int MAX_LENGTH_FOR_SPLIT_3 = 3;
	static final int MAX_LENGTH_FOR_SPLIT_5 = 5;

}
