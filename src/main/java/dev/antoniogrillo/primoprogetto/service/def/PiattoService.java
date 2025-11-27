package dev.antoniogrillo.primoprogetto.service.def;

import java.util.List;

import dev.antoniogrillo.primoprogetto.model.Piatto;

public interface PiattoService {
	long aggiungiPiatto(Piatto p);
	void modificaPiatto(Piatto p);
	Piatto getById(long id);
	List<Piatto> getAllPiatti();
	void eliminaPiatto(Piatto p);
	void eliminaPiatto(long id);
	List<Piatto> getPiattiDiAltriUtenti(long idUtente);
}
