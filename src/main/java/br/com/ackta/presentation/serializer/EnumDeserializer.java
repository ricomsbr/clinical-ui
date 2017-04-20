package br.com.ackta.presentation.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public abstract class EnumDeserializer<E extends Enum<E>> extends JsonDeserializer<E> {

	@Override
    public E deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final EnumTO node = parser.readValueAs(EnumTO.class);
        String result = node.getName();
        return null; //TODO faltar implementar esse método.
    }
    
//	@Override
//	public void deserialize(E value, JsonGenerator gen,
//			SerializerProvider serializers) throws IOException,
//			JsonProcessingException {
//		final EnumTO<E> node = new EnumTO<E>(value, messageSource);
//		gen.writeObject(node);
//	}

}