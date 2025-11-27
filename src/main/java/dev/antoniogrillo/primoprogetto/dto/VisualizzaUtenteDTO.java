package dev.antoniogrillo.primoprogetto.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisualizzaUtenteDTO {
	private String nome;
	private String cognome;
	private String email;
	private List<SingoloPiattoDTO> piattiScelti;
}
