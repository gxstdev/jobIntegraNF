package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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

    public void updateStatusNF(Long codigoStatus, Long cdNF){
        EntityTransaction tx =  this.em.getTransaction();
        try {
            tx.begin();
            String sql = "UPDATE TB_NF NF SET NF.CD_STATUS = ?1 WHERE NF.CD_NF = ?2";
            this.em.createNativeQuery(sql).setParameter(1, codigoStatus).setParameter(2, cdNF).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new ErroAcessoDadosException("Erro ao atualizar status da NF. Caused By: " + e.getMessage());
        }
    }

}
