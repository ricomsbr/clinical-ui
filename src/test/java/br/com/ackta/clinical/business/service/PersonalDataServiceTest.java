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
		assertThatThrownBy(() -> service.validate(personalData))
			.isInstanceOf(ValidatorServiceException.class)
			.extracting("errors")
			.extracting("bindingResult")
			.flatExtracting("allErrors")
			.isNotEmpty()
			.flatExtracting("codes")
			.containsOnly(
			  	"NotBlank.br.com.ackta.clinical.data.entity.PersonalData.name",
			    "NotBlank.name",
			    "NotBlank.java.lang.String",
			    "NotBlank",
			    "NotNull.br.com.ackta.clinical.data.entity.PersonalData.gender",
			    "NotNull.gender",
			    "NotNull.br.com.ackta.clinical.data.entity.Gender",
			    "NotNull",
			    "NotNull.br.com.ackta.clinical.data.entity.PersonalData.childrenQty",
			    "NotNull.childrenQty",
			    "NotNull.java.lang.Integer",
			    "NotNull",
			    "NotBeforeNow.br.com.ackta.clinical.data.entity.PersonalData.birthDate",
			    "NotBeforeNow.birthDate",
			    "NotBeforeNow.java.time.LocalDate",
			    "NotBeforeNow");
	}

}
