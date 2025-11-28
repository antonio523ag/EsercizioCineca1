package dev.antoniogrillo.primoprogetto.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisualizzaUtenteDTO {
	private String nome;
	private String cognome;
	private String email;
	private String ruolo;
	private List<SingoloPiattoDTO> piattiScelti;
	@JsonIgnore
	private String token;
}
