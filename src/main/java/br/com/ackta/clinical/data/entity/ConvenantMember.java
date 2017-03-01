package br.com.ackta.clinical.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConvenantMember implements IConvenantMember {

	@Column(name = "card_number", nullable = true)
	private String cardNumber;

	@Column(name = "convenant_key", nullable = false)
	private String convenantKey;

	public ConvenantMember() {
		super();
	}

	@Override
	public String getCardNumber() {
		return cardNumber;
	}

	@Override
	public String getConvenantKey() {
		return convenantKey;
	}

}
