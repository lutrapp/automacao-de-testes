package org.curso.automacao.modulos.erp.webapp.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration	
public class ConnectionHelper {

	@Autowired
	Environment environment;

	public String getHost(String service) {

		if (isRunningInsideDocker()) {
			return getActiveProfile() + "." + service;
		} else {
			return "localhost";
		}

	}

	public String getActiveProfile() {
		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("test")))
			return "test";
		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("dev")))
			return "dev";
		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("prod")))
			return "prod";
		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("tmp")))
			return "tmp";
		
		return "dev";
	}

	public Boolean isRunningInsideDocker() {

		return new File("/.dockerenv").exists();
	}

}
