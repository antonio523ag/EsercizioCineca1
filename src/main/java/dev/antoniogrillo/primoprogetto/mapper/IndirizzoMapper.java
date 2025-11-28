package dev.antoniogrillo.primoprogetto.mapper;

import org.springframework.stereotype.Component;

import dev.antoniogrillo.primoprogetto.dto.AggiungiIndirizzoDTO;
import dev.antoniogrillo.primoprogetto.dto.SingoloIndirizzoDTO;
import dev.antoniogrillo.primoprogetto.model.Citta;
import dev.antoniogrillo.primoprogetto.model.Indirizzo;

@Component
public class IndirizzoMapper {
	
	public Indirizzo toIndirizzo(AggiungiIndirizzoDTO dto, Citta c) {
		Indirizzo i=new Indirizzo();
		i.setCitta(c);
		i.setCivico(dto.getCivico());
		i.setVia(dto.getVia());
		return i;
	}
	
	public Indirizzo toIndirizzo(SingoloIndirizzoDTO dto, Citta c) {
		Indirizzo i=new Indirizzo();
		i.setCitta(c);
		i.setCivico(dto.getCivico());
		i.setVia(dto.getVia());
		i.setId(dto.getId());
		return i;
	}
	
	public SingoloIndirizzoDTO toSingoloIndirizzoDTO(Indirizzo i) {
		SingoloIndirizzoDTO s=new SingoloIndirizzoDTO();
		s.setCitta(i.getCitta().getNome());
		s.setVia(i.getVia());
		s.setCivico(i.getCivico());
		s.setId(i.getId());
		return s;
	}
}
