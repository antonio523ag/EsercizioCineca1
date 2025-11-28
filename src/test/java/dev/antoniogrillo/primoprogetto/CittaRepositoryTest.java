package dev.antoniogrillo.primoprogetto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import dev.antoniogrillo.primoprogetto.model.Citta;
import dev.antoniogrillo.primoprogetto.repository.CittaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
@SpringBootTest
@ContextConfiguration(classes = PrimoProgetto1Application.class)
public class CittaRepositoryTest {

	@Autowired
	private CittaRepository repo;
	
	@Test
	public void testGetCitta() {
		List<Citta> c= repo.findAll();
		assertEquals(7, c.size());
	}
	
	@Test
	public void testGetSingolaCittaByNome() {
		assertThat(repo.findByNome("Roma")).get().extracting(Citta::getId).isEqualTo(1L);
	}
}
