package dev.antoniogrillo.primoprogetto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import dev.antoniogrillo.primoprogetto.dto.SingolaCittaDTO;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ContextConfiguration(classes = PrimoProgetto1Application.class)
@AutoConfigureMockMvc
public class TestCittaController {
	
	@Autowired
	private MockMvc mock;
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	public void testCittaPerId() throws Exception {
		RequestBuilder builder=MockMvcRequestBuilders.get("/all/citta/2");
		ResultMatcher resultMediaType=MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE);
		ResultMatcher resultCode=MockMvcResultMatchers.status().isOk();
		ResultMatcher resultAtrributePresent=MockMvcResultMatchers.jsonPath("$.nome").exists();
		ResultMatcher resultAtrributeValue=MockMvcResultMatchers.jsonPath("$.nome").value("Milano");
		
		mock.perform(builder)
			.andExpect(resultMediaType)
			.andExpect(resultAtrributeValue)
			.andExpect(resultAtrributePresent)
			.andExpect(resultCode)
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@WithUserDetails("a.grillo@gmail.com")
	public void testModificaCitta()throws Exception{
		String json= "{\"id\":1,\"nome\"}";
		SingolaCittaDTO s=new SingolaCittaDTO();
		s.setId(2);
		s.setNome("Pavia");
		s.setVersion(1);
		json=mapper.writeValueAsString(s);
		ResultMatcher resultCode=MockMvcResultMatchers.status().isOk();
		
		RequestBuilder builder=MockMvcRequestBuilders.put("/authorized/citta/modifica")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json);
		mock.perform(builder)
		.andExpect(resultCode)
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@WithUserDetails("s.buda@gmail.com")
	public void testModificaCittaSenzaCredenziali()throws Exception{
		String json= "{\"id\":1,\"nome\"}";
		SingolaCittaDTO s=new SingolaCittaDTO();
		s.setId(2);
		s.setNome("Pavia");
		s.setVersion(1);
		json=mapper.writeValueAsString(s);
		ResultMatcher resultCode=MockMvcResultMatchers.status().isOk();
		
		RequestBuilder builder=MockMvcRequestBuilders.put("/authorized/citta/modifica")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json);
		mock.perform(builder)
		.andExpect(resultCode)
		.andDo(MockMvcResultHandlers.print());
	}
}
