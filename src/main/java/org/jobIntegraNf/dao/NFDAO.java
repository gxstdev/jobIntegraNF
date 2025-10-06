package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.jobIntegraNf.model.NotaFiscal;

import java.util.List;

public class NFDAO extends GenericDAO<NotaFiscal> {

    public NFDAO(Class<NotaFiscal> clazz, EntityManager em) {
        super(clazz, em);
    }

    @SuppressWarnings("unchecked")
    public List<NotaFiscal> findByStatus(Long codigoStatus) {
        String sql = "SELECT * FROM TB_NF NF WHERE NF.CD_STATUS = ?1";
        return this.em.createNativeQuery(sql, NotaFiscal.class).setParameter(1, codigoStatus).getResultList();
    }

    public boolean updateStatusNF(Long codigoStatus, List<Long> cdNFs) {
        if (cdNFs == null || cdNFs.isEmpty()) return false;   
        Query query = em.createQuery(
                "UPDATE TbNF nf SET nf.codigoStatus = :status WHERE nf.codigoNF IN :cdNf"
        );
        query.setParameter("status", codigoStatus).setParameter("cdNf", cdNFs).executeUpdate();  
        return true;    
    }
}
