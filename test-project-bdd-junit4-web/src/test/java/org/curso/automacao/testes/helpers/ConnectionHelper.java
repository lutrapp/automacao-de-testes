package org.curso.automacao.testes.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ConnectionHelper {
	
	public static String getHost(String env, String service) throws IOException {

		if (isRunningInsideDocker()) {
			return PropertiesHelper.getProperty("docker." + env.trim() + "." + service.trim());
		} else {
			return PropertiesHelper.getProperty(env.trim() + "." + service.trim());
		}

	}

	public static Boolean isRunningInsideDocker() {
		return new File("/.dockerenv").exists();
	}

}
