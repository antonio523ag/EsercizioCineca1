package dev.antoniogrillo.primoprogetto.service.def;

import dev.antoniogrillo.primoprogetto.dto.AggiungiIndirizzoDTO;
import dev.antoniogrillo.primoprogetto.dto.SingoloIndirizzoDTO;

public interface IndirizzoService {
	long aggiungiIndirizzo(AggiungiIndirizzoDTO dto);
	void modificaIndirizzo(SingoloIndirizzoDTO dto);
	void elimina(long id);
	SingoloIndirizzoDTO getById(long id);
	SingoloIndirizzoDTO getByIdUtente(long id);
}
