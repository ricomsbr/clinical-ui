package br.com.ackta.util;

public class EnumUtil {

	private static final String DOT = ".";

	public static String getKey(Enum<?> enum1) {
		return enum1.getClass().getSimpleName().toLowerCase() + DOT + enum1.name().toLowerCase(); 
	}

}
