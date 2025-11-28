package dev.antoniogrillo.primoprogetto.service.def;

import java.util.List;

import dev.antoniogrillo.primoprogetto.dto.AggiungiPiattoDTO;
import dev.antoniogrillo.primoprogetto.dto.SingoloPiattoDTO;
import dev.antoniogrillo.primoprogetto.model.Piatto;

public interface PiattoService {
	long aggiungiPiatto(AggiungiPiattoDTO p);
	void modificaPiatto(SingoloPiattoDTO p);
	SingoloPiattoDTO getById(long id);
	List<SingoloPiattoDTO> getAllPiatti(long id);
	void eliminaPiatto(Piatto p);
	void eliminaPiatto(long id);
	List<SingoloPiattoDTO> getPiattiDiAltriUtenti(long idUtente);
	List<SingoloPiattoDTO> getPiattiPerCitta(String nomeCitta);
}
