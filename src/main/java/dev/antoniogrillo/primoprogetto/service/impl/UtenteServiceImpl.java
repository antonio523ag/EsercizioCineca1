package dev.antoniogrillo.primoprogetto.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.antoniogrillo.primoprogetto.dto.RegistraUtenteDTO;
import dev.antoniogrillo.primoprogetto.dto.VisualizzaUtenteDTO;
import dev.antoniogrillo.primoprogetto.exception.PiattoGiaPresenteException;
import dev.antoniogrillo.primoprogetto.mapper.UtenteMapper;
import dev.antoniogrillo.primoprogetto.model.Piatto;
import dev.antoniogrillo.primoprogetto.model.Utente;
import dev.antoniogrillo.primoprogetto.repository.PiattoRepository;
import dev.antoniogrillo.primoprogetto.repository.UtenteRepository;
import dev.antoniogrillo.primoprogetto.service.def.UtenteService;

@Service
public class UtenteServiceImpl implements UtenteService {
	
	@Autowired
	private UtenteRepository utenteRepo;
	@Autowired
	private PiattoRepository piattoRepo;
	@Autowired
	private UtenteMapper mapper;
	
	
	@Override
	public void registraUtente(RegistraUtenteDTO dto) {
		Utente u=mapper.toUtente(dto);
		if(u==null||
				u.getId()>0||
				u.getEmail()==null||
				u.getEmail().isBlank()||
				u.getPassword()==null||
				u.getPassword().isBlank())return;
		u.setEmail(u.getEmail().trim());
		utenteRepo.save(u);
		
	}

	@Override
	public void modificaUtente(Utente u) {
		if(u==null||
				u.getId()==0||
				u.getEmail()==null||
				u.getEmail().isBlank()||
				u.getPassword()==null||
				u.getPassword().isBlank())return;
		u.setEmail(u.getEmail().trim());
		utenteRepo.save(u);
		
	}

	@Override
	public VisualizzaUtenteDTO getById(long id) {
		Utente u= utenteRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		return mapper.toVisualizzaUtenteDTO(u);
	}

	@Override
	public List<VisualizzaUtenteDTO> getAllUtenti() {
		return mapper.toVisualizzaUtenteDTO(utenteRepo.findAll());
	}

	@Override
	public void eliminaUtente(Utente u) {
		utenteRepo.delete(u);
	}

	@Override
	public void eliminaUtente(long id) {
		utenteRepo.deleteById(id);
	}

	@Override
	public void aggiungiPiatto(long idUtente, long idPiatto) {
		//cerco l'utente
		Utente u=utenteRepo.findById(idUtente).orElse(null);
		//se non esiste nessun utente con quell'id mi fermo
		if(u==null)return;
		//cerco il patto
		Piatto p=piattoRepo.findById(idPiatto).orElse(null);
		//se non esiste nessun piatto con quell'id mi fermo
		if(p==null)return;
		//se l'utente non ha ancora nessun piatto associato e la lista dei suoi piatti e null
		//ne creo una vuota e gliela imposto in modo da evitare la NullPointerException
		if(u.getPiatti()==null) {
			u.setPiatti(new ArrayList<>());
		}
		//faccio lo stream per trovare se il piatto è già tra i piatti dell'utente
		Piatto p1=u.getPiatti()
				//dalla lista avvio uno stream
				.stream()
				//filtro lo stream lasciando solo i piatti che hanno l'id che voglio
				.filter(p2->p2.getId()==idPiatto)
				//ne prendo uno qualsiasi (ma dovrebbe essercene al massimo uno)
				.findAny()
				//se non ne trovo neanche uno, ritorno null
				.orElse(null);
		//se il piatto è già presente nell'elenco dei piatti dell'utente mi fermo per evitare di riaggiungere lo stesso piatto
		if(p1!=null)throw new PiattoGiaPresenteException("il piatto \""+p1.getDescrizione()+"\"è già presente nell'elenco dei piatti di questo utente");
		//altrimenti lo aggiungo
		u.getPiatti().add(p);
		if(p.getUtenti()==null) {
			p.setUtenti(new ArrayList<>());
		}
		p.getUtenti().add(u);
		//salvo solo uno dei due oggetti tanto la relazione è su una tabella a parte
		//convenzionalmente salvo il "proprietario" della relazione (quello con la JoinTable)
		piattoRepo.save(p);
	}

	@Override
	public void rimuoviPiatto(long idUtente, long idPiatto) {
		//cerco l'utente
				Utente u=utenteRepo.findById(idUtente).orElse(null);
				//se non esiste nessun utente con quell'id mi fermo
				if(u==null)return;
				//cerco il patto
				Piatto p=piattoRepo.findById(idPiatto).orElse(null);
				//se non esiste nessun piatto con quell'id mi fermo
				if(p==null)return;
				//se l'utente non ha ancora nessun piatto associato e la lista dei suoi piatti e null
				//ne creo una vuota e gliela imposto in modo da evitare la NullPointerException
				if(u.getPiatti()==null) {
					u.setPiatti(new ArrayList<>());
				}
				//faccio lo stream per trovare se il piatto è già tra i piatti dell'utente
				Piatto p1=u.getPiatti()
						//dalla lista avvio uno stream
						.stream()
						//filtro lo stream lasciando solo i piatti che hanno l'id che voglio
						.filter(p2->p2.getId()==idPiatto)
						//ne prendo uno qualsiasi (ma dovrebbe essercene al massimo uno)
						.findAny()
						//se non ne trovo neanche uno, ritorno null
						.orElse(null);
				//se il piatto non è presente nell'elenco dei piatti dell'utente mi fermo
				if(p1==null)return;
				u.getPiatti().remove(p1);
				p1.getUtenti().remove(u);
				piattoRepo.save(p1);
		
	}

	@Override
	public VisualizzaUtenteDTO login(String email, String password) {
//		List<Utente> utenti=utenteRepo.findAll();
//		return utenti.stream()
//				//.parallel()
//				.filter(u->u.getEmail().equals(email))
//				.filter(u->u.getPassword().equals(password))
//				.findAny()
//				.orElse(null);
		Utente u= utenteRepo.findByEmailAndPassword(email.trim(), password)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"nessun utente con questa email e/o password"));
		return mapper.toVisualizzaUtenteDTO(u);
	}

}
