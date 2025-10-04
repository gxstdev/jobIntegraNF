package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.jobIntegraNf.exception.ErroAcessoDadosException;
import org.jobIntegraNf.util.JPAUtil;

import java.util.List;

public class GenericDAO<T> {
    protected final Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void salvar(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = JPAUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new ErroAcessoDadosException("Erro ao salvar lista de Entidades." + e.getMessage());
        }
    }

    public T buscarPorId(Object id) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.find(clazz, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroAcessoDadosException("Erro ao buscar Entidade por id." + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> buscarTodos() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            String sql = "SELECT * FROM TB_NF";
            return em.createNativeQuery(sql, this.clazz).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroAcessoDadosException("Erro ao buscar todas as entidades." + e.getMessage());
        }
    }

    public void atualizar(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = JPAUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new ErroAcessoDadosException("Erro ao fazer o update da Entidade." + e.getMessage());
        }
    }

    public void deletar(T entity) {
        EntityTransaction tx = null;
        try (EntityManager em = JPAUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new ErroAcessoDadosException("Erro ao remover a Entidade." + e.getMessage());
        }
    }
}
