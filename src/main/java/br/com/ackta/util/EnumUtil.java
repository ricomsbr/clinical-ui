package br.com.ackta.util;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class EnumUtil {

	private static final String DOT = ".";

	public static String getKey(Enum<?> enum1) {
		return getKeyPrefix(enum1) + DOT + getKeySufix(enum1); 
	}

	public static String getKeySufix(Enum<?> enum1) {
		return enum1.name().toLowerCase();
	}

	public static String getKeyPrefix(Enum<?> enum1) {
		return enum1.getClass().getSimpleName();
	}

	public static String getMessage(Enum<?> enum1, ReloadableResourceBundleMessageSource messageSource, Locale locale) {
		String key = getKey(enum1);
		return messageSource.getMessage(key, null, key, locale);
	}
}
