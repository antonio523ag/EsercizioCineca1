package dev.antoniogrillo.primoprogetto.security;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.antoniogrillo.primoprogetto.model.Utente;
import dev.antoniogrillo.primoprogetto.repository.UtenteRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GestoreToken {
	
	@Value("${mia.custom.chiave}")
	private String chiave;
	
	private final UtenteRepository repo;
	
	private SecretKey getkey() {
		byte[] array=Decoders.BASE64.decode(chiave);
		return Keys.hmacShaKeyFor(array);
	}
	
	public String creaToken(Utente u) {
		//calcolo quanti millisecondi ci sono in un mese (ho scelto io il mese)
		long durata=1000L*60*60*24*30;
		//il metodo Jwts.builder() apre il mio builder per la creazione del token
		String token=Jwts.builder()
				//con il metodo .claims() vado a settare i parametri del Payload, la parte centrale del token visibile a tutti
				.claims()
					//con il metodo .add posso aggiungere un qualsiasi attributo al mio payload
				   .add("mioTesto", "ciao a tutti")
				   //il metodo .subject aggiunge una riga al payload con chiave subject
				   .subject(u.getEmail())
				   //imposto la data di scadenza
				   .expiration(new Date(System.currentTimeMillis()+durata))
				   //imposto la data di creazione
				   .issuedAt(new Date(System.currentTimeMillis()))
				//il metodo and() termina la creazione del payload
				.and()
				//firmo il tutto con la chiave alfanumerica che ho creato
				.signWith(getkey())
				//e creo la stringa
				.compact();
		return token;
	}
	
	public Utente getUtenteFromToken(String token) {
		String email=getEmailFromToken(token);
		return repo.findByEmail(email).orElseThrow(()->new JwtException(email));
	}
	
	private String getEmailFromToken(String token) {
		Claims c= getClaims(token);
		return c.getSubject();
	}
	
	private Date getDataScadenza(String token) {
		Claims c= getClaims(token);
		return c.getExpiration();
	}
	
	private String getMioTesto(String token) {
		Claims c=getClaims(token);
		return (String) c.get("mioTesto");
	}
	
	private Claims getClaims(String token) {
		//tutti i token JWT vengono passati nell'header con all'inizio la scritta "Bearer "
		//quindi la tolgo
		if(token.startsWith("Bearer "))token=token.substring(7);
		//creo il parser per decodificare il mio token
		JwtParser parser=Jwts.parser()
				.verifyWith(getkey())
				.build();
		//vado a riprendermi il payload
		Claims c =(Claims) parser.parse(token).getPayload();
		return c;
	}

}
