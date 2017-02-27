package br.com.ackta.clinical.data.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum AddressType implements IPersistableEnum {
	HOME(1L, "home"),
	COMMERCIAL(2L, "commercial");

	private static final Map<Long, AddressType> ID_TO_ENUM_MAP = new HashMap<Long, AddressType>();

	static {
		for (final AddressType enumElement : values()) {
			ID_TO_ENUM_MAP.put(enumElement.id, enumElement);
		}
	}

	public static AddressType findById(Long id) {
		final AddressType result = ID_TO_ENUM_MAP.get(id);
		Objects.requireNonNull(result, "No element found by the id.");
		return result;
	}

	private Long id;
	private String key;

	private AddressType(Long id1, String key1) {
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
