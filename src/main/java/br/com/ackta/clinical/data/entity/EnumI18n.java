/*
 * EnumI18n.java		28/07/2015
 *
 * Copyright (C) 2016 ACKTA. All Rights Reserved.
 */
package br.com.ackta.clinical.data.entity;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
public interface EnumI18n<E extends Enum<E>> {
	public static final String SEPARATOR = ".";

	default public String getKey(E enumVar) {
		return getKeyPrefix(enumVar) + SEPARATOR + getKeySufix(enumVar);
	}

	default public String getKeyPrefix(E enumVar) {
		final String simpleName = enumVar.getClass().getSimpleName();
		return (simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1));
	}

	default public String getKeySufix(E enumVar) {
		return enumVar.name().toLowerCase();
	}
}
