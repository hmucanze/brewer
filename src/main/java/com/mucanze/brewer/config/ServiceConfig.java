package com.mucanze.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mucanze.brewer.service.CervejaService;
import com.mucanze.brewer.storage.FotoStorage;
import com.mucanze.brewer.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = CervejaService.class)
public class ServiceConfig {
	
	@Bean
	public FotoStorage fotoStorage() {
		return new FotoStorageLocal();
	}

}
