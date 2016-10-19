package br.org.pasa.projeto.facade;

import java.io.IOException;
import java.util.List;

import br.org.pasa.projeto.exception.AcessoNegadoException;
import br.org.pasa.projeto.exception.ValidacaoNegocioException;
import br.org.pasa.projeto.modelo.Perfil;
import br.org.pasa.projeto.modelo.Usuario;

/**
 * Titulo: UsuarioFacade.java
 * 
 * @date 30/08/2016
 * @autor rodrigo.cordovil
 */
public interface UsuarioFacade {
	
	Usuario login(String matricula, String senha) throws ValidacaoNegocioException, AcessoNegadoException;
	
	public List<Perfil> recuperaListaPerfil();
	
	public List<Usuario> recuperaListaCompletaUsuario();

	public Usuario inserirNovoUsuario(String matricula, List<String> listaIdPerfilSelecionado) throws ValidacaoNegocioException;

	public Usuario alterarUsuario(Usuario usuario, String matricula, List<String> listaIdPerfilSelecionado) throws ValidacaoNegocioException;	

	public void excluirUsuario(Long idUsuario);
	
	public void logout() throws IOException;
	
}