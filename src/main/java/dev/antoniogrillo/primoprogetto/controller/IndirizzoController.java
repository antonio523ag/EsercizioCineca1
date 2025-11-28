package dev.antoniogrillo.primoprogetto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.antoniogrillo.primoprogetto.dto.AggiungiIndirizzoDTO;
import dev.antoniogrillo.primoprogetto.dto.SingoloIndirizzoDTO;
import dev.antoniogrillo.primoprogetto.model.Utente;
import dev.antoniogrillo.primoprogetto.service.def.IndirizzoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IndirizzoController {
	
	private final IndirizzoService service;
	
	@PostMapping("/authorized/indirizzo")
	public ResponseEntity<Long> aggiungiIndirizzo(@RequestBody AggiungiIndirizzoDTO i){
		return ResponseEntity.ok(service.aggiungiIndirizzo(i));
	}
	
	@PutMapping("/authorized/indirizzo")
	public ResponseEntity<Void> modificaIndirizzo(@RequestBody SingoloIndirizzoDTO i){
		service.modificaIndirizzo(i);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/admin/indirizzo/{id}")
	public ResponseEntity<Void> eliminaIndirizzo(@PathVariable long id){
		service.elimina(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/utente/indirizzo/{id}")
	public ResponseEntity<SingoloIndirizzoDTO> getById(@PathVariable long id){
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping("/authorized/indirizzo/utente")
	public ResponseEntity<SingoloIndirizzoDTO> getByIdUtente(UsernamePasswordAuthenticationToken token){
		Utente u=(Utente)token.getPrincipal();
		return ResponseEntity.ok(service.getByIdUtente(u.getId()));
	}
}
