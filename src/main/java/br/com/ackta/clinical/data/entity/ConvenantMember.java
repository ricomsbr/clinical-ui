package br.com.ackta.clinical.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConvenantMember implements IConvenantMember {

	@Column(name = "card_number", nullable = true)
	private String cardNumber;

	@Column(name = "convenant", nullable = false)
	private Convenant convenant;

	public ConvenantMember() {
		super();
	}

	public ConvenantMember(Convenant convenant1) {
		super();
		this.convenant = convenant1;
	}

	@Override
	public String getCardNumber() {
		return cardNumber;
	}

	@Override
	public Convenant getConvenant() {
		return convenant;
	}

	@Override
	public void setCardNumber(String cardNumber1) {
		cardNumber = cardNumber1;
	}

}
