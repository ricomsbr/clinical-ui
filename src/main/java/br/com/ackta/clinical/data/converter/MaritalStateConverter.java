package br.com.ackta.clinical.data.converter;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.MaritalState;

@Converter(autoApply = true)
public class MaritalStateConverter implements PersistableEnumConverter<MaritalState> {

	@Override
	public MaritalState convertToEntityAttribute(Long dbData) {
		return MaritalState.findById(dbData);
	}

}
