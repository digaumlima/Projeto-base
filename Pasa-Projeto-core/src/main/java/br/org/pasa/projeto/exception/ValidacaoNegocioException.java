package br.org.pasa.projeto.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import br.org.pasa.projeto.utils.MensagemValidacao;

public class ValidacaoNegocioException extends Exception {

	private static final long serialVersionUID = 7600809144554564549L;
	private final Map<String, MensagemValidacao> mensagens = new LinkedHashMap <>();

	/**
	 * @param message
	 */
	public ValidacaoNegocioException() {
	}
	
	/**
	 * 
	 * @param mensagem
	 */
	public ValidacaoNegocioException(MensagemValidacao mensagem) {
		addMensagem(mensagem);
	}
	
	public ValidacaoNegocioException(String mensagem, String...parametros) {
		addMensagem(mensagem,parametros);
	}
	
	public void addMensagem(MensagemValidacao mensagem){
		mensagens.put(mensagem.getMensagem(), mensagem);
	}
	
	public void addMensagem(String mensagem, String...parametros){
		addMensagem(new MensagemValidacao(mensagem,parametros));
	}
	
	public boolean contains(String msg){
		return this.mensagens.containsKey(msg);
	}
	
	/**
	 * 
	 * @throws ValidacaoNegocioException
	 */
	public void throwSeTiverMensagem()throws ValidacaoNegocioException{
		if(this.mensagens.size() > 0){
			throw this;
		}
	}
	
	public List<MensagemValidacao> getMessages(){
		return new ArrayList<>(this.mensagens.values());
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		if(this.mensagens.isEmpty()){
			return StringUtils.EMPTY;
		}
		
		return StringUtils.join(this.mensagens, ", ");
	}
	
	public String getSingleMessage() {
		if(this.mensagens.isEmpty()){
			return "";
		}
		Entry<String, MensagemValidacao> entry= this.mensagens.entrySet().iterator().next();
		return entry.getKey();
	}
}
