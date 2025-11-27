package dev.antoniogrillo.primoprogetto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.antoniogrillo.primoprogetto.dto.RegistraUtenteDTO;
import dev.antoniogrillo.primoprogetto.dto.VisualizzaUtenteDTO;
import dev.antoniogrillo.primoprogetto.model.Utente;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UtenteMapper {
	
	private final PiattoMapper piattoMapper;
	
	public Utente toUtente(RegistraUtenteDTO dto) {
		Utente u=new Utente();
		u.setCognome(dto.cognome());
		u.setNome(dto.nome());
		u.setEmail(dto.email());
		u.setPassword(dto.password());
		return u;
	}
	
	public VisualizzaUtenteDTO toVisualizzaUtenteDTO(Utente u) {
		VisualizzaUtenteDTO dto=new VisualizzaUtenteDTO();
		dto.setCognome(u.getCognome());
		dto.setNome(u.getNome());
		dto.setEmail(u.getEmail());
		dto.setPiattiScelti(piattoMapper.toSingoloPiattoDTO(u.getPiatti()));
		return dto;
	}
	
	public List<VisualizzaUtenteDTO> toVisualizzaUtenteDTO(List<Utente> l){
		return l==null?new ArrayList<>():l.stream().map(this::toVisualizzaUtenteDTO).toList();
	}
}
