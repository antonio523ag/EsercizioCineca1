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

import dev.antoniogrillo.primoprogetto.dto.ElencoCittaDTO;
import dev.antoniogrillo.primoprogetto.dto.SingolaCittaDTO;
import dev.antoniogrillo.primoprogetto.service.def.CittaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CittaController {
	
	private final CittaService service;
	
	@PostMapping("/authorized/citta/aggiungi")
	public ResponseEntity<Long> aggiungiCitta(@RequestParam String citta){
		return ResponseEntity.ok(service.aggiungiCitta(citta));
	}
	
	@PutMapping("/authorized/citta/modifica")
	public ResponseEntity<Void> modificaCitta(@RequestBody SingolaCittaDTO dto){
		service.modificaCitta(dto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/admin/citta/{id}/elimina")
	public ResponseEntity<Void> eliminaCitta(@PathVariable long id){
		service.eliminaCitta(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/all/citta/{id}")
	public ResponseEntity<SingolaCittaDTO> getById(@PathVariable long id){
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping("/all/citta/all/{pagina}")
	public ResponseEntity<ElencoCittaDTO> getAll(@PathVariable int pagina){
		return ResponseEntity.ok(service.getAll(pagina));
	}
	
	@GetMapping("/citta/nome")
	public ResponseEntity<SingolaCittaDTO> getByNome(@RequestParam String nome){
		return ResponseEntity.ok(service.getByNome(nome));
	}
}
