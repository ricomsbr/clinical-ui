package br.com.ackta.clinical.data.converter;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ChronoUnitConverter implements AttributeConverter<ChronoUnit, String> {

	@Override
	public String convertToDatabaseColumn(ChronoUnit attribute) {
		String result = null;
		if (Objects.nonNull(attribute)) {
			result = attribute.toString();
		}
		return result;
	}

	@Override
	public ChronoUnit convertToEntityAttribute(String dbData) {
		ChronoUnit result = null;
		if (Objects.nonNull(dbData)) {
			ChronoUnit[] units = ChronoUnit.values();
			for (ChronoUnit chronoUnit : units) {
				if (chronoUnit.toString().equals(dbData)) {
					result = chronoUnit;
				}
			}
			throw new NullPointerException("No element found by the id.");
		}
		return result;
	}
}
