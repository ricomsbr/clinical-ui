package br.com.ackta.clinical.data.converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;

import br.com.ackta.clinical.data.entity.IPersistableEnum;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
public interface PersistableEnumConverter<E extends IPersistableEnum> extends AttributeConverter<E, Long> {
	@Override
	default public Long convertToDatabaseColumn(E attribute) {
		if (Objects.isNull(attribute)) {
			return null;
		}
		return attribute.getId();
	}

}
