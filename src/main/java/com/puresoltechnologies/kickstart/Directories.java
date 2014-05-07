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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * This class gathers some static methods to perform some standardized work on
 * directories.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class Directories {

	public static final String INSTALLATION_DIRECTORY_KEY = "app.installdir";

	/**
	 * This method returns the installation directory of the tool represented by
	 * clazz. The installation directory is found by searching the package name
	 * and re-engineering of the installation path. The path is put into the
	 * system property Directories.INSTALLATION_DIRECTORY_KEY. If the key is
	 * presented during start up by a start up script or something similar, the
	 * installation path is directly taken out of this value.
	 * 
	 * @param clazz
	 * @param findRootOfPackage
	 * @return
	 */
	public static File getInstallationDirectory(Class<?> clazz,
			boolean findRootOfPackage) {
		String installDir = System.getProperty(INSTALLATION_DIRECTORY_KEY);
		if ((installDir != null) && (!installDir.isEmpty())) {
			return new File(installDir);
		}
		File installDirectory = findInstallationDirectory(clazz,
				findRootOfPackage);
		if (installDirectory != null) {
			System.setProperty(INSTALLATION_DIRECTORY_KEY,
					installDirectory.toString());
		}
		return installDirectory;
	}

	/**
	 * This is a helper method to find the installation directory by package
	 * lookup and path re-engineering.
	 * 
	 * @param clazz
	 * @param findRootOfPackage
	 * @return
	 */
	private static File findInstallationDirectory(Class<?> clazz,
			boolean findRootOfPackage) {
		// get the resource url and path. Closest thing we have to physical
		// location
		URL url = clazz.getResource(clazz.getSimpleName() + ".class");
		if (url.getProtocol().equals("jar")) {
			// removes the 'jar:' in front
			String urlPath = url.getPath();
			// removes 'file:' in front
			urlPath = urlPath.substring(5);
			// remove everything behind the '!'
			urlPath = urlPath.substring(0, urlPath.indexOf("!"));
			File homeLocation = new File(urlPath).getParentFile();
			return homeLocation;
		} else if (url.getProtocol().equals("file")) {
			String urlPath = url.getPath();
			// decode it and get the parent
			File homeLocation;
			try {
				homeLocation = new File(URLDecoder.decode(urlPath, "UTF-8"))
						.getParentFile();
			} catch (UnsupportedEncodingException e) {
				homeLocation = new File(urlPath, "UTF-8").getParentFile();
			}
			// if we should unwind the package to the classpath root.
			if (!findRootOfPackage) {
				return homeLocation;
			}
			// get the package
			Package p = clazz.getPackage();
			if (p == null) {
				return null;
			}
			/*
			 * work your way up as many times as the target class has packages
			 * so package com.foo.bar will move up 3 levels in the directory
			 */
			String[] arrs = p.getName().split("[.]");
			// kill the stuff after the end of the jar file name
			for (int i = 0; i < arrs.length; i++) {
				homeLocation = homeLocation.getParentFile();
			}
			return homeLocation;
		} else {
			return null;
		}
	}

	/**
	 * This method reads the system property 'user.dir' and returns the path as
	 * current execution path.
	 * 
	 * @return
	 */
	public static File getExecutionDirectory() {
		return new File(System.getProperty("user.dir", ".").toString());
	}

	/**
	 * This method reads the system property 'user.home' and returns the path as
	 * the user's home directory.
	 * 
	 * @return
	 */
	public static File getUserDirectory() {
		return new File(System.getProperty("user.home"));
	}
}
