package br.com.ackta.clinical.data.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Convenant implements IPersistableEnum{
	OWN(1L),
	SUS(2L);

	private static final Map<Long, Convenant> ID_TO_ENUM_MAP = new HashMap<Long, Convenant>();

	static {
		for (final Convenant enumElement: values()) {
			ID_TO_ENUM_MAP.put(enumElement.id, enumElement);
		}
	}

	public static Convenant findById(Long id) {
		final Convenant result = ID_TO_ENUM_MAP.get(id);
		Objects.requireNonNull(result, "No element found by the id.");
		return result;
	}

	private Long id;

	private Convenant(Long id1) {
		this.id = id1;
	}

	@Override
	public Long getId() {
		return id;
	}
}
