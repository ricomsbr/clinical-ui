package br.com.ackta.clinical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "responsible")
public class Responsible implements IResponsible {

	/**
	 *
	 */
	private static final long serialVersionUID = 2629660265391426642L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "name", nullable=false)
	private String name;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@ManyToOne(targetEntity=Patient.class)
	@JoinColumn(nullable=true)
	private Patient patient;

	public Responsible() {
		super();
	}

	public Responsible(String name1, String phoneNumber1, Patient patient1) {
		this();
		this.name = name1;
		this.phoneNumber = phoneNumber1;
		this.patient = patient1;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public Patient getPatient() {
		return patient;
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Long getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name1) {
		this.name = name1;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setPhoneNumber(String phoneNumber1) {
		this.phoneNumber = phoneNumber1;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
