package br.com.ackta.clinical.business.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.PersonalData;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("development")
public class PersonalDataServiceTest {

	@Autowired
	PersonalDataService service;

	@Test
	public void testValidateInsert() {
		PersonalData personalData = new PersonalData();
		personalData.setId(1L);
		assertThatThrownBy(() -> service.validateInsert(personalData))
			.isInstanceOf(ServiceException.class)
			.extracting("cause")
			.extracting("allErrors")
			.isNotEmpty();
	}

	@Test
	public void testValidateInsertNoId() {
		PersonalData personalData = new PersonalData();
		assertThatThrownBy(() -> service.validateInsert(personalData))
			.isInstanceOf(IllegalArgumentException.class).isNotNull();
	}

}
