package br.com.ackta.clinical.presentation;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.FamilyMember;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IMedicalHistory;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.Kinship;
import br.com.ackta.clinical.data.entity.MaritalState;
import br.com.ackta.clinical.data.entity.MedicalHistory;
import br.com.ackta.clinical.data.entity.PeriodicityUnit;
import br.com.ackta.clinical.data.entity.PhoneType;
import br.com.ackta.clinical.data.entity.Responsible;

public class Form {
	private static final Gender[] ALL_GENDERS = Gender.values();
	private static final MaritalState[] ALL_MARITAL_STATES = MaritalState.values(); 
	private static final PeriodicityUnit[] ALL_PERIODICITY_UNITS = PeriodicityUnit.values();
	
	private Long id;
	private String cpf;

	@NotNull
	@NotEmpty
	private String name;

	private String rg;
	
	@NotNull
	private Gender gender;
	private String publicArea;
	private String number;
	private String district;
	private String city;
	private String complement;
	private MaritalState maritalState;
	private String mobilePhone;
	private String homePhone;
	private String mail;
	private String profession;
	private String zipCode;
	private String observation;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate birthDate;
	private Integer childrenQty;
	private MedicalHistory medicalHistory;
	private Boolean isMember;
	private String susCard;
	private FamilyMemberHistoryForm motherHistory;
	private FamilyMemberHistoryForm fatherHistory;
	private String responsibleName1;
	private String responsibleName2;
	private String responsiblePhone1;
	private String responsiblePhone2;
	
	/**
	 *
	 */
	public Form() {
		super();
	}

	public Form(IPatient patient, Boolean isMember, String susCardNumber) {
		this();
		IPersonalData personalData = patient.getPersonalData();
		BeanUtils.copyProperties(personalData, this);
		SortedSet<Address> addresses = personalData.getAddresses();
		if(!addresses.isEmpty()) {
			Address address = addresses.first();
			BeanUtils.copyProperties(address, this);
		}
		personalData.getPhones().forEach(p -> {
			if (PhoneType.HOME.equals(p.getType())) {
				this.setHomePhone(p.getNumber());
			}
			if (PhoneType.MOBILE.equals(p.getType())) {
				this.setMobilePhone(p.getNumber());
			}
		});
		medicalHistory = patient.getMedicalHistory();
		this.setMedicalHistory(medicalHistory);

		List<Responsible> respList = patient.getResponsibles();
		if (respList.size() > 0) {
			Responsible resp1 = respList.get(0);
			this.responsibleName1 = resp1.getName();
			this.responsiblePhone1 = resp1.getPhoneNumber();
		}
		if (respList.size() > 1) {
			Responsible resp2 = respList.get(1);
			this.responsibleName2 = resp2.getName();
			this.responsiblePhone2 = resp2.getPhoneNumber();
		}
		this.isMember = isMember;
		this.susCard = susCardNumber;
		this.observation = patient.getObservation();
		List<FamilyMember> familyMembers = patient.getMedicalHistory().getFamilyMembers();
		this.motherHistory = new FamilyMemberHistoryForm(familyMembers
				.stream()
				.filter(m -> Kinship.MOTHER.equals(m.getKinship()))
				.findFirst().get());
		this.fatherHistory = new FamilyMemberHistoryForm(familyMembers
				.stream()
				.filter(m -> Kinship.FATHER.equals(m.getKinship()))
				.findFirst().get());
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Integer getChildrenQty() {
		return childrenQty;
	}

	public String getCity() {
		return city;
	}

	public String getComplement() {
		return complement;
	}

	public String getCpf() {
		return cpf;
	}

	public String getDistrict() {
		return district;
	}

	public FamilyMemberHistoryForm getFatherHistory() {
		return fatherHistory;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public Boolean getIsMember() {
		return isMember;
	}
	public String getMail() {
		return mail;
	}
	public MaritalState getMaritalState() {
		return maritalState;
	}
	public IMedicalHistory getMedicalHistory() {
		return medicalHistory;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public FamilyMemberHistoryForm getMotherHistory() {
		return motherHistory;
	}
	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getObservation() {
		return observation;
	}

	public String getProfession() {
		return profession;
	}

	public String getPublicArea() {
		return publicArea;
	}

	public String getResponsibleName1() {
		return responsibleName1;
	}

	public String getResponsibleName2() {
		return responsibleName2;
	}

	public String getResponsiblePhone1() {
		return responsiblePhone1;
	}

	public String getResponsiblePhone2() {
		return responsiblePhone2;
	}

	public String getRg() {
		return rg;
	}

	public String getSusCard() {
		return susCard;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setChildrenQty(Integer childrenQty) {
		this.childrenQty = childrenQty;
	}

	public void setCity(String city) {
		this.city = city.trim();
	}

	public void setComplement(String complement) {
		this.complement = complement.trim();
	}

	public void setCpf(String cpf) {
		this.cpf = cpf.trim();
	}

	public void setDistrict(String district) {
		this.district = district.trim();
	}

	public void setFatherHistory(FamilyMemberHistoryForm fatherHistory) {
		this.fatherHistory = fatherHistory;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone.trim();
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

	public void setMail(String mail) {
		this.mail = mail.trim();
	}

	public void setMaritalState(MaritalState maritalState) {
		this.maritalState = maritalState;
	}

	public void setMedicalHistory(MedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone.trim();
	}

	public void setMotherHistory(FamilyMemberHistoryForm motherHistory) {
		this.motherHistory = motherHistory;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public void setNumber(String number) {
		this.number = number.trim();
	}

	public void setObservation(String observation) {
		this.observation = observation.trim();
	}

	public void setProfession(String profession) {
		this.profession = profession.trim();
	}

	public void setPublicArea(String publicArea) {
		this.publicArea = publicArea.trim();
	}

	public void setResponsibleName1(String responsibleName1) {
		this.responsibleName1 = responsibleName1.trim();
	}

	public void setResponsibleName2(String responsibleName2) {
		this.responsibleName2 = responsibleName2.trim();
	}
	public void setResponsiblePhone1(String responsiblePhone1) {
		this.responsiblePhone1 = responsiblePhone1.trim();
	}

	public void setResponsiblePhone2(String responsiblePhone2) {
		this.responsiblePhone2 = responsiblePhone2.trim();
	}

	public void setRg(String rg) {
		this.rg = rg.trim();
	}

	public void setSusCard(String susCard) {
		this.susCard = susCard.trim();
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode.trim();
	}

	/**
	 * @return the allGenders
	 */
	public static Gender[] getAllGenders() {
		return ALL_GENDERS;
	}

	/**
	 * @return the allMaritalStates
	 */
	public static MaritalState[] getAllMaritalStates() {
		return ALL_MARITAL_STATES;
	}

	/**
	 * @return the allPeriodicityUnit
	 */
	public static PeriodicityUnit[] getAllPeriodicityUnits() {
		return ALL_PERIODICITY_UNITS;
	}
	
	
}
