package br.com.ackta.clinical.model.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Gender implements IPersistableEnum {
	MALE(1L),
	FEMALE(2L),
	UNDEFINED(3L);
	
	private Long id;
	
	private static final Map<Long, Gender> ID_TO_ENUM_MAP = new HashMap<Long, Gender>();
	
	static {
		for (final Gender enumElement: values()) {
			ID_TO_ENUM_MAP.put(enumElement.id, enumElement);
		}
	}
	
	private Gender(Long id1) {
		this.id = id1;
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public static Gender findById(Long id) {
		final Gender result = ID_TO_ENUM_MAP.get(id);
		Objects.requireNonNull(result, "No element found by the id.");
		return result;
	}

}
