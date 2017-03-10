package br.com.ackta.clinical.data.converter;

import java.util.Objects;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.Kinship;

@Converter(autoApply = true)
public class KinshipConverter implements PersistableEnumConverter<Kinship> {

	@Override
	public Kinship convertToEntityAttribute(Long dbData) {
		Kinship result = null;
		if (Objects.nonNull(dbData)) {
			result = Kinship.findById(dbData);
		}
		return result;
	}

}
