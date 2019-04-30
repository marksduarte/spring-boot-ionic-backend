package com.marksduarte.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.marksduarte.cursomc.services.DBService;

/**
 * Essa classe define os beans que serão utilizados quando o profile test
 * estiver ativo. * 
 * @author marksduarte
 *
 */
@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	/**
	 * Os métodos não podem ser void, por isso o return true
	 * @return true
	 */
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
}
