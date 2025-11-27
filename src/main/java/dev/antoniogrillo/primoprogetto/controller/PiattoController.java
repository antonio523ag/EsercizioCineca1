package dev.antoniogrillo.primoprogetto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.antoniogrillo.primoprogetto.model.Piatto;
import dev.antoniogrillo.primoprogetto.service.def.PiattoService;
import lombok.RequiredArgsConstructor;

/**
 * creare un sito che ci permetta di registrare un utente, 
 * l'utente potrà inserire un piatto con descrizione e 
 * lista di ingredienti (basta un campo ingredienti di tipo string), 
 * visualizzare i piatti non suoi e aggiungere un piatto alla 
 * propria lista dei piatti,visualizzare i propri piatti e 
 * rimuovere un piatto dai propri piatti
 */

@RestController
@RequiredArgsConstructor
public class PiattoController {
	private final PiattoService service;
	
	@PostMapping("/piatto/add")
	public ResponseEntity<Long> creaPiatto(@RequestBody Piatto p){
		long id=service.aggiungiPiatto(p);
		if(id>0) return ResponseEntity.ok(id);
		else return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/piatto/modifica")
	public ResponseEntity<Void> modificaPiatto(@RequestBody Piatto p){
		service.modificaPiatto(p);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/piatto/{id}/elimina")
	public ResponseEntity<Void> eliminaPiatto(@PathVariable long id){
		service.eliminaPiatto(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/piatto/{id}")
	public ResponseEntity<Piatto> getById(@PathVariable long id){
		Piatto p=service.getById(id);
		if(p!=null)return ResponseEntity.ok(p);
		else return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/piatto/all")
	public ResponseEntity<List<Piatto>> getAll(){
		return ResponseEntity.ok(service.getAllPiatti());
	}
	
	@GetMapping("/piatto/all/{idUtente}")
	public ResponseEntity<List<Piatto>> getAltriPiatti(@PathVariable long idUtente){
		return ResponseEntity.ok(service.getPiattiDiAltriUtenti(idUtente));
	}
}
