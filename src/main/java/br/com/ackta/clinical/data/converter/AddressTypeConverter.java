package br.com.ackta.clinical.data.converter;

import br.com.ackta.clinical.data.entity.AddressType;

public class AddressTypeConverter implements PersistableEnumConverter<AddressType> {

	@Override
	public AddressType convertToEntityAttribute(Long dbData) {
		return AddressType.findById(dbData);
	}

}
