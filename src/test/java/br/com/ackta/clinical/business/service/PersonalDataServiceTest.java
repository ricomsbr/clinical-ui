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

import br.com.ackta.clinical.data.entity.PersonalData;
import br.com.ackta.validation.ValidatorServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("test")
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
				    "gender.is_null.br.com.ackta.clinical.data.entity.PersonalData.gender",
				    "gender.is_null.gender",
				    "gender.is_null.br.com.ackta.clinical.data.entity.Gender",
				    "gender.is_null",
				    "childrenQty.is_null.br.com.ackta.clinical.data.entity.PersonalData.childrenQty",
				    "childrenQty.is_null.childrenQty",
				    "childrenQty.is_null.java.lang.Integer",
				    "childrenQty.is_null",
				    "birthDate.not_before_now.br.com.ackta.clinical.data.entity.PersonalData.birthDate",
				    "birthDate.not_before_now.birthDate",
				    "birthDate.not_before_now.java.time.LocalDate",
				    "birthDate.not_before_now",
				    "name.is_empty.br.com.ackta.clinical.data.entity.PersonalData.name",
				    "name.is_empty.name",
				    "name.is_empty.java.lang.String",
				    "name.is_empty");
	}

}
