package br.com.ackta.clinical.data.converter;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.AddressType;

@Converter(autoApply = true)
public class AddressTypeConverter implements PersistableEnumConverter<AddressType> {

	@Override
	public AddressType convertToEntityAttribute(Long dbData) {
		return AddressType.findById(dbData);
	}

}
