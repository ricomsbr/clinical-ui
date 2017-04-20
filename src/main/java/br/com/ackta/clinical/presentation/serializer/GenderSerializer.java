package br.com.ackta.clinical.presentation.serializer;

import br.com.ackta.clinical.SerializableResourceBundleMessageSource;
import br.com.ackta.clinical.data.entity.Gender;
import br.com.ackta.presentation.serializer.EnumSerializer;

public class GenderSerializer extends EnumSerializer<Gender> {

	public GenderSerializer(SerializableResourceBundleMessageSource messageSource) {
		super(messageSource);
	}

}
