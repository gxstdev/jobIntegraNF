package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class GenericDAO<T> {
    protected final Class<T> clazz;
    protected final EntityManager em;

    public GenericDAO(Class<T> clazz, EntityManager em) {
        this.clazz = clazz;
        this.em = em;
    }

    public void salvar(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public T buscarPorId(Object id) {
        return em.find(clazz, id);
    }

    public List<T> buscarTodos() {
        String sql = "SELECT * FROM TB_NF";
        return em.createNativeQuery(sql, this.clazz).getResultList();
    }

    public void atualizar(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deletar(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public EntityManager getEm() {
        return em;
    }
}
