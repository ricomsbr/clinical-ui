package br.com.ackta.clinical.business;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Form {
	private String cpf;
	private String name;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate birthDate;

	/**
	 *
	 */
	public Form() {
		super();
	}

	/**
	 * @param cnpj
	 * @param name
	 * @param emitente
	 */
	public Form(String cpf, String name, LocalDate birthDate) {
		super();
		this.cpf = cpf;
		this.name = name;
		this.birthDate = birthDate;
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


}
