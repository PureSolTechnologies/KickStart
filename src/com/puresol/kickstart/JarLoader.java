package com.puresol.kickstart;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * The JarLoader class is for dynamic loading and initializing of jar libraries
 * at startup. The JarLoader does not support logging due to the uncoupling of
 * extern logging facitlies. Loading should be performed in that way only at
 * startup.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class JarLoader {

	/**
	 * This simple file filter accepts all files ending in '.jar' and all
	 * directories. Hidden files and directories and rejected.
	 * 
	 * @author Rick-Rainer Ludwig
	 * 
	 */
	private static class JarFileFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String file) {
			if (file.startsWith(".")) {
				return false;
			}
			if (new File(dir, file).isDirectory()) {
				return true;
			}
			return file.endsWith(".jar");
		}
	}

	public static void loadJarsFromDirectory(File directory, boolean recursive,
			boolean verbose) {
		if (verbose) {
			System.out.println("Loading all libraries from directory '"
					+ directory.toString() + "'...");
		}
		String[] files = directory.list(new JarFileFilter());
		if (files == null) {
			if (verbose) {
				System.out.println("Nothing found to be loaded...");
			}
			return;
		}
		int counter = 0;
		for (String file : files) {
			counter++;
			File library = new File(directory, file);
			if (library.isDirectory() && recursive) {
				loadJarsFromDirectory(library, recursive, verbose);
				continue;
			}
			if (verbose) {
				int percentage = (int) Math.round((double) counter * 100.0
						/ (double) files.length);
				System.out.print(String.valueOf(percentage) + "% ");
				System.out.println(library.toString());
			}
			JarLoader.load(library);
		}
		System.out.println();
	}

	/**
	 * @param args
	 */
	public static void load(File jarFile) {
		try {
			URL jarURL = jarFile.toURI().toURL();
			/*
			 * Either do it this way:
			 * 
			 * ClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
			 * 
			 * or the other:
			 */
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			if (classLoader != null && (classLoader instanceof URLClassLoader)) {
				URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
				Method addURL = URLClassLoader.class.getDeclaredMethod(
						"addURL", new Class[] { URL.class });
				addURL.setAccessible(true);
				addURL.invoke(urlClassLoader, new Object[] { jarURL });
			} else {
				throw new RuntimeException(
						"The ContextClassLoader should be a URLClassLoader!");
			}
			return;
		} catch (MalformedURLException e) {
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
		throw new RuntimeException("Could not load jar file!");
	}
}
