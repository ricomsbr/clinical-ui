package br.com.ackta.clinical.data.converter;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.Gender;

@Converter(autoApply = true)
public class GenderConverter implements PersistableEnumConverter<Gender> {

	@Override
	public Gender convertToEntityAttribute(Long dbData) {
		return Gender.findById(dbData);
	}

}
