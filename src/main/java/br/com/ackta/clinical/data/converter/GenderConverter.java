package br.com.ackta.clinical.data.converter;

import br.com.ackta.clinical.data.entity.Gender;

public class GenderConverter implements PersistableEnumConverter<Gender> {

	@Override
	public Gender convertToEntityAttribute(Long dbData) {
		return Gender.findById(dbData);
	}

}
