package br.com.ackta.clinical.data.converter;

import java.time.Year;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, Integer> {

	@Override
	public Year convertToEntityAttribute(Integer dbData) {
		return Year.of(dbData);
	}

	@Override
	public Integer convertToDatabaseColumn(Year attribute) {
		return attribute.getValue();
	}
}
