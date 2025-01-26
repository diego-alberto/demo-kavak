package com.kavak.main_core;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MainCoreApplication extends SpringBootServletInitializer {
	public static void init(Class appClass, String... args) {
		new SpringApplicationBuilder(appClass).web(WebApplicationType.SERVLET).run(args);
	}
}
