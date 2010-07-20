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

package com.puresol.kickstart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * KickStart is the main class for the KickStart framework. This class is
 * started and reads the KickStart.properties file in the same directory as the
 * jar containing this class. All library paths are searches and all classes are
 * loaded. Afterwards the specified application is loaded and started with the
 * runtime parameters given to KickStart or a new thread is created.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class KickStart implements Runnable {

	private final String classToStart;
	private final String[] argumentsForClassToStart;
	private final KickStartProperties properties;

	public KickStart(String classToStart, String[] argumentsForClassToStart)
			throws KickStartException {
		try {
			this.classToStart = classToStart;
			this.argumentsForClassToStart = argumentsForClassToStart;
			properties = KickStartProperties.getInstance();
		} catch (FileNotFoundException e) {
			throw new KickStartException(
					"Could not find properties file for KickStart.");
		} catch (IOException e) {
			throw new KickStartException(
					"Could not read properties file for KickStart due to IO issues.");
		}
	}

	@Override
	public void run() {
		addClassDirectories();
		loadJars();
		runApplication();
	}

	private void loadJars() {
		for (File jarDirectory : properties.getJarDirectories()) {
			JarLoader.loadJarsFromDirectory(jarDirectory,
					properties.isRecursiveJarSearch(), properties.isVerbose());
		}
	}

	private void addClassDirectories() {
		for (File jarDirectory : properties.getClassDirectories()) {
			JarLoader.load(jarDirectory);
		}
	}

	private void runApplication() {
		try {
			Class<?> clazz = Class.forName(classToStart);
			Method mainMethod = clazz.getMethod("main", String[].class);
			int modifiers = mainMethod.getModifiers();
			if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
				Object params[] = new Object[1];
				params[0] = argumentsForClassToStart;
				mainMethod.invoke(null, params);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length <= 0) {
				System.out
						.println("Usage: KickStart <class name to start> <parameters for class to start...>\n");
				return;
			}
			String classToStart = "";
			String[] argumentsForClassToStart = new String[args.length - 1];
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				if (i == 0) {
					classToStart = arg;
				} else {
					argumentsForClassToStart[i - 1] = arg;
				}
			}
			KickStart kickStart = new KickStart(classToStart,
					argumentsForClassToStart);
			new Thread(kickStart).run();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
