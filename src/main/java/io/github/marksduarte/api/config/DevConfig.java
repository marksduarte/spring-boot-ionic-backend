package io.github.marksduarte.api.config;

import java.text.ParseException;

import io.github.marksduarte.api.services.DBService;
import io.github.marksduarte.api.services.EmailService;
import io.github.marksduarte.api.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	/**
	 * Os métodos não podem ser void.
	 * @return true
	 */
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"create".equals(strategy)) {
			return false;
		}			
		
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService EmailService() {
		return new SmtpEmailService();
	}
}
