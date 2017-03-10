package br.com.ackta.clinical.data.converter;

import java.util.Objects;

import javax.persistence.Converter;

import br.com.ackta.clinical.data.entity.AddressType;

@Converter(autoApply = true)
public class AddressTypeConverter implements PersistableEnumConverter<AddressType> {

	@Override
	public AddressType convertToEntityAttribute(Long dbData) {
		AddressType result = null;
		if (Objects.nonNull(dbData)) {
			result = AddressType.findById(dbData);
		}
		return result;
	}

}
