package com.puresol.kickstart;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import junit.framework.TestCase;

public class KickStartTest extends TestCase {

	@Test
	public void testMain() {
		try {
			Class<?> clazz = KickStart.class;
			Method mainMethod = clazz.getMethod("main", String[].class);
			Object params[] = new Object[1];
			String args[] = new String[] {};
			params[0] = (args);
			mainMethod.invoke(null, params);
		} catch (SecurityException e) {
			e.printStackTrace();
			fail("No exception was expected!");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail("No exception was expected!");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail("No exception was expected!");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("No exception was expected!");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("No exception was expected!");
		}

	}
}
