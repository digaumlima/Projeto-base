package br.org.projeto.utils;

import java.io.Serializable;

/**
 *
 */
public class MensagemValidacao implements Serializable{

	private static final long serialVersionUID = 3180414337938268179L;
	
	private String mensagem;
	private String[] parametros;
	
	public MensagemValidacao(String mensagem, String...parametros) {
		this.mensagem = mensagem;
		this.parametros = parametros;
	}

	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @return the parametros
	 */
	public String[] getParametros() {
		return parametros;
	}
}
