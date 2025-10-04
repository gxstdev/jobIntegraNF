package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.jobIntegraNf.exception.ErroAcessoDadosException;
import org.jobIntegraNf.model.TbNF;

import org.jobIntegraNf.util.JPAUtil;

import java.util.List;

public class NFDAO extends GenericDAO<TbNF> {
    public NFDAO(Class<TbNF> clazz) {
        super(clazz);
    }

    @SuppressWarnings("unchecked")
    public List<TbNF> findByStatus(Long codigoStatus) {
        try(EntityManager em = JPAUtil.getEntityManager()) {
            String sql = "SELECT * FROM TB_NF NF WHERE NF.CD_STATUS = ?1";
            return em.createNativeQuery(sql, TbNF.class).setParameter(1, codigoStatus).getResultList();
        } catch (Exception e) {
            throw new ErroAcessoDadosException("Erro ao buscar entidades por status. Caused By: " + e.getMessage());
        }
    }

    public boolean updateStatusNF(Long codigoStatus, List<Long> cdNFs) {
        if (cdNFs == null || cdNFs.isEmpty()) return false;
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try (em) {
            tx.begin();
            Query query = em.createQuery(
                    "UPDATE TbNF nf SET nf.codigoStatus = :status WHERE nf.codigoNF IN :cdNf"
            );
            query.setParameter("status", codigoStatus).setParameter("cdNf", cdNFs).executeUpdate();
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new ErroAcessoDadosException("Erro ao atualizar status da NF. Caused By: " + e.getMessage());
        }
    }
}
