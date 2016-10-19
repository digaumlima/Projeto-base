package br.org.projeto.facade.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import br.org.projeto.acesso.AutenticacaoLDAP;
import br.org.projeto.acesso.UsuarioLogado;
import br.org.projeto.config.AppConfig;
import br.org.projeto.dao.JPADAO;
import br.org.projeto.dao.impl.PerfilDAOImpl;
import br.org.projeto.dao.impl.UsuarioDAOImpl;
import br.org.projeto.exception.AcessoNegadoException;
import br.org.projeto.exception.ValidacaoNegocioException;
import br.org.projeto.facade.UsuarioFacade;
import br.org.projeto.modelo.Perfil;
import br.org.projeto.modelo.Usuario;
import br.org.projeto.util.MessageBundle;
import br.org.projeto.utils.Util;

/**
 * Titulo: UsuarioFacade.java
 * 
 * @date 30/08/2016
 * @autor rodrigo.cordovil
 */
@Named
@RequestScoped
public class UsuarioFacadeImpl implements UsuarioFacade, Serializable {
	
	private static final long serialVersionUID = 8044657045918660392L;

	private static final Logger logger = Logger.getLogger(UsuarioFacadeImpl.class);
	
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
	@Override
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
	@Override
	public List<Perfil> recuperaListaPerfil() {
		return this.perfilDAOImpl.buscarTodos();
	}
	
	/**
	 * Recupera uma listagem com todos os usuarios cadastrados
	 * @return List<Usuario>
	 */
	@Override
	public List<Usuario> recuperaListaCompletaUsuario() {
		return this.usuarioDAOImpl.buscarTodos();
	}

	/**
	 * Inclui um novo usuarop pela matricula
	 * @param matricula
	 * @param listaIdPerfilSelecionado
	 */
	@Override
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
	@Override
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
	@Override
	@Transactional
	public void excluirUsuario(Long idUsuario) {
		this.usuarioDAOImpl.deletar(idUsuario);
	}
	
	/**
	 * Faz o logout do usuario que esta conectado na aplicacao
	 * @throws IOException
	 */
	@Override
	@Transactional
	public void logout() throws IOException{
		this.usuarioLogado.logout();
	}
	
}