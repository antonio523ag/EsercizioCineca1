package dev.antoniogrillo.primoprogetto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.antoniogrillo.primoprogetto.model.Indirizzo;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long>{
	
	@Query("select i from Indirizzo i join Utente u on u.indirizzo.id = i.id where u.id = :id")
	Optional<Indirizzo> findByIdUtente(long id);
}
