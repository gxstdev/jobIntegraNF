package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.jobIntegraNf.exception.ErroAcessoDadosException;
import org.jobIntegraNf.model.TbNF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NFDAO extends GenericDAO<TbNF> {
    private static final Logger log = LoggerFactory.getLogger(NFDAO.class);

    public NFDAO(Class<TbNF> clazz, EntityManager em) {
        super(clazz, em);
    }

    public List<TbNF> findByStatus(Long codigoStatus) {
        try {
            String sql = "SELECT * FROM TB_NF NF WHERE NF.CD_STATUS = ?1";
            return super.em.createNativeQuery(sql, TbNF.class).setParameter(1, codigoStatus).getResultList();
        } catch (Exception e) {
            throw new ErroAcessoDadosException("Erro ao buscar entidades por status. Caused By: " + e.getMessage());
        }
    }

    public boolean updateStatusNF(Long codigoStatus, List<Long> cdNFs) {
        if (cdNFs == null || cdNFs.isEmpty()) return false;
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            Query query = this.em.createQuery(
                    "UPDATE TbNF nf SET nf.codigoStatus = :status WHERE nf.codigoNF IN :cdNf"
            );
            query.setParameter("status", codigoStatus).setParameter("cdNf", cdNFs).executeUpdate();
            tx.commit();
            this.em.clear();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new ErroAcessoDadosException("Erro ao atualizar status da NF. Caused By: " + e.getMessage());
        } finally {
            this.em.close();
        }
    }
}
