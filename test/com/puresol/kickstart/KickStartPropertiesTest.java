package com.puresol.kickstart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class KickStartPropertiesTest extends TestCase {

	@Test
	public void testSingleton() {
		try {
			KickStartProperties instance1 = KickStartProperties.getInstance();
			assertNotNull(instance1);
			KickStartProperties instance2 = KickStartProperties.getInstance();
			assertNotNull(instance2);
			assertSame(instance1, instance2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("No exception was expected.");
		} catch (IOException e) {
			e.printStackTrace();
			fail("No exception was expected.");
		}
	}

	@Test
	public void testContent() {
		try {
			KickStartProperties instance = KickStartProperties.getInstance();
			assertNotNull(instance);
			assertTrue(instance.isRecursiveJarSearch());
			List<File> jarDirectories = instance.getJarDirectories();
			assertEquals(1, jarDirectories.size());
			assertEquals(
					new File(Directories.getInstallationDirectory(
							KickStart.class, true), "lib"),
					jarDirectories.get(0));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("No exception was expected.");
		} catch (IOException e) {
			e.printStackTrace();
			fail("No exception was expected.");
		}
	}
}
