package br.com.ackta.clinical.business.helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

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
import br.com.ackta.clinical.data.entity.AddressType;
import br.com.ackta.clinical.data.entity.Convenant;
import br.com.ackta.clinical.data.entity.ConvenantMember;
import br.com.ackta.clinical.data.entity.FamilyMember;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IAddress;
import br.com.ackta.clinical.data.entity.IMedicalHistory;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.Kinship;
import br.com.ackta.clinical.data.entity.MaritalState;
import br.com.ackta.clinical.data.entity.MedicalHistory;
import br.com.ackta.clinical.data.entity.Patient;
import br.com.ackta.clinical.data.entity.PeriodicityUnit;
import br.com.ackta.clinical.data.entity.Phone;
import br.com.ackta.clinical.data.entity.PhoneType;
import br.com.ackta.clinical.data.repository.PatientRepository;
import br.com.ackta.clinical.presentation.FamilyMemberHistoryForm;
import br.com.ackta.clinical.presentation.Form;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("development")
public class PatientHelperTest {

	@Autowired
	private PatientHelper helper;

	@Autowired
	private PatientRepository repository;

	private Form buildForm() {
		Form form = new Form();
		form.setBirthDate(LocalDate.of(1972, 2, 26));
		form.setChildrenQty(Integer.valueOf(2));
		form.setCity("Recife");
		form.setChildrenQty(3);
		form.setComplement("apt 23");
		form.setCpf("834.983.876-84");
		form.setDistrict("Boa Viagem");
		FamilyMemberHistoryForm fatherHistory = new FamilyMemberHistoryForm();
		fatherHistory.setAge(55);
		fatherHistory.setAlive(true);
		fatherHistory.setBirthYear(null);
		fatherHistory.setDiseases("doenças do pai");
		fatherHistory.setKinship(Kinship.FATHER);
		fatherHistory.setMedicines("medicamentos do pai");
		form.setFatherHistory(fatherHistory );
		form.setGender(Gender.MALE);
		form.setHomePhone("34569876");
		form.setIsMember(true);
		form.setMail("joao@ackta.com.br");
		form.setMaritalState(MaritalState.MARRIED);
		MedicalHistory medicalHistory = buildMedicalHistory();
		form.setMedicalHistory(medicalHistory);
		form.setMobilePhone("998765432");
		FamilyMemberHistoryForm motherHistory = new FamilyMemberHistoryForm();
		motherHistory.setAge(54);
		motherHistory.setAlive(false);
		motherHistory.setBirthYear(null);
		motherHistory.setDiseases("doenças da mãe");
		motherHistory.setKinship(Kinship.MOTHER);
		motherHistory.setMedicines("medicamentos da mãe");
		form.setMotherHistory(motherHistory);
		form.setName("João da Silva");
		form.setNumber("240");
		form.setObservation("observation1");
		form.setProfession("professor");
		form.setPublicArea("Rua do Futuro");
		form.setResponsibleName1("responsibleName1");
		form.setResponsibleName2("responsibleName2");
		form.setResponsiblePhone1("(11) 92834-9873");
		form.setResponsiblePhone2("(11) 92834-9553");
		form.setRg("324824-4");
		form.setSusCard("888.333-87");
		form.setZipCode("55002-003");
		return form;
	}

	private MedicalHistory buildMedicalHistory() {
		MedicalHistory medicalHistory= new MedicalHistory();
		medicalHistory.setAllergic(true);
		medicalHistory.setAllergies("allergies");
		medicalHistory.setDiseases("diseases1");
		medicalHistory.setDrinker(true);
		medicalHistory.setDrinkFrequence(3);
		medicalHistory.setDrinkPeriodicityUnit(PeriodicityUnit.WEEKLY);
		medicalHistory.setHasDiseases(false);
		medicalHistory.setHasSurgeries(false);
		medicalHistory.setHeight(123.0);
		medicalHistory.setMedicines("medicamentos1");
		medicalHistory.setSmokeFrequence(1);
		medicalHistory.setSmokePeriodicityUnit(PeriodicityUnit.DAILY);
		medicalHistory.setSmoker(true);
		medicalHistory.setSurgeries("cirurgias1");
		medicalHistory.setWeight(89.5);
		return medicalHistory;
	}

