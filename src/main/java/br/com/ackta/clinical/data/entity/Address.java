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

	@Column(name = "index", nullable = false)
	private Integer index;

	@Column(name = "zip_code", nullable = true)
	private String zipCode;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "type", nullable = false)
	private AddressType type;

	public Address() {
		super();
	}

	public Address(Integer index1, AddressType type) {
		super();
		this.index = index1;
		this.type = type;
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
	public String getDescription() {
		return description;
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

	@Override
	public AddressType getType() {
		return type;
	}

	@Override
	public String getZipCode() {
		return zipCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setType(AddressType type) {
		this.type = type;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
