package org.curso.automacao.modulos.erp.webapp.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.curso.automacao.modulos.erp.webapp.helpers.ConnectionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.resource.ResourceTransformer;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

@Configuration
public class ConfigJSResourceTransformer implements ResourceTransformer {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticResourceConfiguration.class);
	
	@Autowired
	private ConnectionHelper connectionHelper;

	@Override
	public Resource transform(HttpServletRequest request, Resource resource, ResourceTransformerChain transformerChain)
			throws IOException {
		
		LOGGER.info("Active profile on web app " + connectionHelper.getActiveProfile());
		
		if (request.getServletPath().equals("/utils/config.js")) {
			byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
			String content = new String(bytes, StandardCharsets.UTF_8);
			
			content = content.replace("{%ENV}", connectionHelper.getActiveProfile());
					
			return new TransformedResource(resource, content.getBytes(StandardCharsets.UTF_8));
		} else {
			return resource;
		}
	}
}

