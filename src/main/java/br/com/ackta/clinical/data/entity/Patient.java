package br.com.ackta.clinical.data.entity;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.assertj.core.util.Lists;
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
	private IPersonalData personalData;

	@Column(name = "observation", nullable=true)
	private String observation;

	@Embedded
	private IMedicalHistory medicalHistory;

	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = PersonalData.class)
	@JoinTable(name = "patient_responsible",
			joinColumns = @JoinColumn(name = "personal_data_id", referencedColumnName = "id", nullable = false),
			inverseJoinColumns={@JoinColumn(name="patient_id")}
	)
	public List<IPersonalData> responsibles;

	@ElementCollection(targetClass=ConvenantMember.class)
	@CollectionTable(
	        name="ConvenantMember",
	        joinColumns=@JoinColumn(name="convenant_member_id")
	  )
	private List<IConvenantMember> convenantMembers;

	public Patient() {
		super();
	}

	public Patient(IPersonalData personalData) {
		this(personalData, Lists.newArrayList(), Lists.newArrayList());
	}

	public Patient(IPersonalData personalData, List<IConvenantMember> convenantMembers, List<IPersonalData> responsibles) {
		this();
		this.personalData = personalData;
		this.convenantMembers = convenantMembers;
		this.responsibles = responsibles;
	}

	@Override
	public List<IConvenantMember> getConvenantMembers() {
		return convenantMembers;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public IMedicalHistory getMedicalHistory() {
		return medicalHistory;
	}

	@Override
	public String getObservation() {
		return observation;
	}

	@Override
	public IPersonalData getPersonalData() {
		return personalData;
	}

	@Override
	public List<IPersonalData> getResponsibles() {
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

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setMedicalHistory(IMedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
}
