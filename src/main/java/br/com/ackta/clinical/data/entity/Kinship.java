package br.com.ackta.clinical.data.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Kinship implements IPersistableEnum {
	MOTHER(1L),
	FATHER(2L),
	UNCLE(3L),
	AUNT(4L),
	BROTHER(5L),
	SISTER(6L),
	GRANDMOTHER(7L),
	GRANDFATHER(8L),
	SON(9L),
	DAUTHER(10L),
	GRANDSON(11L),
	GRANDDAUTHER(12L);

	private static final Map<Long, Kinship> ID_TO_ENUM_MAP = new HashMap<Long, Kinship>();

	static {
		for (final Kinship enumElement : values()) {
			ID_TO_ENUM_MAP.put(enumElement.id, enumElement);
		}
	}

	public static Kinship findById(Long id) {
		final Kinship result = ID_TO_ENUM_MAP.get(id);
		Objects.requireNonNull(result, "No element found by the id.");
		return result;
	}

	private Long id;

	private Kinship(Long id1) {
		this.id = id1;
	}

	@Override
	public Long getId() {
		return id;
	}
}
