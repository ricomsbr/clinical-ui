package br.com.ackta.clinical.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name = "patient")
public class Patient implements IPatient {

	private static final long serialVersionUID = -8785925386745821405L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "active", nullable = false)
	private boolean active;

	// @OneToOne(targetEntity = PersonalData.class)
	// @JoinColumn(name = "personal_data_id", columnDefinition = "id", nullable
	// = false)
	@Transient
	public PersonalData personalData;

	public Patient() {
		super();
	}

	public Patient(PersonalData personalData) {
		this();
		this.personalData = personalData;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public IPersonalData getPersonalData() {
		return personalData;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
}
