package dev.antoniogrillo.primoprogetto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.antoniogrillo.primoprogetto.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	//query nativa
	//utilizzo nomi di tabelle e nomi degli attributi di slq
	@Query(nativeQuery = true,value = "select * from Utente where email = :email and password = :password")
	public Optional<Utente> loginNativa(String email,String password);
	
	//qery Jpql (java persistence query lenguage)
	//utilizzo nome di classi e nomi degli attributi di java
	@Query("select u from Utente u where u.email = :email and u.password = password")
	public Optional<Utente> loginJPQL(String email,String password);
	
	//Annotated query o naming query
	//findBy serve per un Optional
	//findAllBy serve o per una List o per un Set
	//nomiDgliAttibutiJava
	//And/Or/Is/IsNot sono le relazioni per le varie condizioni
	public Optional<Utente> findByEmailAndPassword(String email,String password);
	
	@Query(nativeQuery = true,value = "select * from Utente join Indirizzo on Indirizzo.id = utente.indirizzo_fk join Citta on citta.id = indirizzo.citta_fk where citta.nome_della_citta = :nomeCitta")
	public List<Utente> findUtentiPerCittaNative(String nomeCitta);
	
	@Query("select u from Utente u where u.indirizzo.citta.nome = :nomeDellaCitta")
	public List<Utente> findUtentiPerCittaJPQL(String nomeDellaCitta);
	
	public List<Utente> findAllByIndirizzo_Citta_Nome(String nomeCitta);
	
	public List<Utente> findAllByIndirizzo_ViaAndEmailAndIndirizzoIsNotNull(String via,String Email);
	
}
