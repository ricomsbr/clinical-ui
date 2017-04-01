package br.com.ackta.clinical.business.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.business.service.validator.ValidatorServiceException;
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
		personalData.setBirthDate(LocalDate.of(6000, 1, 1));
		assertThatThrownBy(() -> service.validateInsert(personalData))
			.isInstanceOf(ValidatorServiceException.class)
			.extracting("errors")
			.extracting("bindingResult")
			.flatExtracting("allErrors")
			.isNotEmpty()
			.flatExtracting("codes")
			.containsOnly(
					"birthDate.after_now.br.com.ackta.clinical.data.entity.PersonalData.birthDate",
				    "birthDate.after_now.birthDate",
				    "birthDate.after_now.java.time.LocalDate",
				    "birthDate.after_now",
				    "name.is_empty.br.com.ackta.clinical.data.entity.PersonalData.name",
				    "name.is_empty.name",
				    "name.is_empty.java.lang.String",
				    "name.is_empty");
	}
	
	@Test
	public void testValidateInsertOld() {
		PersonalData personalData = new PersonalData();
		personalData.setId(1L);
		personalData.setBirthDate(LocalDate.of(6000, 1, 1));
		assertThatThrownBy(() -> service.validateInsertOld(personalData))
			.isInstanceOf(ValidatorServiceException.class)
			.extracting("errors")
			.extracting("bindingResult")
			.flatExtracting("allErrors")
			.isNotEmpty()
			.flatExtracting("codes")
			.contains(
					"birthDate.after_now.br.com.ackta.clinical.data.entity.PersonalData.birthDate",
				    "birthDate.after_now.birthDate",
				    "birthDate.after_now.java.time.LocalDate",
				    "birthDate.after_now",
				    "name.is_empty.br.com.ackta.clinical.data.entity.PersonalData.name",
				    "name.is_empty.name",
				    "name.is_empty.java.lang.String",
				    "name.is_empty");			
	}

//	@Test
//	public void testValidateInsertNoId() {
//		PersonalData personalData = new PersonalData();
//		assertThatThrownBy(() -> service.validateInsert(personalData))
//			.isInstanceOf(IllegalArgumentException.class).isNotNull();
//	}

}
