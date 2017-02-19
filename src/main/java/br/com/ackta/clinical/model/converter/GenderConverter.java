package br.com.ackta.clinical.model.converter;

import br.com.ackta.clinical.model.entity.Gender;

public class GenderConverter implements PersistableEnumConverter<Gender> {

	@Override
	public Gender convertToEntityAttribute(Long dbData) {
		return Gender.findById(dbData);
	}

}
