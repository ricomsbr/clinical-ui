package br.com.ackta.clinical.business;

import org.springframework.format.annotation.DateTimeFormat;

public class Form {
	private String cpf;
	private String name;

	@DateTimeFormat(pattern="dd/MM/yyyy")
	private String birthDate;

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
	public Form(String cpf, String name, String birthDate) {
		super();
		this.cpf = cpf;
		this.name = name;
		this.birthDate = birthDate;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public String getName() {
		return name;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setName(String name) {
		this.name = name;
	}


}
