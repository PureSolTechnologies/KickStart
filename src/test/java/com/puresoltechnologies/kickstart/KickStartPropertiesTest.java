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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.puresoltechnologies.kickstart.Directories;
import com.puresoltechnologies.kickstart.KickStart;
import com.puresoltechnologies.kickstart.KickStartProperties;

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
							KickStart.class, true), "../lib"),
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
