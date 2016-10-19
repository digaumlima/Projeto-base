package br.org.pasa.projeto.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import br.org.pasa.projeto.acesso.UsuarioLogado;

@Named
@RequestScoped
public class BaseView implements Serializable {

	private static final long serialVersionUID = 4035802315238943140L;
	
	private static final Logger logger = Logger.getLogger(BaseView.class);
	
	@Inject 
	private UsuarioLogado logado;
	
	protected FacesContext getContext(){
		return FacesContext.getCurrentInstance();
	}
	
	protected HttpServletRequest getRequest(){
		return (HttpServletRequest)getContext().getExternalContext().getRequest();
	}
	
	protected HttpSession getSession(boolean create){
		return (HttpSession)getContext().getExternalContext().getSession(create);
	}
	
	protected boolean isPostback(){
		return getContext().isPostback();
	}
	
	protected Map<String, Object> getFlash(){
		return getContext().getExternalContext().getSessionMap();
	}
	
	protected void redirect(String path){
		try {
			getContext().getExternalContext().redirect(
				new StringBuilder(getRequest().getContextPath()).append(path).toString()
			);
		} catch (IOException e) {
			logger.debug(e.getMessage(), e);
		}
	}
	
	protected UsuarioLogado getUsuarioLogado(){
		return this.logado;
	}
	
	protected void addToSession(String key, Object value){
		getContext().getExternalContext().getSessionMap().put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	protected<T> T getFromSession(String key){
		return (T)getContext().getExternalContext().getSessionMap().get(key);
	}
	
	@SuppressWarnings("unchecked")
	protected<T> T removeFromSession(String key){
		return (T)getContext().getExternalContext().getSessionMap().remove(key);
	}
	
	protected String getRedirect(String outcome, String params){
		StringBuilder q = new StringBuilder(outcome).append("?faces-redirect=true");
		
		if(params != null && !params.isEmpty()){
			q.append('&').append(params);
		}
		
		return q.toString();
	}
	
	protected String getParameter(String name){
		return getContext().getExternalContext().getRequestParameterMap().get(name);
	}
	
}
