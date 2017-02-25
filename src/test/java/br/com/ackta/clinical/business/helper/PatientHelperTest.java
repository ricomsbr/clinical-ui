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
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IAddress;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.presentation.Form;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Profile("test")
public class PatientHelperTest {

	@Autowired
	private PatientHelper helper;

	private Form buildForm(final String name, final String city) {
		Form form = new Form();
		form.setBirthDate(LocalDate.of(1972, 2, 26));
		form.setCity(city);
		form.setComplement("apt 23");
		form.setCpf("834.983.876-84");
		form.setDistrict("Boa Viagem");
		form.setGender(Gender.MALE);
		form.setName(name);
		form.setNumber("240");
		form.setPublicArea("Rua do Futuro");
		form.setRg("324824-4");
		form.setChilderQty(2);
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
		final String name = "João das Couves";
		final String city = "Recife";
		Form form = buildForm(name, city);
		IPatient patient = helper.insert(form);
		assertThat(patient.getId()).isNotNull();
		IPersonalData personalData = patient.getPersonalData();
		assertThat(personalData.getName()).isEqualTo(name);
		assertThat(personalData.getId()).isNotNull();
		SortedSet<Address> addresses = personalData.getAddresses();
		assertThat(addresses.size()).isEqualTo(1);
		IAddress firstAddress = addresses.first();
		assertThat(firstAddress.getCity()).isEqualTo(city);
	}

	@Test
	@Ignore
	public void testSearch() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		final String name = "João da Silva";
		final String name2 = "Pedro Coelho";
		final String city = "Recife";
		final String city2 = "Olinda";
		Form form = buildForm(name, city);
		IPatient patient = helper.insert(form);
		form.setCity(city2);
		form.setName(name2);
		IPatient newPatient = helper.update(patient.getId(), form);
		assertThat(newPatient.getPersonalData().getName()).isEqualTo(name2);
		assertThat(newPatient.getPersonalData().getAddresses().first().getCity()).isEqualTo(city2);
	}

}
