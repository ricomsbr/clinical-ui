package br.com.ackta.util;

public class EnumUtil {

	private static final String DOT = ".";

	public static String getKey(Enum<?> enum1) {
		return getKeyPrefix(enum1) + DOT + getKeySufix(enum1); 
	}

	public static String getKeySufix(Enum<?> enum1) {
		return enum1.name().toLowerCase();
	}

	public static String getKeyPrefix(Enum<?> enum1) {
		return enum1.getClass().getSimpleName().toLowerCase();
	}

}
