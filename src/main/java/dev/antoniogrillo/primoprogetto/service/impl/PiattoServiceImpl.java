package dev.antoniogrillo.primoprogetto.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.antoniogrillo.primoprogetto.model.Piatto;
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
	
	

	@Override
	public long aggiungiPiatto(Piatto p) {
		if(p==null||p.getId()!=0||
				p.getDescrizione()==null||p.getDescrizione().isBlank()||
				p.getListaIngredienti()==null||p.getListaIngredienti().isBlank())return 0;
		p=piattoRepo.save(p);
		return p.getId();
	}

	@Override
	public void modificaPiatto(Piatto p) {
		if(p==null||p.getId()<=0||
				p.getDescrizione()==null||p.getDescrizione().isBlank()||
				p.getListaIngredienti()==null||p.getListaIngredienti().isBlank())return;
		piattoRepo.save(p);
		
	}

	@Override
	public Piatto getById(long id) {
		return piattoRepo.findById(id).orElse(null);
	}

	@Override
	public List<Piatto> getAllPiatti() {
		return piattoRepo.findAll();
	}

	@Override
	public void eliminaPiatto(Piatto p) {
		//TODO dobbiamo gestire le relazioni con la tabella utente_piatto_unique
		piattoRepo.delete(p);
		
	}

	@Override
	public void eliminaPiatto(long id) {
		//TODO dobbiamo gestire le relazioni con la tabella utente_piatto_unique
		piattoRepo.deletePiatto(id);
	}

	@Override
	public List<Piatto> getPiattiDiAltriUtenti(long idUtente) {
//		List<Piatto> piatti=getAllPiatti();
//		Utente u = utenteRepo.findById(idUtente).orElse(null);
//		if(u==null||u.getPiatti()==null)return piatti;
//		for(Piatto p:u.getPiatti()) {
//			piatti.removeIf(p1->p1.getId()==p.getId());
//		}
//		return piatti;
		Utente u=utenteRepo.findById(idUtente).orElse(null);
		if(u==null)return piattoRepo.findAll();
		return piattoRepo.findAltriPiatti(u);
	}

}
