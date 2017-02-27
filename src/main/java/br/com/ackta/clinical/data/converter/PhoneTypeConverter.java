package br.com.ackta.clinical.data.converter;

import br.com.ackta.clinical.data.entity.PhoneType;

public class PhoneTypeConverter implements PersistableEnumConverter<PhoneType> {

	@Override
	public PhoneType convertToEntityAttribute(Long dbData) {
		return PhoneType.findById(dbData);
	}

}
