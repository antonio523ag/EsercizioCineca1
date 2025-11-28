package dev.antoniogrillo.primoprogetto.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.antoniogrillo.primoprogetto.dto.AggiungiPiattoDTO;
import dev.antoniogrillo.primoprogetto.dto.SingoloPiattoDTO;
import dev.antoniogrillo.primoprogetto.mapper.PiattoMapper;
import dev.antoniogrillo.primoprogetto.model.Piatto;
import dev.antoniogrillo.primoprogetto.model.Ruolo;
import dev.antoniogrillo.primoprogetto.model.Utente;
import dev.antoniogrillo.primoprogetto.repository.PiattoRepository;
import dev.antoniogrillo.primoprogetto.repository.UtenteRepository;
import dev.antoniogrillo.primoprogetto.service.def.PiattoService;
import lombok.RequiredArgsConstructor;

@Service
//crea un costruttore con tutte le variabili final
@RequiredArgsConstructor
public class PiattoServiceImpl implements PiattoService{
	
	private final UtenteRepository utenteRepo;
	private final PiattoRepository piattoRepo;
	private final PiattoMapper mapper;
	
	

	@Override
	public long aggiungiPiatto(AggiungiPiattoDTO p1) {
		if(p1==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"devi inserire i dati del piatto");
		Piatto p=mapper.toPiatto(p1);
		if(p.getDescrizione()==null||p.getDescrizione().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"devi inserire una descrizione");
		if(p.getListaIngredienti()==null||p.getListaIngredienti().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"devi inserire gli ingredienti");
		p=piattoRepo.save(p);
		return p.getId();
	}

	@Override
	public void modificaPiatto(SingoloPiattoDTO p1) {
		if(p1==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"devi inserire i dati del piatto");
		if(p1.getId()<=0)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"non esistono piatti con id 0 o negativo");
		Piatto p= mapper.toPiatto(p1);
		if(p.getDescrizione()==null||p.getDescrizione().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"devi inserire una descrizione");
		if(p.getListaIngredienti()==null||p.getListaIngredienti().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"devi inserire gli ingredienti");
		piattoRepo.save(p);
		
	}

	@Override
	public SingoloPiattoDTO getById(long id) {
		Piatto p=piattoRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"non esiste nessun piatto con id "+id));
		return mapper.toSingoloPiattoDTO(p);
	}

	@Override
	public List<SingoloPiattoDTO> getAllPiatti(long idUtente) {
		Utente u=utenteRepo.findById(idUtente).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"nessun utente"));
		if(u.getRuolo()!=Ruolo.ADMIN) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"non hai i diritti per visualizzare tutti i piatti");
		List<Piatto> p= piattoRepo.findAll();
		return mapper.toSingoloPiattoDTO(p);
	}

	@Override
	public void eliminaPiatto(Piatto p) {
		eliminaPiatto(p.getId());
		
	}

	@Override
	public void eliminaPiatto(long id) {
		piattoRepo.deletePiatto(id);
	}

	@Override
	public List<SingoloPiattoDTO> getPiattiDiAltriUtenti(long idUtente) {
//		List<Piatto> piatti=getAllPiatti();
//		Utente u = utenteRepo.findById(idUtente).orElse(null);
//		if(u==null||u.getPiatti()==null)return piatti;
//		for(Piatto p:u.getPiatti()) {
//			piatti.removeIf(p1->p1.getId()==p.getId());
//		}
//		return piatti;
		Utente u=utenteRepo.findById(idUtente).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"nessun utente"));
		List<Piatto> p= piattoRepo.findAltriPiatti(u);
		return mapper.toSingoloPiattoDTO(p);
	}
	
	@Override
	public List<SingoloPiattoDTO> getPiattiPerCitta(String nomeCitta){
		List<Piatto> piatti=piattoRepo.findByCitta(nomeCitta.trim());
		return mapper.toSingoloPiattoDTO(piatti);
	}

}
