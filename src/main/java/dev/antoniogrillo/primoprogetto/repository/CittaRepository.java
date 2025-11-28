package dev.antoniogrillo.primoprogetto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.antoniogrillo.primoprogetto.model.Citta;

public interface CittaRepository extends JpaRepository<Citta, Long>{
	Optional<Citta> findByNome(String nome);

}
