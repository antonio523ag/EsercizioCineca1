package dev.antoniogrillo.primoprogetto.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.antoniogrillo.primoprogetto.dto.ElencoCittaDTO;
import dev.antoniogrillo.primoprogetto.dto.SingolaCittaDTO;
import dev.antoniogrillo.primoprogetto.mapper.CittaMapper;
import dev.antoniogrillo.primoprogetto.model.Citta;
import dev.antoniogrillo.primoprogetto.repository.CittaRepository;
import dev.antoniogrillo.primoprogetto.service.def.CittaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CittaServiceImpl implements CittaService{
	
	private final CittaRepository repo;
	private final CittaMapper mapper;
	
	@Override
	public long aggiungiCitta(String nomeCitta) {
		Citta c=mapper.toCitta(nomeCitta);
		c=repo.save(c);
		return c.getId();
	}
	@Override
	public void modificaCitta(SingolaCittaDTO dto) {
		Citta c=mapper.toCitta(dto);
		repo.save(c);
		
	}
	@Override
	public SingolaCittaDTO getById(long id) {
		Citta c= repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"nessuna citta con questo id"));
		return mapper.toSingolaCittaDTO(c);
	}
	@Override
	public SingolaCittaDTO getByNome(String nome) {
		Citta c=repo.findByNome(nome.trim()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"nessuna citta con questo nome"));
		return mapper.toSingolaCittaDTO(c);
	}
	@Override
	public ElencoCittaDTO getAll(int pagina) {
		pagina=pagina-1;
		if(pagina<0)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"non esistono pagine negative");
		Sort s=Sort.by("nome").ascending();
		Pageable p=PageRequest.of(pagina, 5,s);
		Page<Citta> c=repo.findAll(p);
		List<SingolaCittaDTO> list= mapper.toSingolaCittaDTO(c.getContent());
		ElencoCittaDTO result=new ElencoCittaDTO();
		result.setCitta(list);
		result.setPagina(pagina+1);
		result.setNumeroDiPagine(c.getTotalPages());
		return result;
	}
	@Override
	public void eliminaCitta(long id) {
		repo.deleteById(id);
		
	}
}
