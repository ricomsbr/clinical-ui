package br.com.ackta.clinical.data.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum PeriodicityUnit implements IPersistableEnum {
	DAILY(1L),
	WEEKLY(2L),
	MONTHLY(3L);

	private static final Map<Long, PeriodicityUnit> ID_TO_ENUM_MAP = new HashMap<Long, PeriodicityUnit>();

	static {
		for (final PeriodicityUnit enumElement : values()) {
			ID_TO_ENUM_MAP.put(enumElement.id, enumElement);
		}
	}

	public static PeriodicityUnit findById(Long id) {
		final PeriodicityUnit result = ID_TO_ENUM_MAP.get(id);
		Objects.requireNonNull(result, "No element found by the id.");
		return result;
	}

	private Long id;

	private PeriodicityUnit(Long id1) {
		this.id = id1;
	}

	@Override
	public Long getId() {
		return id;
	}

}