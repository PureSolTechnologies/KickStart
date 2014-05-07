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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class handles the information of $installdir/kickstart.properties and
 * translates its information into easy to read object for easy access and
 * handling.
 * 
 * This class is designed as singleton.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class KickStartProperties {

	/**
	 * This constant defines the relative path to the properties file containing
	 * the configuration of KickStart relative to the installation directory.
	 */
	public static final String KICK_START_PROPERTIES_FILENAME = "KickStart.properties";

	private static final String VERBOSE_KEY = "verbose";
	private static final String JAR_DIRECTORIES_KEY = "jar.directories";
	private static final String CLASS_DIRECTORIES_KEY = "class.directories";
	private static final String JAR_DIRECTORIES_RECURSIVE_KEY = "jar.directories.recursive";

	private static KickStartProperties instance = null;

	public static KickStartProperties getInstance()
			throws FileNotFoundException, IOException {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	private static synchronized void createInstance()
			throws FileNotFoundException, IOException {
		if (instance == null) {
			instance = new KickStartProperties();
		}
	}

	private final File installDir;
	private final File kickStartPropertiesFile;
	private final Properties properties = new Properties();

	private KickStartProperties() throws FileNotFoundException, IOException {
		installDir = Directories
				.getInstallationDirectory(KickStart.class, true);
		kickStartPropertiesFile = new File(installDir,
				KICK_START_PROPERTIES_FILENAME);
		properties.load(new FileInputStream(kickStartPropertiesFile));
	}

	/**
	 * This method returns the list of all jar file directories.
	 * 
	 * @return
	 */
	public List<File> getJarDirectories() {
		List<File> directories = new ArrayList<File>();
		String dirs = properties.getProperty(JAR_DIRECTORIES_KEY);
		for (String dir : dirs.split(",")) {
			dir = dir.trim();
			File directory = new File(dir);
			if (directory.isAbsolute()) {
				directories.add(directory);
			} else {
				directories.add(new File(installDir, dir));
			}
		}
		return directories;
	}

	/**
	 * This method returns the setting for recursive search.
	 * 
	 * @return
	 */
	public boolean isRecursiveJarSearch() {
		return Boolean.valueOf(
				properties.getProperty(JAR_DIRECTORIES_RECURSIVE_KEY))
				.booleanValue();
	}

	/**
	 * This method returns the setting for verbosity.
	 * 
	 * @return
	 */
	public boolean isVerbose() {
		return Boolean.valueOf(properties.getProperty(VERBOSE_KEY))
				.booleanValue();
	}

	/**
	 * This method returns the list of all directories with class files.
	 * 
	 * @return
	 */
	public List<File> getClassDirectories() {
		List<File> directories = new ArrayList<File>();
		String dirs = properties.getProperty(CLASS_DIRECTORIES_KEY);
		for (String dir : dirs.split(",")) {
			dir = dir.trim();
			File directory = new File(dir);
			if (directory.isAbsolute()) {
				directories.add(directory);
			} else {
				directories.add(new File(installDir, dir));
			}
		}
		return directories;
	}

}
