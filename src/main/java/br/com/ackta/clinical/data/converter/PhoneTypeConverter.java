package br.com.ackta.clinical.data.converter;

import java.util.Objects;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.PhoneType;

@Converter(autoApply = true)
public class PhoneTypeConverter implements PersistableEnumConverter<PhoneType> {

	@Override
	public PhoneType convertToEntityAttribute(Long dbData) {
		PhoneType result = null;
		if (Objects.nonNull(dbData)) {
			result = PhoneType.findById(dbData);
		}
		return result;
	}

}
