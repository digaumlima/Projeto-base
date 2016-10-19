package br.org.projeto.controller.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import br.org.projeto.exception.ValidacaoNegocioException;
import br.org.projeto.facade.UsuarioFacade;
import br.org.projeto.modelo.Perfil;
import br.org.projeto.modelo.Usuario;
import br.org.projeto.utils.Util;
import br.org.projeto.web.BaseView;
import br.org.projeto.web.MensagensView;

@Named
@ViewScoped
public class UsuariosView extends BaseView {
	
	private static final long serialVersionUID = 6921584544869754340L;
	
	private static final Logger logger = Logger.getLogger(UsuariosView.class);
	
	@Inject
	private UsuarioFacade usuarioFacade;
	
	private List<Perfil> listaPerfil;
	
	private List<String> listaIdPerfilSelecionado = new ArrayList<>();
	
	private List<Usuario> listaUsuarios = new ArrayList<>();
	
	private Usuario usuarioSelecionado;
	
	private String matricula;
	
	private static final String MENSAGEM_ERROR_INCLUIR_USUARIO = "mensagem.error.incluir.usuario";
	
	@PostConstruct
	private void init(){
		if (Util.isCollectionNullOrEmpty(this.listaPerfil)) {
			this.listaPerfil = this.usuarioFacade.recuperaListaPerfil();
		}
		if (Util.isCollectionNullOrEmpty(this.listaUsuarios)) {
			this.listaUsuarios = this.usuarioFacade.recuperaListaCompletaUsuario();
		}		
		
	}
	
	public void alterarUsuario() {
		try {
			this.usuarioFacade.alterarUsuario(usuarioSelecionado, this.matricula, this.listaIdPerfilSelecionado);
			MensagensView.addInfoGlobalMessage(MensagensView.getMensagemFromBundle("mensagem.sucesso.alterar.usuario"));
			this.listaUsuarios = this.usuarioFacade.recuperaListaCompletaUsuario();
			this.resetaAtributos();
		} catch (ValidacaoNegocioException ve) {
			logger.debug(ve.getSingleMessage(), ve);
			MensagensView.addErrorGlobalMessage(ve.getSingleMessage());
		} catch (Exception e) {
			logger.debug(MensagensView.getMensagemFromBundle(MENSAGEM_ERROR_INCLUIR_USUARIO), e);
			MensagensView.addErrorGlobalMessage(MensagensView.getMensagemFromBundle(MENSAGEM_ERROR_INCLUIR_USUARIO));
		}
	}
	
	public void editarUsuario(Usuario usuario) {
		this.usuarioSelecionado = usuario;
		this.matricula = usuario.getMatricula();
		for (Perfil perfil : usuario.getPerfilList()) {
			this.getListaIdPerfilSelecionado().add(String.valueOf(perfil.getId()));	
		}
	}
	
	public void excluirUsuario(Long idUsuario) {
		try {
			this.usuarioFacade.excluirUsuario(idUsuario);		
			MensagensView.addInfoGlobalMessage(MensagensView.getMensagemFromBundle("mensagem.sucesso.excluir.usuario"));
			this.listaUsuarios = this.usuarioFacade.recuperaListaCompletaUsuario();
		} catch (Exception e) {
			logger.debug(MensagensView.getMensagemFromBundle("mensagem.error.excluir.usuario"), e);
			MensagensView.addErrorGlobalMessage(MensagensView.getMensagemFromBundle("mensagem.error.excluir.usuario"));
		}
	}
	
	/**
	 * Inclui um novo usuario pela matricula
	 */
	public void incluirUsuario() {
		try {
			this.usuarioFacade.inserirNovoUsuario(this.matricula, this.listaIdPerfilSelecionado);		
			MensagensView.addInfoGlobalMessage(MensagensView.getMensagemFromBundle("mensagem.sucesso.incluir.usuario"));
			this.listaUsuarios = this.usuarioFacade.recuperaListaCompletaUsuario();
			this.resetaAtributos();
		} catch (ValidacaoNegocioException ve) {
			logger.debug(ve.getSingleMessage(), ve);
			MensagensView.addErrorGlobalMessage(ve.getSingleMessage());
		} catch (Exception e) {
			logger.debug(MensagensView.getMensagemFromBundle(MENSAGEM_ERROR_INCLUIR_USUARIO), e);
			MensagensView.addErrorGlobalMessage(MensagensView.getMensagemFromBundle(MENSAGEM_ERROR_INCLUIR_USUARIO));
		}
	}
	
	private void resetaAtributos(){
		this.matricula = null;
		this.listaIdPerfilSelecionado.clear();
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public List<Perfil> getListaPerfil() {
		return listaPerfil;
	}

	public void setListaPerfil(List<Perfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	public List<String> getListaIdPerfilSelecionado() {
		return listaIdPerfilSelecionado;
	}

	public void setListaIdPerfilSelecionado(List<String> listaIdPerfilSelecionado) {
		this.listaIdPerfilSelecionado = listaIdPerfilSelecionado;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
