package br.com.ackta.clinical.presentation.serializer;

import java.time.temporal.ChronoUnit;

import br.com.ackta.clinical.SerializableResourceBundleMessageSource;
import br.com.ackta.presentation.serializer.EnumSerializer;

public class ChronoUnitSerializer extends EnumSerializer<ChronoUnit> {

	public ChronoUnitSerializer(SerializableResourceBundleMessageSource messageSource) {
		super(messageSource);
	}

}
