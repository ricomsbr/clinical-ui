package br.com.ackta.clinical.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "patient")
@SQLDelete(sql = "UPDATE patient SET deleted = 1 WHERE id = ? AND version = ?")
@Where(clause="deleted = 0")
public class Patient implements IPatient {

	private static final long serialVersionUID = -8785925386745821405L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = PersonalData.class)
	@JoinColumn(name = "personal_data_id", referencedColumnName = "id", nullable = false)
	public IPersonalData personalData;

	public Patient() {
		super();
	}

	public Patient(IPersonalData personalData) {
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
	public boolean isDeleted() {
		return deleted;
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
