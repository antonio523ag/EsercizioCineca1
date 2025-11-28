package dev.antoniogrillo.primoprogetto.dto;

import lombok.Data;

@Data
public class SingoloIndirizzoDTO {
	private long id;
	private String via;
	private String civico;
	private String citta;
}
