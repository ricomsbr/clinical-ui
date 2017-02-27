package br.com.ackta.clinical.presentation;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.ackta.clinical.data.entity.Address;
import br.com.ackta.clinical.data.entity.AddressType;
import br.com.ackta.clinical.data.entity.Gender;
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
	private Integer mobileRegionalCode;
	private String homePhone;
	private Integer homeRegionalCode;
	private String mail;
	private String profession;
	private String zipCode;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate birthDate;
	private Integer childrenQty;

	/**
	 *
	 */
	public Form() {
		super();
	}
	public Form(IPatient patient) {
		this();
		IPersonalData personalData = patient.getPersonalData();
		BeanUtils.copyProperties(personalData, this);
		Address address = personalData.getAddresses().first();
		BeanUtils.copyProperties(address, this);
		address.setType(AddressType.HOME);
		personalData.getPhones().forEach(p -> {
			if (PhoneType.HOME.equals(p.getType())) {
				this.setHomePhone(p.getNumber());
				this.setHomeRegionalCode(p.getRegionalCode());
			}
			if (PhoneType.MOBILE.equals(p.getType())) {
				this.setMobilePhone(p.getNumber());
				this.setMobileRegionalCode(p.getRegionalCode());
			}
		});
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

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public Integer getHomeRegionalCode() {
		return homeRegionalCode;
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

	public String getMobilePhone() {
		return mobilePhone;
	}

	public Integer getMobileRegionalCode() {
		return mobileRegionalCode;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getProfession() {
		return profession;
	}

	public String getPublicArea() {
		return publicArea;
	}

	public String getRg() {
		return rg;
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

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public void setHomeRegionalCode(Integer homeRegionalCode) {
		this.homeRegionalCode = homeRegionalCode;
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

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setMobileRegionalCode(Integer mobileRegionalCode) {
		this.mobileRegionalCode = mobileRegionalCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public void setPublicArea(String publicArea) {
		this.publicArea = publicArea;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
