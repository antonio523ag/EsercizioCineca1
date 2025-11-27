package dev.antoniogrillo.primoprogetto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.antoniogrillo.primoprogetto.dto.SingoloPiattoDTO;
import dev.antoniogrillo.primoprogetto.model.Piatto;


@Component
public class PiattoMapper {
	public SingoloPiattoDTO toSingoloPiattoDTO(Piatto p) {
		if(p==null)return null;
		SingoloPiattoDTO dto=new SingoloPiattoDTO();
		dto.setId(p.getId());
		dto.setIngredienti(p.getListaIngredienti());
		dto.setNomePiatto(p.getDescrizione());
		return dto;
	}
	
	public List<SingoloPiattoDTO> toSingoloPiattoDTO(List<Piatto> p){
		return p==null?new ArrayList<>():p.stream()
				/*.map(this::toSingoloPiattoDTO)*/
				.map(p1->this.toSingoloPiattoDTO(p1))
				.toList();
	}
}
