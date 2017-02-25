package br.com.ackta.clinical.presentation;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.clinical.data.entity.IPatient;

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

	public String getRg() {
		return rg;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public void setRg(String rg) {
		this.rg = rg;
	}


}
