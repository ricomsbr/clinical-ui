package br.com.ackta.clinical.data.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum PhoneType implements IPersistableEnum {
	HOME(1L, "home"),
	MOBILE(2L, "mobile"),
	GENERAL(3L, "general");

	private static final Map<Long, PhoneType> ID_TO_ENUM_MAP = new HashMap<Long, PhoneType>();

	static {
		for (final PhoneType enumElement : values()) {
			ID_TO_ENUM_MAP.put(enumElement.id, enumElement);
		}
	}

	public static PhoneType findById(Long id) {
		final PhoneType result = ID_TO_ENUM_MAP.get(id);
		Objects.requireNonNull(result, "No element found by the id.");
		return result;
	}

	private Long id;
	private String key;

	private PhoneType(Long id1, String key1) {
		this.id = id1;
		this.key = key1;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String  getKey() {
		return key;
	}
}
