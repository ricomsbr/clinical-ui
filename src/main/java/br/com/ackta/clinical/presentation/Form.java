package br.com.ackta.clinical.presentation;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.ackta.clinical.model.entity.Gender;
import br.com.ackta.clinical.model.entity.IPatient;

public class Form {
	private Long id;
	private String cpf;
	private String name;
	private String rg;
	private Gender gender;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate birthDate;

	/**
	 *
	 */
	public Form() {
		super();
	}

	public Form(IPatient patient) {
		this();
		BeanUtils.copyProperties(patient.getPersonalData(), this);
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public String getName() {
		return name;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


}
