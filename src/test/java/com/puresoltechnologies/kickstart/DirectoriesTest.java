/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 ***************************************************************************/

package com.puresoltechnologies.kickstart;

import java.io.File;

import org.junit.Test;

import com.puresoltechnologies.kickstart.Directories;

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
