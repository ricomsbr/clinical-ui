package br.com.ackta.clinical.model.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum MaritalState implements IPersistableEnum {
	SINGLE(1L), MARRIED(2L), WIDOWER(3L), DIVORCED(4L);

	private Long id;

	private static final Map<Long, MaritalState> ID_TO_ENUM_MAP = new HashMap<Long, MaritalState>();

	static {
		for (final MaritalState enumElement : values()) {
			ID_TO_ENUM_MAP.put(enumElement.id, enumElement);
		}
	}

	private MaritalState(Long id1) {
		this.id = id1;
	}

	@Override
	public Long getId() {
		return id;
	}

	public static MaritalState findById(Long id) {
		final MaritalState result = ID_TO_ENUM_MAP.get(id);
		Objects.requireNonNull(result, "No element found by the id.");
		return result;
	}

}