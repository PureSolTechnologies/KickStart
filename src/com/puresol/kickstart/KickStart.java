package com.puresol.kickstart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
public class KickStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			KickStartProperties properties = KickStartProperties.getInstance();
			for (File jarDirectory : properties.getJarDirectories()) {
				JarLoader.loadJarsFromDirectory(jarDirectory,
						properties.isRecursiveJarSearch(),
						properties.isVerbose());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
