package br.com.ackta.clinical.data.converter;

import java.util.Objects;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.PeriodicityUnit;

@Converter(autoApply = true)
public class PeriodicityUnitConverter implements PersistableEnumConverter<PeriodicityUnit> {

	@Override
	public PeriodicityUnit convertToEntityAttribute(Long dbData) {
		PeriodicityUnit result = null;
		if (Objects.nonNull(dbData)) {
			result = PeriodicityUnit.findById(dbData);
		}
		return result;
	}
}
