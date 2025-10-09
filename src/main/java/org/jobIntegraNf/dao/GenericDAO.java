package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * DAO genérico com operações CRUD básicas usadas pelas entidades do sistema.
 * @param <T> tipo da entidade.
 */
public class GenericDAO<T> {
    protected final EntityManager em;
    protected final Class<T> clazz;

    /**
     * @param clazz tipo da entidade.
     * @param em    entity manager a ser utilizado.
     */
    public GenericDAO(Class<T> clazz, EntityManager em) {
        this.em = em;
        this.clazz = clazz;
    }

    /**
     * Persiste a entidade no contexto JPA.
     * @param entity entidade para persistência.
     */
    public void salvar(T entity) {
        this.em.persist(entity);
    }

    /**
     * Busca uma entidade pelo identificador.
     * @param id identificador.
     * @return entidade encontrada ou null.
     */
    public T buscarPorId(Object id) {
        return this.em.find(clazz, id);

    }

    @SuppressWarnings("unchecked")
    /**
     * Retorna todos os registros da tabela associada.
     * Nota: implementação genérica com SQL simples; ajustar conforme necessidade.
     * @return lista de entidades.
     */
    public List<T> buscarTodos() {
        String sql = "SELECT * FROM TB_NF";
        return this.em.createNativeQuery(sql, this.clazz).getResultList();
    }

    /**
     * Atualiza a entidade no banco de dados.
     * @param entity entidade a atualizar.
     */
    public void atualizar(T entity) {
        this.em.merge(entity);
    }

    /**
     * Remove a entidade do banco de dados.
     * @param entity entidade a remover.
     */
    public void deletar(T entity) {
        this.em.remove(this.em.contains(entity) ? entity : this.em.merge(entity));
    }
}
