package dev.antoniogrillo.primoprogetto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import dev.antoniogrillo.primoprogetto.model.Piatto;
import dev.antoniogrillo.primoprogetto.model.Utente;
import jakarta.transaction.Transactional;

public interface PiattoRepository extends JpaRepository<Piatto, Long> {
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value = "delete from Piatto where id = :id and id not in(select piatto_fk from piatti_per_utente)")
	void deletePiatto(long id);
	
	@Query("select p from Piatto p where :u not member of p.utenti")
	public List<Piatto> findAltriPiatti(Utente u);
}
