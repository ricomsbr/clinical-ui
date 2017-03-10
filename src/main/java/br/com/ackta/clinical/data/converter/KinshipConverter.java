package br.com.ackta.clinical.data.converter;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.Kinship;

@Converter(autoApply = true)
public class KinshipConverter implements PersistableEnumConverter<Kinship> {

	@Override
	public Kinship convertToEntityAttribute(Long dbData) {
		return Kinship.findById(dbData);
	}

}
