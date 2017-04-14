package br.com.ackta.clinical.data.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.ClinicalApplicationTests;
import br.com.ackta.clinical.data.entity.PersonalData;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ClinicalApplicationTests.class})
@WebAppConfiguration
@Rollback
@Transactional
@ActiveProfiles("test")
public class PersonalDataRepositoryTest {

	@Autowired
	private PersonalDataRepository repository;

	@Test
	public void testFindOneID() {
		PersonalData personalData = new PersonalData();
		personalData.setName("name1");
		personalData.setBirthDate(LocalDate.of(6000, 1, 1));
		personalData.setCpf("11111111199");
		personalData.setRg("111222");
		PersonalData saved = repository.save(personalData);
		saved.setCpf("22233322299");
		PersonalData notUpdated = repository.findOne(saved.getId());
		assertEquals("11111111199", notUpdated.getCpf());
	}

}
