package br.org.pasa.projeto.web;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import br.org.pasa.projeto.acesso.UsuarioLogado;
import br.org.pasa.projeto.exception.AcessoNegadoException;
import br.org.pasa.projeto.exception.ValidacaoNegocioException;
import br.org.pasa.projeto.facade.UsuarioFacade;

@Named
@ViewScoped
public class LoginView extends BaseView {

	private static final long serialVersionUID = -7566789403426975109L;
	
	private static final Logger logger = Logger.getLogger(LoginView.class);
	
	@Inject
	private UsuarioLogado usuarioLogado;
	
	@Inject
	private UsuarioFacade usuarioFacade; 

	private String matricula;
	private String senha;
	
	/**
	 * Caso o usuario ja esteja logado redireciona para a pagina index
	 * @throws IOException
	 */
	@PostConstruct
	private void init() {
		if(!isPostback() && this.usuarioLogado.isLogado()){
			redirectToIndex();
		}
	}
	
	/**
	 * Autentica o usuario junto o AD da PASA
	 * @throws SistemaException
	 * @throws IOException
	 */
	public void autenticar() {
		if(this.usuarioLogado.isLogado()){
			redirectToIndex();
			return;
		}
		
		try{
			this.usuarioLogado.setUsuario(this.usuarioFacade.login(matricula, senha));
			redirectToIndex();
		}catch(ValidacaoNegocioException e){
			logger.debug(e.getMessage(), e);
			MensagensView.addErrorGlobalMessages(e.getMessages());
		}catch(AcessoNegadoException ex){
			logger.debug(ex.getMessage(), ex);
			MensagensView.addErrorGlobalMessage(ex.getMessage());
		}
	}
	
	public void logout() throws IOException{
		this.usuarioFacade.logout();
		
		redirect("/login.xhtml");
	}
	
	private void redirectToIndex() {
		redirect("/view/produtos/index.xhtml");
	}
	
	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

}

