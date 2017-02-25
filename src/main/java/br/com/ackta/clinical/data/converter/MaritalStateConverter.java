package br.com.ackta.clinical.data.converter;

import br.com.ackta.clinical.data.entity.MaritalState;

public class MaritalStateConverter implements PersistableEnumConverter<MaritalState> {

	@Override
	public MaritalState convertToEntityAttribute(Long dbData) {
		return MaritalState.findById(dbData);
	}

}
