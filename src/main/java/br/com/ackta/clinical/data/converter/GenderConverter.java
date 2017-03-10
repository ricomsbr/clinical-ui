package br.com.ackta.clinical.data.converter;

import java.util.Objects;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.Gender;

@Converter(autoApply = true)
public class GenderConverter implements PersistableEnumConverter<Gender> {

	@Override
	public Gender convertToEntityAttribute(Long dbData) {
		Gender result = null;
		if (Objects.nonNull(dbData)) {
			result = Gender.findById(dbData);
		}
		return result;
	}

}
