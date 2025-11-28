package dev.antoniogrillo.primoprogetto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import dev.antoniogrillo.primoprogetto.controller.CittaController;
import dev.antoniogrillo.primoprogetto.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityCustomConfigBean {

	private final UtenteRepository repo;

	@Bean
	protected UserDetailsService getUserDetailService() {
		return username->repo.findByEmail(username)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@Bean
	protected PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	@Bean
	protected AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider dap=new DaoAuthenticationProvider(getUserDetailService());
		dap.setPasswordEncoder(getPasswordEncoder());
		return dap;
	}

}
