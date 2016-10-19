package br.org.pasa.projeto.acesso;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.org.pasa.projeto.modelo.Usuario;

@Named
@SessionScoped
public class UsuarioLogado implements Serializable {
	
	private static final long serialVersionUID = 8289565743581209291L;
	
	private Usuario usuarioLogado;

	public void setUsuario(Usuario usuario){
		if (usuario != null) {
	    	this.usuarioLogado = usuario;
       }
	}
	
	public Usuario getUsuarioLogado(){
		return this.usuarioLogado;
	}
	
	public void logout() throws IOException {
		this.usuarioLogado = null;
	}
	
	public boolean isLogado(){
		return this.usuarioLogado != null;
	}
	
	public String getMatricula(){
		return this.usuarioLogado != null ? this.usuarioLogado.getMatricula() : null;
	}

}
