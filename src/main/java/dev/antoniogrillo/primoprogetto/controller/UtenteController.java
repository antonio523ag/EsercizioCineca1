package dev.antoniogrillo.primoprogetto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.antoniogrillo.primoprogetto.dto.RegistraUtenteDTO;
import dev.antoniogrillo.primoprogetto.dto.VisualizzaUtenteDTO;
import dev.antoniogrillo.primoprogetto.model.Utente;
import dev.antoniogrillo.primoprogetto.service.def.UtenteService;

/**
 * creare un sito che ci permetta di registrare un utente, 
 * l'utente potrà inserire un piatto con descrizione e 
 * lista di ingredienti (basta un campo ingredienti di tipo string), 
 * visualizzare i piatti non suoi e aggiungere un piatto alla 
 * propria lista dei piatti,visualizzare i propri piatti e 
 * rimuovere un piatto dai propri piatti
 */

@RestController
public class UtenteController {
	
	private final UtenteService service;
	
	public UtenteController(UtenteService s) {
		service=s;
	}
	
	@PostMapping("/utente/registra")
	public ResponseEntity<Void> registraUtente(@RequestBody RegistraUtenteDTO u){
		service.registraUtente(u);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/utente/login")
	public ResponseEntity<VisualizzaUtenteDTO> login(@RequestParam String username,@RequestParam String password){
		VisualizzaUtenteDTO u=service.login(username, password);
		if(u==null)return ResponseEntity.notFound().build();
		else return ResponseEntity.ok(u);
	}
	
	@PostMapping("/utente/{idUtente}/aggiungi/piatto/{idPiatto}")
	public ResponseEntity<Void> aggiungiPiatto(@PathVariable long idUtente,@PathVariable long idPiatto){
		service.aggiungiPiatto(idUtente, idPiatto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/utente/{idUtente}/rimuovi/piatto/{idPiatto}")
	public ResponseEntity<Void> rimuoviPiatto(@PathVariable long idUtente, @PathVariable long idPiatto){
		service.rimuoviPiatto(idUtente, idPiatto);
		return ResponseEntity.ok().build();
	}
	
	

}
