package br.com.ackta.presentation.serializer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.ackta.clinical.SerializableResourceBundleMessageSource;

public abstract class EnumSerializer<E extends Enum<E>> extends JsonSerializer<E> {

	@Autowired
	SerializableResourceBundleMessageSource messageSource;
	
	@Override
	public void serialize(E value, JsonGenerator gen,
			SerializerProvider serializers) throws IOException,
			JsonProcessingException {
		final EnumTO<E> node = new EnumTO<E>(value, messageSource);
		gen.writeObject(node);
	}

}