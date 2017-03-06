package br.com.ackta.clinical.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "phone")
public class Phone implements IPhone, Serializable {

	private static final long serialVersionUID = -238364325324238451L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "number", nullable = false)
	private String number;

	@Column(name = "index", nullable = false)
	private Integer index;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "countryCode", nullable = false)
	private Integer countryCode;

	@Column(name = "type", nullable = false)
	private PhoneType type;

	@ManyToOne(targetEntity=PersonalData.class)
	@JoinColumn(nullable=true)
	private IPersonalData personalData;

	public Phone() {
		super();
	}

	public Phone(Integer index1, PhoneType type, Integer countryCode, String number) {
		super();
		this.index = index1;
		this.number = number;
		this.description = type.getKey();
		this.countryCode = countryCode;
		this.type = type;
	}

	@Override
	public Integer getCountryCode() {
		return countryCode;
	}

	@Override
	public String getDescription() {
		return description;
	}

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
	public PhoneType getType() {
		return type;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
