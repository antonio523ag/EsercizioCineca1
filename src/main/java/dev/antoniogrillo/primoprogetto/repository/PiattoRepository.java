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
	
	
	@Query(nativeQuery = true ,value="select distinct * from Piatto p "+
					"join piatti_per_utente pu on p.id=pu.piatto_fk "+
					"join Utente u on u.id=pu.utente_fk "+
					"join Indirizzo i on i.id = u.indirizzo_fk "+
					"join Citta c on c.id=i.citta_fk "+
					"where c.nome_della_citta = :citta")
	public List<Piatto> findByCitta(String citta);
}
