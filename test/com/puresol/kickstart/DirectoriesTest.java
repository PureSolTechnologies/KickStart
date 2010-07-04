package com.puresol.kickstart;

import java.io.File;

import org.junit.Test;

import junit.framework.TestCase;

public class DirectoriesTest extends TestCase {

	/**
	 * This test checks the preconditions for a working Directories class like
	 * non empty system properties.
	 */
	@Test
	public void testPreConditions() {
		assertFalse(System.getProperty("user.home").isEmpty());
		assertFalse(System.getProperty("user.dir").isEmpty());
	}

	@Test
	public void testInstallationDirectory() {
		File installDir = Directories.getInstallationDirectory(
				DirectoriesTest.class, true);
		assertNotNull(installDir);
		assertTrue(installDir.exists());
	}

	@Test
	public void testExecutionDirectory() {
		File execDir = Directories.getExecutionDirectory();
		assertNotNull(execDir);
		assertTrue(execDir.exists());
	}

	@Test
	public void testUserDirectory() {
		File userDir = Directories.getUserDirectory();
		assertNotNull(userDir);
		assertTrue(userDir.exists());
	}

}
