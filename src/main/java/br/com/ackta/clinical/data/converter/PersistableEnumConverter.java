package br.com.ackta.clinical.data.converter;

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
		return attribute.getId();
	}

}
