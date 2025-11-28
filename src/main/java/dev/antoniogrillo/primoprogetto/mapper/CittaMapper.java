package dev.antoniogrillo.primoprogetto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import dev.antoniogrillo.primoprogetto.dto.SingolaCittaDTO;
import dev.antoniogrillo.primoprogetto.model.Citta;

@Component
public class CittaMapper {
	
	public Citta toCitta(String citta) {
		Citta c=new Citta();
		if(citta==null||citta.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"devi inserire la citta");
		if(citta.trim().length()==1)c.setNome(citta.trim().toUpperCase());
		String nomeCitta=citta.trim().substring(0, 1).toUpperCase().concat(citta.trim().substring(1).toLowerCase());
		c.setNome(nomeCitta);
		return c;
	}
	
	public Citta toCitta(SingolaCittaDTO dto) {
		Citta c=toCitta(dto.getNome());
		c.setId(dto.getId());
		c.setVersion(dto.getVersion());
		return c;
	}
	
	public SingolaCittaDTO toSingolaCittaDTO(Citta c) {
		SingolaCittaDTO dto=new SingolaCittaDTO();
		dto.setId(c.getId());
		dto.setNome(c.getNome());
		dto.setVersion(c.getVersion());
		return dto;
	}
	
	public List<SingolaCittaDTO> toSingolaCittaDTO(List<Citta> c){
		return c==null?new ArrayList<>():c.stream().map(this::toSingolaCittaDTO).toList();
	}

}
