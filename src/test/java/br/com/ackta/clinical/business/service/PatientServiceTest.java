package br.com.ackta.clinical.business.service;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.AddressType;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.MaritalState;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PersonalData;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("test")
public class PatientServiceTest {

	@Autowired
	IPatientService service;

	@Test
	public void testGeneratePdf() {
		Patient patient = buildPatient();
		service.generatePdf(patient);
	}

	/**
	 * @return
	 */
	private Patient buildPatient() {
		PersonalData personalData = new PersonalData();
		personalData.setId(1L);
		personalData.setBirthDate(LocalDate.of(2017, 5, 27));
		SortedSet<Address> addresses = new TreeSet<Address>();
		Address address1 = new Address();
		address1.setCity("São Paulo");
		address1.setComplement("apt 34");
		address1.setDistrict("Lapa");
		address1.setNumber("155");
		address1.setPublicArea("Rua Pio XI");
		address1.setType(AddressType.HOME);
		address1.setZipCode("05584-951");
		addresses.add(address1);
		personalData.setAddresses(addresses );
		personalData.setGender(Gender.MALE);
		personalData.setChildrenQty(1);
		personalData.setCpf("123.456.789-22");
		personalData.setMail("mail@ackta.com.br");
		personalData.setMaritalState(MaritalState.MARRIED);
		personalData.setName("Name 1");
		personalData.setProfession("profession");
		personalData.setRg("123.456-88");
		Patient patient = new Patient();
		patient.setId(1L);
		patient.setPersonalData(personalData);
		return patient;
	}

}
