package br.com.ackta.clinical.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

	@OneToOne(cascade={}, targetEntity = PersonalData.class)
	@JoinColumn(name = "personal_data_id", referencedColumnName = "id", nullable = false)
	private PersonalData personalData;

	@Column(name = "observation", nullable=true)
	private String observation;

	@Embedded
	private MedicalHistory medicalHistory;

	@OneToMany(cascade = {CascadeType.ALL}, targetEntity = Responsible.class, mappedBy = "patient", orphanRemoval=true)
	public List<Responsible> responsibles;

	@ElementCollection(targetClass=ConvenantMember.class)
	@CollectionTable(
	        name="convenant_member",
	        joinColumns=@JoinColumn(name="convenant_member_id")
	  )
	private List<ConvenantMember> convenantMembers = new ArrayList<>();

	public Patient() {
		super();
	}

	public Patient(PersonalData personalData) {
		this(personalData, new ArrayList<>(), new ArrayList<>(), null);
	}

	public Patient(PersonalData personalData1,
			List<ConvenantMember> convenantMembers1,
			List<Responsible> responsibles1,
			String observation1) {
		this();
		this.personalData = personalData1;
		this.convenantMembers = convenantMembers1;
		this.responsibles = responsibles1;
		this.observation = observation1;
	}

	@Override
	public List<ConvenantMember> getConvenantMembers() {
		return convenantMembers;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public MedicalHistory getMedicalHistory() {
		return medicalHistory;
	}

	@Override
	public String getObservation() {
		return observation;
	}

	@Override
	public PersonalData getPersonalData() {
		return personalData;
	}

	@Override
	public List<Responsible> getResponsibles() {
		return responsibles;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	public void setConvenantMembers(List<ConvenantMember> convenant) {
		this.convenantMembers = convenant;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setMedicalHistory(MedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public void setPersonalData(PersonalData personalData2) {
		this.personalData = personalData2;
	}

	public void setResponsibles(List<Responsible> responsibles1) {
		this.responsibles = responsibles1;
	}


	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
}
