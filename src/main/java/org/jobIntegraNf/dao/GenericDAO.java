package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import java.util.List;

public class GenericDAO<T> {
    protected final EntityManager em;
    protected final Class<T> clazz;

    public GenericDAO(Class<T> clazz, EntityManager em) {
        this.em = em;
        this.clazz = clazz;
    }

    public void salvar(T entity) {
        this.em.persist(entity);
    }

    public T buscarPorId(Object id) {
        return this.em.find(clazz, id);

    }

    @SuppressWarnings("unchecked")
    public List<T> buscarTodos() {
        String sql = "SELECT * FROM TB_NF";
        return this.em.createNativeQuery(sql, this.clazz).getResultList();
    }

    public void atualizar(T entity) {
        this.em.merge(entity);
    }

    public void deletar(T entity) {
        this.em.remove(this.em.contains(entity) ? entity : this.em.merge(entity));
    }
}