	@Test
	@Ignore
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOne() {
		Form form = buildForm();
		IPatient patient = helper.insert(form);
		Form result = helper.findOne(patient.getId());
		assertThat(result.getId()).isNotNull();
		assertThat(result.getObservation()).isEqualTo("observation1");
		assertThat(result.getCity()).isEqualTo("Recife");
		assertThat(result.getComplement()).isEqualTo("apt 23");
		assertThat(result.getDistrict()).isEqualTo("Boa Viagem");
		assertThat(result.getNumber()).isEqualTo("240");
		assertThat(result.getPublicArea()).isEqualTo("Rua do Futuro");
		assertThat(result.getZipCode()).isEqualTo("55002-003");
		assertThat(result.getBirthDate()).isEqualTo(LocalDate.of(1972, 2, 26));
		assertThat(result.getChildrenQty()).isEqualTo(3);
		assertThat(result.getCpf()).isEqualTo("834.983.876-84");
		assertThat(result.getGender()).isEqualTo(Gender.MALE);
		assertThat(result.getId()).isNotNull();
		assertThat(result.getMail()).isEqualTo("joao@ackta.com.br");
		assertThat(result.getMaritalState()).isEqualTo(MaritalState.MARRIED);
		assertThat(result.getName()).isEqualTo("João da Silva");
		String homePhone = result.getHomePhone();
		String mobile = result.getMobilePhone();
		assertThat(homePhone).isEqualTo("34569876");
		assertThat(mobile).isEqualTo("998765432");
		assertThat(result.getRg()).isEqualTo("324824-4");
		assertThat(result.getIsMember()).isEqualTo(true);
		assertThat(result.getSusCard()).isEqualTo("888.333-87");
		IMedicalHistory medicalHist = result.getMedicalHistory();
		assertThat(medicalHist.getAllergic()).isEqualTo(true);
		assertThat(medicalHist.getAllergies()).isEqualTo("allergies");
		assertThat(medicalHist.getDate()).isNotNull();
		assertThat(medicalHist.getDiseases()).isEqualTo("diseases1");
		assertThat(medicalHist.getDrinker()).isEqualTo(true);
		assertThat(medicalHist.getDrinkFrequence()).isEqualTo(3);
		assertThat(medicalHist.getDrinkPeriodicityUnit()).isEqualTo(PeriodicityUnit.WEEKLY);
		assertThat(result.getMotherHistory().getKinship()).isEqualTo(Kinship.MOTHER);
		assertThat(result.getMotherHistory().getMedicines()).isEqualTo("medicamentos da mãe");
		assertThat(result.getMotherHistory().getDiseases()).isEqualTo("doenças da mãe");
		assertThat(result.getFatherHistory().getKinship()).isEqualTo(Kinship.FATHER);
		assertThat(result.getFatherHistory().getMedicines()).isEqualTo("medicamentos do pai");
		assertThat(result.getFatherHistory().getDiseases()).isEqualTo("doenças do pai");
		
		assertThat(result.getResponsibleName1()).isEqualTo("responsibleName1");
		assertThat(result.getResponsiblePhone1()).isEqualTo("(11) 92834-9873");
		assertThat(result.getResponsibleName2()).isEqualTo("responsibleName2");
		assertThat(result.getResponsiblePhone2()).isEqualTo("(11) 92834-9553");
		
	}

