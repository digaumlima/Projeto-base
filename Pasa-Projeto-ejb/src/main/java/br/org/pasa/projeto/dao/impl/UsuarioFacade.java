package br.org.pasa.projeto.dao.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import br.org.pasa.projeto.acesso.AutenticacaoLDAP;
import br.org.pasa.projeto.acesso.UsuarioLogado;
import br.org.pasa.projeto.config.AppConfig;
import br.org.pasa.projeto.dao.JPADAO;
import br.org.pasa.projeto.dao.impl.PerfilDAOImpl;
import br.org.pasa.projeto.dao.impl.UsuarioDAOImpl;
import br.org.pasa.projeto.exception.AcessoNegadoException;
import br.org.pasa.projeto.exception.ValidacaoNegocioException;
import br.org.pasa.projeto.modelo.Perfil;
import br.org.pasa.projeto.modelo.Usuario;
import br.org.pasa.projeto.util.MessageBundle;
import br.org.pasa.projeto.utils.Util;

/**
 * Titulo: UsuarioFacade.java
 * 
 * @date 30/08/2016
 * @autor rodrigo.cordovil
 */
@Named
@RequestScoped
public class UsuarioFacade implements Serializable {
	
	private static final long serialVersionUID = 8044657045918660392L;

	private static final Logger logger = Logger.getLogger(UsuarioFacade.class);
	
	@Inject @JPADAO
	private UsuarioDAOImpl usuarioDAOImpl;
	
	@Inject @JPADAO
	private PerfilDAOImpl perfilDAOImpl;
	
	@Inject
	private UsuarioLogado usuarioLogado;
	
	@Inject
	private AppConfig appConfig;

	/**
	 * Realiza o login de acordo com o usuario e senha
	 * @param matricula
	 * @param senha
	 * @return Usuario
	 * @throws ValidacaoNegocioException
	 * @throws AcessoNegadoException
	 */
	@Transactional
	public Usuario login(String matricula, String senha) throws ValidacaoNegocioException, AcessoNegadoException {
		
		this.validaLoginSenha(matricula, senha);
		
		Usuario usuario = this.usuarioDAOImpl.buscarUsuarioPorMatricula(matricula);
		
		if(usuario == null || usuario.getPerfil().isEmpty()){
			String error = MessageBundle.getMensagemFromBundle("mensagem.erro.usuario.autenticar.matricula", matricula);
			logger.debug(error);
			throw new AcessoNegadoException(error);
		}
		
		boolean autenticado = new AutenticacaoLDAP().autenticar(this.appConfig.getConfiguracao().getCaminhoUrlAutenticacao(), matricula, senha);
		
		if(!autenticado){
			String error = MessageBundle.getMensagemFromBundle("mensagem.erro.autenticado") + matricula;
			logger.debug(error);
			throw new AcessoNegadoException(error);
		}
		
		return usuario;
	}
	
	/**
	 * Valida matricula e senha do usuario
	 * @param matricula
	 * @param senha
	 * @throws ValidacaoNegocioException
	 */
	private void validaLoginSenha(String matricula, String senha) throws ValidacaoNegocioException{
		
		ValidacaoNegocioException validacao = new ValidacaoNegocioException();
		
		if(matricula == null || matricula.trim().isEmpty()){
			validacao.addMensagem(MessageBundle.getMensagemFromBundle("msg.login.matricula.invalida"));
		}
		
		if(senha == null || senha.trim().isEmpty()){
			validacao.addMensagem(MessageBundle.getMensagemFromBundle("msg.login.senha.invalida"));
		}
		
		validacao.throwSeTiverMensagem();
	}
	
	
	/**
	 * Recupera uma listagem com todos os perfis cadastrados
	 * @return List<Perfil>
	 */
	public List<Perfil> recuperaListaPerfil() {
		return this.perfilDAOImpl.buscarTodos();
	}
	
	/**
	 * Recupera uma listagem com todos os usuarios cadastrados
	 * @return List<Usuario>
	 */
	public List<Usuario> recuperaListaCompletaUsuario() {
		return this.usuarioDAOImpl.buscarTodos();
	}

	/**
	 * Inclui um novo usuarop pela matricula
	 * @param matricula
	 * @param listaIdPerfilSelecionado
	 */
	@Transactional
	public Usuario inserirNovoUsuario(String matricula, List<String> listaIdPerfilSelecionado) throws ValidacaoNegocioException {
		
		if(Util.isStringNullOrEmpty(matricula) || Util.isCollectionNullOrEmpty(listaIdPerfilSelecionado)){
			throw new ValidacaoNegocioException(MessageBundle.getMensagemFromBundle("msg.erro.incluir.matricula.perfil.vazio"));
		}else if(this.usuarioDAOImpl.buscarUsuarioPorMatricula(matricula) != null){
			throw new ValidacaoNegocioException(MessageBundle.getMensagemFromBundle("msg.erro.incluir.matricula.existente"));
		}
		
		Usuario usuario = new Usuario();
		usuario.setMatricula(matricula);
		
		if(Util.isCollectionNullOrEmpty(usuario.getPerfil())){
			usuario.setPerfil(new HashSet<>());
		}
		
		for (String idPerfil : listaIdPerfilSelecionado) {
			usuario.getPerfil().add(new Perfil(Long.valueOf(idPerfil)));	
		}
		this.usuarioDAOImpl.inserir(usuario);
		
		return usuario;
	}
	
	/**
	 * Alterar um usuario pela matricula
	 * @param usuario
	 * @param matricula
	 * @param listaIdPerfilSelecionado
	 */
	@Transactional
	public Usuario alterarUsuario(Usuario usuario, String matricula, List<String> listaIdPerfilSelecionado) throws ValidacaoNegocioException {
		
		if(Util.isStringNullOrEmpty(matricula) || Util.isCollectionNullOrEmpty(listaIdPerfilSelecionado)){
			throw new ValidacaoNegocioException(MessageBundle.getMensagemFromBundle("msg.erro.incluir.matricula.perfil.vazio"));
		}else if(!usuario.getMatricula().equals(matricula.trim()) && this.usuarioDAOImpl.buscarUsuarioPorMatricula(matricula) != null){
			throw new ValidacaoNegocioException(MessageBundle.getMensagemFromBundle("msg.erro.incluir.matricula.existente"));
		}

		usuario.setMatricula(matricula);
		
		usuario.getPerfil().clear();
		for (String idPerfil : listaIdPerfilSelecionado) {
			usuario.getPerfil().add(new Perfil(Long.valueOf(idPerfil)));	
		}
		
		this.usuarioDAOImpl.atualizar(usuario);
		
		return usuario;
	}
	
	/**
	 * Excluir um usuario pela matricula
	 * @param matricula
	 */
	@Transactional
	public void excluirUsuario(Long idUsuario) {
		this.usuarioDAOImpl.deletar(idUsuario);
	}
	
	/**
	 * Faz o logout do usuario que esta conectado na aplicacao
	 * @throws IOException
	 */
	@Transactional
	public void logout() throws IOException{
		this.usuarioLogado.logout();
	}
	
}