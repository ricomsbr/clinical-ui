package br.com.ackta.clinical.business.helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.SortedSet;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IAddress;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.MaritalState;
import br.com.ackta.clinical.data.entity.Phone;
import br.com.ackta.clinical.data.entity.PhoneType;
import br.com.ackta.clinical.presentation.Form;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("test")
public class PatientHelperTest {

	@Autowired
	private PatientHelper helper;

	private Form buildForm() {
		Form form = new Form();
		form.setBirthDate(LocalDate.of(1972, 2, 26));
		form.setCity("Recife");
		form.setZipCode("55002-003");
		form.setComplement("apt 23");
		form.setCpf("834.983.876-84");
		form.setDistrict("Boa Viagem");
		form.setGender(Gender.MALE);
		form.setName("João da Silva");
		form.setNumber("240");
		form.setPublicArea("Rua do Futuro");
		form.setRg("324824-4");
		form.setChilderQty(2);
		form.setHomePhone("34569876");
		form.setMobilePhone("998765432");
		form.setProfession("professor");
		form.setMaritalState(MaritalState.MARRIED);
		form.setMail("joao@ackta.com.br");
		return form;
	}

	@Test
	@Ignore
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testFindOne() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		Form form = buildForm();
		IPatient patient = helper.insert(form);
		assertThat(patient.getId()).isNotNull();
		IPersonalData personalData = patient.getPersonalData();
		assertThat(personalData.getName()).isEqualTo("João da Silva");
		assertThat(personalData.getId()).isNotNull();
		assertThat(personalData.getMail()).isEqualTo("joao@ackta.com.br");
		SortedSet<Address> addresses = personalData.getAddresses();
		assertThat(addresses.size()).isEqualTo(1);
		IAddress firstAddress = addresses.first();
		assertThat(firstAddress.getCity()).isEqualTo("Recife");
		assertThat(firstAddress.getZipCode()).isEqualTo("55002-003");
		Phone homePhone = personalData.getPhones().first();
		Phone mobile = personalData.getPhones().last();
		assertThat(homePhone.getCountryCode()).isEqualTo(55);
		assertThat(homePhone.getNumber()).isEqualTo("34569876");
		assertThat(homePhone.getType()).isEqualTo(PhoneType.HOME);

		assertThat(mobile.getCountryCode()).isEqualTo(55);
		assertThat(mobile.getNumber()).isEqualTo("998765432");
		assertThat(mobile.getType()).isEqualTo(PhoneType.MOBILE);
	}

	@Test
	@Ignore
	public void testSearch() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		final String name2 = "Pedro Coelho";
		final String city2 = "Olinda";
		Form form = buildForm();
		IPatient patient = helper.insert(form);
		form.setCity(city2);
		form.setName(name2);
		IPatient newPatient = helper.update(patient.getId(), form);
		IPersonalData personalData = newPatient.getPersonalData();
		assertThat(personalData.getName()).isEqualTo(name2);
		assertThat(personalData.getMail()).isEqualTo("joao@ackta.com.br");
		Address firstAddress = personalData.getAddresses().first();
		assertThat(firstAddress.getCity()).isEqualTo(city2);
		assertThat(firstAddress.getZipCode()).isEqualTo("55002-003");

	}

}
