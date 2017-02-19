/*
 * IPersistable.java		28/09/2015
 *
 * Copyright (C) 2016 ACKTA. All Rights Reserved.
 */
package br.com.ackta.clinical.model.entity;

import java.io.Serializable;

/**
 *
 *
 * @author RMendonca
 * @version @version@
 * @since @since@
 */
public interface IPersistable extends Serializable {

	Long getId();

	Long getVersion();

	boolean isActive();

	void setId(Long id);

	void setVersion(Long version);

}
