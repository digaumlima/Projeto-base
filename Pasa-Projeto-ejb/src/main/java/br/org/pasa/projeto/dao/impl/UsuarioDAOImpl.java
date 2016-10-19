package br.org.pasa.projeto.dao.impl;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.org.pasa.projeto.dao.JPADAO;
import br.org.pasa.projeto.modelo.Usuario;

@JPADAO
public class UsuarioDAOImpl extends GenericDAOImpl<Usuario, Long> {
	
	private static final Logger logger = Logger.getLogger(UsuarioDAOImpl.class);
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Usuario buscarUsuarioPorMatricula(String matricula) {
        Query q = this.getEntityManager().createQuery("select entity FROM Usuario entity WHERE entity.matricula = :matricula");
        q.setParameter("matricula", matricula);
        try{
        	return (Usuario) q.getSingleResult();
	    } catch (final NoResultException e) {
	    	logger.error("Ocorreu um erro ao buscar um usuario pela matricula" + e.getMessage(), e);
	        return null;
	    }

    }
}
