package dev.antoniogrillo.primoprogetto.dto;

import java.util.List;

import lombok.Data;

@Data
public class ElencoCittaDTO {
	private int numeroDiPagine;
	private int pagina;
	private List<SingolaCittaDTO> citta;
}
