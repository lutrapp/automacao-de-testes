package org.curso.automacao.testes.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {

	private static Properties properties;

	private static Properties getProperties() throws IOException {

		if (properties == null) {
			FileInputStream file = new FileInputStream(new File("src/test/resources/test.properties"));

			properties = new Properties();
			properties.load(file);
		}

		return properties;
	}

	public static String getProperty(String name) throws IOException {
		return getProperties().getProperty(name);
	}

}
