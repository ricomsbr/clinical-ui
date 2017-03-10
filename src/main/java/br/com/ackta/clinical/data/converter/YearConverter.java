package br.com.ackta.clinical.data.converter;

import java.time.Year;
import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, Integer> {

	@Override
	public Year convertToEntityAttribute(Integer dbData) {
		Year result = null;
		if (Objects.nonNull(dbData)) {
			result = Year.of(dbData);
		}
		return result;
	}

	@Override
	public Integer convertToDatabaseColumn(Year attribute) {
		Integer result = null;
		if (Objects.nonNull(attribute)) {
			result = attribute.getValue();
		}
		return result;
	}
}
