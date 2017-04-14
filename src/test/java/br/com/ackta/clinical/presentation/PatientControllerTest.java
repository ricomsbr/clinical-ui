package br.com.ackta.clinical.presentation;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.ClinicalApplicationTests;
import br.com.ackta.clinical.ControllerTest;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.MaritalState;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ClinicalApplicationTests.class})
@WebAppConfiguration
@Rollback
@Transactional
@ActiveProfiles("test")
public class PatientControllerTest extends ControllerTest {

	private Form buildForm() {
		Form form = new Form();
		form.setId(1L);
		form.setBirthDate(LocalDate.of(2017, 5, 27));
		form.setCity("São Paulo");
		form.setComplement("apt 34");
		form.setDistrict("Lapa");
		form.setNumber("155");
		form.setPublicArea("Rua Pio XI");
		form.setZipCode("05584-951");
		form.setGender(Gender.MALE);
		form.setChildrenQty(1);
		form.setCpf("123.456.789-22");
		form.setMail("mail@ackta.com.br");
		form.setMaritalState(MaritalState.MARRIED);
		form.setName("Name 1");
		form.setProfession("profession");
		form.setRg("123.456-88");
		return form;
	}
	
	@Test
	public void testGoToAdd() throws Exception {
		final Form form = buildForm();
		String str = this.mockMvc.perform(
				MockMvcRequestBuilders
				.get("/patients/insert")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json(form))).andExpect(
						MockMvcResultMatchers.status().isOk())
		.andReturn().getResponse().getContentAsString();
		System.out.println(str);
	}
}
