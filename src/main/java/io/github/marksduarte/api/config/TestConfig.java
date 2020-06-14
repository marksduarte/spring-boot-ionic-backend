package io.github.marksduarte.api.config;

import java.text.ParseException;

import io.github.marksduarte.api.services.DBService;
import io.github.marksduarte.api.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
	
	/**
	 * Quando ser faz um método @Bean, esse objeto vai 
	 * ficar disponivel para todo o sistema.
	 * @return
	 */
	@Bean
	public MockEmailService EmailService() {
		return new MockEmailService();
	}
		
}
