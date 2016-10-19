package br.org.projeto.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.org.projeto.dao.JPADAO;
import br.org.projeto.modelo.BaseEntity;



/**
 * Fornece operacoes predefinidas como genericas.
 * 
 * @param <T>
 *            Tipo da Entidade.
 * @param <K>
 *            Tipo da Chave Primaria.
 */
@JPADAO
public abstract class GenericDAOImpl<T extends BaseEntity, K extends Number> {

	private static final Logger logger = Logger.getLogger(GenericDAOImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
    
    private Class<T> persistentClass;
    
	@SuppressWarnings("unchecked")
    public GenericDAOImpl() {
        final Type[] typeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.persistentClass = (Class<T>) typeArguments[0];
    }

    public void inserir(final T entity) {
    	this.getEntityManager().persist(entity);	
    }

    public T atualizar(final T entity) {
        return this.getEntityManager().merge(entity);
    }

    public void deletar(final K id) {
        this.getEntityManager().remove(this.getEntityManager().getReference(this.getPersistentClass(), id));
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public T buscarPorId(final K id) {
        return this.getEntityManager().find(this.getPersistentClass(), id);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public int contar() {
        return this.getEntityManager().createQuery("SELECT COUNT(*) FROM " + this.getPersistentClass().getSimpleName(), Long.class).getSingleResult().intValue();
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public T buscaPorNome(String nomeEntidade) {
        try {
            Query q = this.getEntityManager().createQuery("select entity FROM " + this.getPersistentClass().getSimpleName() + " entity WHERE entity.nome = :nome");
            q.setParameter("nome", nomeEntidade);
            return (T) q.getSingleResult();
        } catch (final NoResultException e) {
        	logger.error("Ocorreu um erro ao buscar a entidade " + this.getPersistentClass().getSimpleName() + " pelo nome " + e.getMessage(), e);
            return null;
        }
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected Object buscarResultadoUnicoPorJPQL(final String jpql, final Object... params) {
        return this.criarQuery(jpql, params).getSingleResult();
    }
    
	/**
	 * Retorna todos com o nome em lower case
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<T> buscarTodosComNomeLower(){
		return (List<T>) this.buscarPorJPQL("SELECT new " + this.getPersistentClass().getSimpleName() + "(entity.id, LOWER(entity.nome)) FROM " + this.getPersistentClass().getSimpleName() + " entity");
	}
    
    @SuppressWarnings("unchecked")
    public List<T> buscarTodos() {
        return (List<T>) this.buscarPorJPQL("FROM " + this.getPersistentClass().getSimpleName());
    }
    
    protected List<?> buscarPorJPQL(final String jpql, final Object... params) {
        return this.criarQuery(jpql, params).getResultList();
    }
    
    protected List<?> buscarPorJPQL(final String jpql, final Map<String, Object> params) {
        return this.criarQueryParametrizada(jpql, params).getResultList();
    }
    
    protected Query criarQueryParametrizada(final String jpql, final Map<String, Object> params) {
        final Query query = this.getEntityManager().createQuery(jpql);
        final Object[] keys = params.keySet().toArray();
        for (final Object key : keys) {
            query.setParameter(key.toString(), params.get(key));
        }
        return query;
    }

    protected Class<T> getPersistentClass() {
        if (this.persistentClass == null) {
            throw new IllegalStateException("A PersistentClass não foi definida.");
        }
        return this.persistentClass;
    }

    protected void setPersistentClass(final Class<T> persistentClassParam) {
        this.persistentClass = persistentClassParam;
    }

    protected EntityManager getEntityManager() {
        if (this.entityManager == null) {
            throw new IllegalStateException("O EntityManager não foi injetado.");
        }
        return this.entityManager;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    protected Query criarQuery(final String jpql, final Object... params) {
        final Query query = this.getEntityManager().createQuery(jpql);
        int i = 0;
        for (final Object object : params) {
            query.setParameter(++i, object);
        }
        return query;
    }

}
