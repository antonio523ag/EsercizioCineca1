package dev.antoniogrillo.primoprogetto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.antoniogrillo.primoprogetto.model.Ruolo;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class GestorePath {
	private final MioCustomFilter filter;
	private final AuthenticationProvider provider;
	
	@Bean
	protected SecurityFilterChain getChain(HttpSecurity http) throws Exception {
		http
		.headers(c->c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(req->
			req.requestMatchers("/utente/registra").permitAll()
//				.requestMatchers("/utente/login").permitAll()
//				.requestMatchers("/admin/registra").hasRole(Ruolo.ADMIN.name())
//				.requestMatchers("/piatto/all/**").hasRole(Ruolo.ADMIN.name())
//				.requestMatchers("/citta/all/**").hasAnyRole(Ruolo.ADMIN.name(),Ruolo.UTENTE.name())
//				.anyRequest().permitAll()
			
				.requestMatchers("/all/**").permitAll()
				.requestMatchers("/utente/**").hasRole(Ruolo.UTENTE.name())
				.requestMatchers("/admin/**").hasRole(Ruolo.ADMIN.name())
				.requestMatchers("/authorized/**").authenticated()
				.anyRequest().permitAll()
			).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(provider)
			.cors(AbstractHttpConfigurer::disable)
			.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
