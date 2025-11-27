package dev.antoniogrillo.primoprogetto.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;

/**
 * il piatto avrà una descrizione, una lista di ingredienti, gli ingredienti saranno obbligatori
 * visualizzare i piatti non suoi e aggiungere un piatto alla propria lista dei piatti
 */

@Entity
public class Piatto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String descrizione;
	@Column(nullable = false)
	private String listaIngredienti;
	
	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinTable(name = "piatti_per_utente", 
		joinColumns = @JoinColumn(name="piatto_fk"), /*la foreignkey verso la classe dove sto scrivendo*/
		inverseJoinColumns = @JoinColumn(name="utente_fk"), /*la foreignkey verso la classe relazionata*/
		uniqueConstraints = {@UniqueConstraint(name="utente_piatto_unique",columnNames = {"piatto_fk","utente_fk"})})
	private List<Utente> utenti;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getListaIngredienti() {
		return listaIngredienti;
	}
	public void setListaIngredienti(String listaIngredienti) {
		this.listaIngredienti = listaIngredienti;
	}
	public List<Utente> getUtenti() {
		return utenti;
	}
	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}

	
}
