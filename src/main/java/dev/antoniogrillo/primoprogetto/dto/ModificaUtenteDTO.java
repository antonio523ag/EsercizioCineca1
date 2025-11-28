package dev.antoniogrillo.primoprogetto.dto;

import lombok.Data;

@Data
public class ModificaUtenteDTO {
	private long id;
	private String nome;
	private String cognome;
	private String password;
}
