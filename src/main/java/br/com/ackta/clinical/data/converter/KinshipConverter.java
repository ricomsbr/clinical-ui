package br.com.ackta.clinical.data.converter;

import br.com.ackta.clinical.data.entity.Kinship;

public class KinshipConverter implements PersistableEnumConverter<Kinship> {

	@Override
	public Kinship convertToEntityAttribute(Long dbData) {
		return Kinship.findById(dbData);
	}

}
