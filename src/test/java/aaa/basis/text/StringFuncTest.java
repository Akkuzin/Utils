package aaa.basis.text;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringFuncTest {

	@SuppressWarnings("nls")
	@Test
	public void substringAfterLastIfExistsTest() {
		assertEquals(null, StringFunc.substringAfterLastIfExists(null, "."));
		assertEquals("", StringFunc.substringAfterLastIfExists("", "."));
		assertEquals("", StringFunc.substringAfterLastIfExists("ASDQWEZX", null));
		assertEquals("", StringFunc.substringAfterLastIfExists("ASDQWEZX", ""));
		assertEquals("ASD", StringFunc.substringAfterLastIfExists("ASD", "."));
		assertEquals("QWE", StringFunc.substringAfterLastIfExists("ASD.QWE", "."));
		assertEquals("QWE", StringFunc.substringAfterLastIfExists(".QWE", "."));
		assertEquals("ZXC", StringFunc.substringAfterLastIfExists("ASD.QWE.ZXC", "."));
		assertEquals("", StringFunc.substringAfterLastIfExists("ASD.QWE.", "."));
		assertEquals("ZXC", StringFunc.substringAfterLastIfExists("ASDQWEZXC", "QWE"));
		assertEquals("", StringFunc.substringAfterLastIfExists("ASDQWE", "QWE"));
	}

}