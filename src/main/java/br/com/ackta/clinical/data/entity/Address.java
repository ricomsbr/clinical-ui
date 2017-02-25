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

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "address")
@SQLDelete(sql = "UPDATE address SET deleted = 1 WHERE id = ? AND version = ?")
@Where(clause="deleted = 0")
public class Address implements IAddress {


	/**
	 *
	 */
	private static final long serialVersionUID = -6294632106116595490L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@Column(name = "publicArea", nullable = true)
	private String publicArea;

	@Column(name = "number", nullable = true)
	private String number;

	@Column(name = "distric", nullable = true)
	private String district;

	@Column(name = "complement", nullable = true)
	private String complement;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "index", nullable = true)
	private Integer index;

	@ManyToOne(targetEntity = PersonalData.class)
	@JoinColumn(name = "personal_data_id", referencedColumnName = "id", nullable = true)
	private IPersonalData personalData;

	public Address() {
		super();
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public String getComplement() {
		return complement;
	}

	@Override
	public String getDistrict() {
		return district;
	}

	@Override
	public Long getId() {
		return id;
	}


	@Override
	public Integer getIndex() {
		return index;
	}
	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public IPersonalData getPersonalData() {
		return personalData;
	}

	@Override
	public String getPublicArea() {
		return publicArea;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public void setPersonalData(IPersonalData personalData) {
		this.personalData = personalData;
	}

	public void setPublicArea(String publicArea) {
		this.publicArea = publicArea;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}

}
