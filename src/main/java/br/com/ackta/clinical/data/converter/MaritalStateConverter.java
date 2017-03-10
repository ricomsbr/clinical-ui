package br.com.ackta.clinical.data.converter;

import java.util.Objects;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.MaritalState;

@Converter(autoApply = true)
public class MaritalStateConverter implements PersistableEnumConverter<MaritalState> {

	@Override
	public MaritalState convertToEntityAttribute(Long dbData) {
		MaritalState result = null;
		if (Objects.nonNull(dbData)) {
			result = MaritalState.findById(dbData);
		}
		return result;
	}

}
