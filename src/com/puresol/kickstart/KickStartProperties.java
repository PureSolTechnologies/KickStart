package com.puresol.kickstart;

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

	public boolean isRecursiveJarSearch() {
		return Boolean.valueOf(
				properties.getProperty(JAR_DIRECTORIES_RECURSIVE_KEY))
				.booleanValue();
	}

	public boolean isVerbose() {
		return Boolean.valueOf(properties.getProperty(VERBOSE_KEY))
				.booleanValue();
	}
}
