package br.com.ackta.clinical.business;

public class Form {
	private String cnpj;
	private String nfe;
	private String emitente;
	private Boolean boleto;
	private Boolean processado;

	/**
	 *
	 */
	public Form() {
		super();
	}

	/**
	 * @param cnpj
	 * @param nfe
	 * @param emitente
	 */
	public Form(String cnpj, String nfe, String emitente) {
		super();
		this.cnpj = cnpj;
		this.nfe = nfe;
		this.emitente = emitente;
	}

	/**
	 * @return the boleto
	 */
	public Boolean getBoleto() {
		return boleto;
	}

	/**
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}

	/**
	 * @return the emitente
	 */
	public String getEmitente() {
		return emitente;
	}

	/**
	 * @return the nfe
	 */
	public String getNfe() {
		return nfe;
	}

	/**
	 * @return the processado
	 */
	public Boolean getProcessado() {
		return processado;
	}

	/**
	 * @param boleto
	 *            the boleto to set
	 */
	public void setBoleto(Boolean boleto) {
		this.boleto = boleto;
	}

	/**
	 * @param cnpj
	 *            the cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * @param emitente
	 *            the emitente to set
	 */
	public void setEmitente(String emitente) {
		this.emitente = emitente;
	}

	/**
	 * @param nfe
	 *            the nfe to set
	 */
	public void setNfe(String nfe) {
		this.nfe = nfe;
	}

	/**
	 * @param processado
	 *            the processado to set
	 */
	public void setProcessado(Boolean processado) {
		this.processado = processado;
	}

}
