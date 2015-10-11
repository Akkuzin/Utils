package aaa.type.interval;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class IntervalUtilsTest {

	@Test
	public void testPositive() throws ParseException {
		assertEquals(111 * 24 * 60 * 60 * 1000L, IntervalUtils.parseInterval("111")); //$NON-NLS-1$
		assertEquals((111 * 60 + 12) * 60 * 1000L, IntervalUtils.parseInterval("111:12")); //$NON-NLS-1$
		assertEquals((11 * 60 + 12) * 60 * 1000L, IntervalUtils.parseInterval("11:12")); //$NON-NLS-1$
		assertEquals(	((111 * 24 + 12) * 60 + 13) * 60 * 1000L,
						IntervalUtils.parseInterval("111 12:13")); //$NON-NLS-1$
		assertEquals(	11123456789L * 24 * 60 * 60 * 1000L,
						IntervalUtils.parseInterval("11123456789")); //$NON-NLS-1$
	}

	@Test
	public void testPositiveWithSpaces() throws ParseException {
		assertEquals(111 * 24 * 60 * 60 * 1000L, IntervalUtils.parseInterval("    111    ")); //$NON-NLS-1$
		assertEquals((111 * 60 + 12) * 60 * 1000L, IntervalUtils.parseInterval(" 111 \t : 12  ")); //$NON-NLS-1$
		assertEquals((11 * 60 + 12) * 60 * 1000L, IntervalUtils.parseInterval("	11  :  12\n   ")); //$NON-NLS-1$
		assertEquals(	((111 * 24 + 12) * 60 + 13) * 60 * 1000L,
						IntervalUtils.parseInterval("111 12:13")); //$NON-NLS-1$
		assertEquals(111 * 24 * 60 * 60 * 1000L, IntervalUtils.parseInterval("	\n111 	")); //$NON-NLS-1$
	}

	@Test(expected = ParseException.class)
	public void testNegative1() throws ParseException {
		assertEquals(-1, IntervalUtils.parseInterval("11:123")); //$NON-NLS-1$
	}

	@Test(expected = ParseException.class)
	public void testNegative2() throws ParseException {
		assertEquals(-1, IntervalUtils.parseInterval("XXX")); //$NON-NLS-1$
	}

	@Test(expected = ParseException.class)
	public void testNegative3() throws ParseException {
		assertEquals(-1, IntervalUtils.parseInterval("11:123 19")); //$NON-NLS-1$
	}

	@Test(expected = ParseException.class)
	public void testNegative4() throws ParseException {
		assertEquals(-1, IntervalUtils.parseInterval("11::12")); //$NON-NLS-1$
	}
}
