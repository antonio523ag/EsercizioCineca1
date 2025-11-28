package dev.antoniogrillo.primoprogetto.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.antoniogrillo.primoprogetto.dto.AggiungiIndirizzoDTO;
import dev.antoniogrillo.primoprogetto.dto.SingoloIndirizzoDTO;
import dev.antoniogrillo.primoprogetto.mapper.IndirizzoMapper;
import dev.antoniogrillo.primoprogetto.model.Citta;
import dev.antoniogrillo.primoprogetto.model.Indirizzo;
import dev.antoniogrillo.primoprogetto.repository.CittaRepository;
import dev.antoniogrillo.primoprogetto.repository.IndirizzoRepository;
import dev.antoniogrillo.primoprogetto.service.def.IndirizzoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndirizzoServiceImpl implements IndirizzoService{
	
	private final IndirizzoRepository repo;
	private final CittaRepository cittaRepo;
	private final IndirizzoMapper mapper;
	@Override
	public long aggiungiIndirizzo(AggiungiIndirizzoDTO dto) {
		Citta c=cittaRepo.findByNome(dto.getNomeCitta().trim()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"non esiste la citta "+dto.getNomeCitta()));
		Indirizzo i=mapper.toIndirizzo(dto, c);
		i=repo.save(i);
		return i.getId();
	}
	@Override
	public void modificaIndirizzo(SingoloIndirizzoDTO dto) {
		Citta c=cittaRepo.findByNome(dto.getCitta().trim()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"non esiste la citta "+dto.getCitta()));
		Indirizzo i=mapper.toIndirizzo(dto, c);
		i=repo.save(i);
		
	}
	@Override
	public void elimina(long id) {
		repo.deleteById(id);
		
	}
	@Override
	public SingoloIndirizzoDTO getById(long id) {
		Indirizzo i=repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"nessun indirizzo con questo id"));
		return mapper.toSingoloIndirizzoDTO(i);
	}
	@Override
	public SingoloIndirizzoDTO getByIdUtente(long id) {
		Indirizzo i=repo.findByIdUtente(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"nessun indirizzo con questo id"));
		return mapper.toSingoloIndirizzoDTO(i);
	}
	
	
	

}
