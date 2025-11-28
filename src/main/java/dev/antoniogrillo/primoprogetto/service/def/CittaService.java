package dev.antoniogrillo.primoprogetto.service.def;

import java.util.List;

import dev.antoniogrillo.primoprogetto.dto.ElencoCittaDTO;
import dev.antoniogrillo.primoprogetto.dto.SingolaCittaDTO;

public interface CittaService {
	
	long aggiungiCitta(String nomeCitta);
	void modificaCitta(SingolaCittaDTO dto);
	SingolaCittaDTO getById(long id);
	SingolaCittaDTO getByNome(String nome);
	ElencoCittaDTO getAll(int pagina);
	void eliminaCitta(long id);

}
