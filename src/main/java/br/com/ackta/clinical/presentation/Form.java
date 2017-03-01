package br.com.ackta.clinical.presentation;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.AddressType;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IFamilyMemberHistory;
import br.com.ackta.clinical.data.entity.IMedicalHistory;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.IPersonalData;
import br.com.ackta.clinical.data.entity.MaritalState;
import br.com.ackta.clinical.data.entity.PhoneType;

public class Form {
	private Long id;
	private String cpf;
	private String name;
	private String rg;
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
	private IMedicalHistory medicalHistory;
	private Boolean ownConvenantMember;
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

	public Form(IPatient patient, Boolean isOwnConvenantMember, String susCardNumber) {
		this();
		IPersonalData personalData = patient.getPersonalData();
		BeanUtils.copyProperties(personalData, this);
		Address address = personalData.getAddresses().first();
		BeanUtils.copyProperties(address, this);
		address.setType(AddressType.HOME);
		personalData.getPhones().forEach(p -> {
			if (PhoneType.HOME.equals(p.getType())) {
				this.setHomePhone(p.getNumber());
			}
			if (PhoneType.MOBILE.equals(p.getType())) {
				this.setMobilePhone(p.getNumber());
			}
		});
		medicalHistory = patient.getMedicalHistory();
		this.ownConvenantMember = isOwnConvenantMember;
		this.susCard = susCardNumber;
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

	public IFamilyMemberHistory getFatherHistory() {
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
	public IFamilyMemberHistory getMotherHistory() {
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

	public Boolean getOwnConvenantMember() {
		return ownConvenantMember;
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

	public void setChilderQty(int i) {
		childrenQty = i;
	}

	public void setChildrenQty(Integer childrenQty) {
		this.childrenQty = childrenQty;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setDistrict(String district) {
		this.district = district;
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
		this.homePhone = homePhone;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setMaritalState(MaritalState maritalState) {
		this.maritalState = maritalState;
	}

	public void setMedicalHistory(IMedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setMotherHistory(FamilyMemberHistoryForm motherHistory) {
		this.motherHistory = motherHistory;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public void setOwnConvenantMember(Boolean ownConvenantMember) {
		this.ownConvenantMember = ownConvenantMember;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public void setPublicArea(String publicArea) {
		this.publicArea = publicArea;
	}

	public void setResponsibleName1(String responsibleName1) {
		this.responsibleName1 = responsibleName1;
	}

	public void setResponsibleName2(String responsibleName2) {
		this.responsibleName2 = responsibleName2;
	}
	public void setResponsiblePhone1(String responsiblePhone1) {
		this.responsiblePhone1 = responsiblePhone1;
	}

	public void setResponsiblePhone2(String responsiblePhone2) {
		this.responsiblePhone2 = responsiblePhone2;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public void setSusCard(String susCard) {
		this.susCard = susCard;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
