package dev.antoniogrillo.primoprogetto.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.antoniogrillo.primoprogetto.model.Utente;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MioCustomFilter extends OncePerRequestFilter{
	
	private final GestoreToken tokenGranter;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//riprendo l'header di autenticazione
		String header = request.getHeader("Authorization");
		//se l'header esiste
		if(header!=null
				//è un token jwt
				&&header.startsWith("Bearer ")
				//e non ho ancora settato nessuna security
				&&SecurityContextHolder.getContext().getAuthentication()==null) {
			//prendo l'utente dal token
			Utente u=null;
			try{
				u=tokenGranter.getUtenteFromToken(header);
			}catch (JwtException e) {
				response.sendError(418, "riprova, sarai più fortunato");
				return;
			}
			//creo l'oggetto per gestire le autorizzazioni dell'utente 
			//(i tre parametri sono:
				//Principal= l'utente che fa la login
				//Credential = se gestiamo l'accesso per credenziali, nel nostro caso no
				//Authorization = la lista di GrantedAuthorities creata nell'utente
			UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(u,null,u.getAuthorities());
			//lo aggancio alla request
			upat.setDetails(new WebAuthenticationDetails(request));
			//setto il tutto nel come autorizzazioni nel cotex holder
			SecurityContextHolder.getContext().setAuthentication(upat);
		}
		//a prescindere da tutto vado alla chiamata successiva
		filterChain.doFilter(request, response);
		
	}

}