	@Test
	public void testInsert() {
		Form form = buildForm();
		IPatient patientDb = helper.insert(form);
		Patient patient = repository.findOne(patientDb.getId());

		assertThat(patient.getId()).isNotNull();
		assertThat(patient.getObservation()).isEqualTo("observation1");
		IPersonalData personalData = patient.getPersonalData();
		SortedSet<Address> addresses = personalData.getAddresses();
		assertThat(addresses.size()).isEqualTo(1);
		IAddress firstAddress = addresses.first();
		assertThat(firstAddress.getCity()).isEqualTo("Recife");
		assertThat(firstAddress.getComplement()).isEqualTo("apt 23");
		assertThat(firstAddress.getDescription()).isNull();
		assertThat(firstAddress.getDistrict()).isEqualTo("Boa Viagem");
		assertThat(firstAddress.getIndex()).isEqualTo(1);
		assertThat(firstAddress.getNumber()).isEqualTo("240");
		assertThat(firstAddress.getPublicArea()).isEqualTo("Rua do Futuro");
		assertThat(firstAddress.getType()).isEqualTo(AddressType.HOME);
		assertThat(firstAddress.getZipCode()).isEqualTo("55002-003");
		assertThat(personalData.getBirthDate()).isEqualTo(LocalDate.of(1972, 2, 26));
		assertThat(personalData.getChildrenQty()).isEqualTo(3);
		assertThat(personalData.getCpf()).isEqualTo("834.983.876-84");
		assertThat(personalData.getGender()).isEqualTo(Gender.MALE);
		assertThat(personalData.getId()).isNotNull();
		assertThat(personalData.getMail()).isEqualTo("joao@ackta.com.br");
		assertThat(personalData.getMaritalState()).isEqualTo(MaritalState.MARRIED);
		assertThat(personalData.getName()).isEqualTo("João da Silva");
		Phone homePhone = personalData.getPhones().first();
		Phone mobile = personalData.getPhones().last();
		assertThat(homePhone.getCountryCode()).isEqualTo(55);
		assertThat(homePhone.getNumber()).isEqualTo("34569876");
		assertThat(homePhone.getType()).isEqualTo(PhoneType.HOME);
		assertThat(mobile.getCountryCode()).isEqualTo(55);
		assertThat(mobile.getNumber()).isEqualTo("998765432");
		assertThat(mobile.getType()).isEqualTo(PhoneType.MOBILE);
		assertThat(personalData.getRg()).isEqualTo("324824-4");
		assertThat(personalData.getVersion()).isEqualTo(0);
		List<ConvenantMember> convenantMembers = patient.getConvenantMembers();
		assertThat(convenantMembers)
			.filteredOn("convenant", Convenant.SUS)
			.extracting("cardNumber")
			.first()
			.isEqualTo("888.333-87");
		assertThat(convenantMembers)
			.filteredOn("convenant", Convenant.OWN)
			.first()
			.isNotNull();
		IMedicalHistory medicalHist = patient.getMedicalHistory();
		assertThat(medicalHist.getAllergic()).isEqualTo(true);
		assertThat(medicalHist.getAllergies()).isEqualTo("allergies");
		assertThat(medicalHist.getDate()).isNotNull();
		assertThat(medicalHist.getDiseases()).isEqualTo("diseases1");
		assertThat(medicalHist.getDrinker()).isEqualTo(true);
		assertThat(medicalHist.getDrinkFrequence()).isEqualTo(3);
		assertThat(medicalHist.getDrinkPeriodicityUnit()).isEqualTo(PeriodicityUnit.WEEKLY);
		List<FamilyMember> memberHistories = medicalHist.getFamilyMembers();
		assertThat(memberHistories)
			.filteredOn("kinship", Kinship.MOTHER)
			.extracting("medicines")
			.first()
			.isEqualTo("medicamentos da mãe");
		assertThat(memberHistories)
			.filteredOn("kinship", Kinship.MOTHER)
			.extracting("medicines")
			.first()
			.isEqualTo("medicamentos da mãe");
		assertThat(memberHistories)
		.filteredOn("kinship", Kinship.MOTHER)
		.extracting("diseases")
		.first()
		.isEqualTo("doenças da mãe");

		assertThat(patient.getResponsibles())
			.extracting("name").containsOnly("responsibleName1", "responsibleName2");
		List<Phone> phones = patient.getResponsibles()
			.stream()
			.flatMap(r -> r.getPhones().stream())
			.collect(Collectors.toList());
		assertThat(phones)
			.extracting("number")
			.containsOnly("(11) 92834-9873", "(11) 92834-9553");
		assertThat(patient.getVersion()).isEqualTo(0);
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
