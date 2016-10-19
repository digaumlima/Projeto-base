package br.org.projeto.dao.impl;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.org.projeto.dao.JPADAO;
import br.org.projeto.modelo.Configuracao;

/**
 * Titulo: ConfiguracaoDAOImpl.java
 * @date 23/09/2016
 * @autor rodrigo.cordovil
 * 
 */
@JPADAO
public class ConfiguracaoDAOImpl extends GenericDAOImpl<Configuracao, Long> {

	
	private static final Logger logger = Logger.getLogger(ConfiguracaoDAOImpl.class);
	
	/**
	 * Buscar as propriedades de configuracao da aplicacao
	 * @return Configuracao
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Configuracao buscarConfiguracaoSistema() {
	    try {
			Query query = this.getEntityManager().createQuery("SELECT entity FROM Configuracao entity");
			return (Configuracao) query.setMaxResults(1).getSingleResult();
	    } catch (final NoResultException e) {
	    	logger.error("Ocorreu um erro ao buscar as configuracaoes do sistema na base de dados. " + e.getMessage(), e);
	        throw new NoResultException("Ocorreu um erro ao buscar as configuracaoes do sistema na base de dados. " + e.getMessage());
	    }
    }
}
