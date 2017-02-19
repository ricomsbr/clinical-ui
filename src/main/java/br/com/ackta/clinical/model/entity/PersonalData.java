package br.com.ackta.clinical.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "personal_data")
public class PersonalData implements IPersonalData {

	private static final long serialVersionUID = -2383673733659048451L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "active", nullable = false)
	private boolean active;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "birth_dt", nullable = false)
	private LocalDate birthDate;

	@Column(name = "gender_id", nullable = false)
	private Gender gender;

	@Column(name = "cpf", nullable = true)
	private String cpf;

	@Column(name = "rg", nullable = true)
	private String rg;

	public PersonalData() {
		super();
	}

	@Override
	public LocalDate getBirthDate() {
		return birthDate;
	}

	@Override
	public String getCpf() {
		return cpf;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getRg() {
		return rg;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	/**
	 * Updates a binded object.
	 *
	 * @param user
	 * @return
	 */
	@Override
	public IPersonalData merge(IPersonalData user) {
		BeanUtils.copyProperties(this, user, UNMERGED_PROPERTIES);
		return user;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
}
