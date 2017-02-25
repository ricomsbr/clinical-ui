package br.com.ackta.clinical.presentation;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IPatient;
import br.com.ackta.clinical.data.entity.IPersonalData;

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
		BeanUtils.copyProperties(personalData.getAddresses().first(), this);
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getPublicArea() {
		return publicArea;
	}

	public String getRg() {
		return rg;
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

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPublicArea(String publicArea) {
		this.publicArea = publicArea;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}
}
