package dev.antoniogrillo.primoprogetto.service.def;

import java.util.List;

import dev.antoniogrillo.primoprogetto.dto.RegistraUtenteDTO;
import dev.antoniogrillo.primoprogetto.dto.VisualizzaUtenteDTO;
import dev.antoniogrillo.primoprogetto.model.Utente;

public interface UtenteService {
	void registraUtente(RegistraUtenteDTO u);
	void modificaUtente(Utente u);
	VisualizzaUtenteDTO getById(long id);
	List<VisualizzaUtenteDTO> getAllUtenti();
	void eliminaUtente(Utente u);
	void eliminaUtente(long id);
	void aggiungiPiatto(long idUtente,long idPiatto);
	void rimuoviPiatto(long idUtente,long idPiatto);
	VisualizzaUtenteDTO login(String email,String password);
}
