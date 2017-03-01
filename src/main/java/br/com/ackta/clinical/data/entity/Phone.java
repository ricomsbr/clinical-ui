package br.com.ackta.clinical.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Phone implements IPhone {

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
}
