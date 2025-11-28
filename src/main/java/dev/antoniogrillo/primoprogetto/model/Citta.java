package dev.antoniogrillo.primoprogetto.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data //@Getter,@Setter,@ToString,@EqualsAndHashCode
public class Citta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="nome_della_citta",unique = true)
	private String nome;
	
	@Version
	private long version;

}
