package br.com.ackta.clinical.data.converter;

import java.time.temporal.ChronoUnit;

import javax.persistence.AttributeConverter;

public class ChronoUnitConverter implements AttributeConverter<ChronoUnit, String> {

	@Override
	public String convertToDatabaseColumn(ChronoUnit attribute) {
		return attribute.toString();
	}

	@Override
	public ChronoUnit convertToEntityAttribute(String dbData) {
		ChronoUnit[] units = ChronoUnit.values();
		for (ChronoUnit chronoUnit : units) {
			if (chronoUnit.toString().equals(dbData)) {
				return chronoUnit;
			}
		}
		throw new NullPointerException("No element found by the id.");
	}
}
