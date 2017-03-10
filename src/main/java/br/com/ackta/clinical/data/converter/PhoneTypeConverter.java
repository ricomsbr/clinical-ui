package br.com.ackta.clinical.data.converter;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.PhoneType;

@Converter(autoApply = true)
public class PhoneTypeConverter implements PersistableEnumConverter<PhoneType> {

	@Override
	public PhoneType convertToEntityAttribute(Long dbData) {
		return PhoneType.findById(dbData);
	}

}
