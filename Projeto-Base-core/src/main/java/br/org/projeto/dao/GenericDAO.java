package br.org.projeto.dao;

import java.util.List;

import br.org.projeto.modelo.BaseEntity;

/**
 * Interface generica para as interfaces de servico.
 * 
 * @param <T>
 *            Tipo da Entidade.
 * @param <K>
 *            Tipo da Chave Primaria.
 */
public interface GenericDAO<T extends BaseEntity, K extends Number> {

	/**
	 * inserir
	 * @param entidade
	 */
    void inserir(T entidade);

    /**
     * atualizar
     * @param entidade
     * @return
     */
    T atualizar(T entidade);

    /**
     * deletar
     * @param id
     */
    void deletar(K id);

    /**
     * buscarPorId
     * @param id
     * @return
     */
    T buscarPorId(K id);
    
    /**
     * buscarTodos
     * @return
     */
    List<T> buscarTodos();
    
    /**
     * buscaPorNome
     * @param nomeEntidade
     * @return
     */
    T buscaPorNome(String nomeEntidade);

    /**
     * contar
     * @return
     */
    int contar();
    
    /**
     * retorna todos os resultados de entidades que tem o atributo nome
     * @return
     */
    List<T> buscarTodosComNomeLower();

}