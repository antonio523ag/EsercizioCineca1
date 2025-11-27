package dev.antoniogrillo.primoprogetto.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * l'utente avrà nome, cognome, email, password
 * email non sarà possibile modificarla dopo la creazione
 * password sarà obbligatoria
 */
@Entity
@NoArgsConstructor //crea un costruttore vuoto
@AllArgsConstructor //crea un costruttore con tutti i parametri nell'ordine in cui sono definiti nella classe
@Getter //crea i getter per tutte le variabili
@Setter //crea i setter per tutte le variabili
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String password;
	@Column(unique = true,updatable = false)
	private String email;
	private String nome;
	private String cognome;
	
	@ManyToMany(mappedBy = "utenti")
	private List<Piatto> piatti;
	
	@ManyToOne
	@JoinColumn(name="indirizzo_fk")
	private Indirizzo indirizzo;
	
}
