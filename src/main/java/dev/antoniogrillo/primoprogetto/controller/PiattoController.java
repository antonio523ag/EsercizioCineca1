package dev.antoniogrillo.primoprogetto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.antoniogrillo.primoprogetto.dto.AggiungiPiattoDTO;
import dev.antoniogrillo.primoprogetto.dto.SingoloPiattoDTO;
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
	
	@PostMapping("/authorized/piatto/add")
	public ResponseEntity<Long> creaPiatto(@RequestBody AggiungiPiattoDTO p){
		long id=service.aggiungiPiatto(p);
		return ResponseEntity.ok(id);
	}
	
	@PutMapping("/authorized/piatto/modifica")
	public ResponseEntity<Void> modificaPiatto(@RequestBody SingoloPiattoDTO p){
		service.modificaPiatto(p);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/admin/piatto/{id}/elimina")
	public ResponseEntity<Void> eliminaPiatto(@PathVariable long id){
		service.eliminaPiatto(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/authorized/piatto/{id}")
	public ResponseEntity<SingoloPiattoDTO> getById(@PathVariable long id){
		SingoloPiattoDTO p=service.getById(id);
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/admin/piatto/all/{idUtente}")
	public ResponseEntity<List<SingoloPiattoDTO>> getAll(@PathVariable long idUtente){
		return ResponseEntity.ok(service.getAllPiatti(idUtente));
	}
	
	@GetMapping("/authorized/piatto/altro/{idUtente}")
	public ResponseEntity<List<SingoloPiattoDTO>> getAltriPiatti(@PathVariable long idUtente){
		return ResponseEntity.ok(service.getPiattiDiAltriUtenti(idUtente));
	}
	
	@GetMapping("/authorized/piatto/citta")
	public ResponseEntity<List<SingoloPiattoDTO>> getPiattiPerCitta(@RequestParam String citta){
		return ResponseEntity.ok(service.getPiattiPerCitta(citta));
	}
}
