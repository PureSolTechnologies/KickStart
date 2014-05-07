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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.puresoltechnologies.kickstart.KickStart;

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
