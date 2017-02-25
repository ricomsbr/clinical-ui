package br.com.ackta.clinical.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements IAddress {

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
	public Integer getIndex() {
		return index;
	}
	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public String getPublicArea() {
		return publicArea;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPublicArea(String publicArea) {
		this.publicArea = publicArea;
	}

}
