package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.model.NotaFiscal;

import java.util.List;

/**
 * DAO de Nota Fiscal com consultas específicas por status e atualização em lote.
 */
public class NFDAO extends GenericDAO<NotaFiscal> {

    /**
     * @param clazz classe da entidade.
     * @param em    entity manager.
     */
    public NFDAO(Class<NotaFiscal> clazz, EntityManager em) {
        super(clazz, em);
    }

    @SuppressWarnings("unchecked")
    /**
     * Recupera NFs por status.
     * @param status filtro de status.
     * @return lista de NFs.
     */
    public List<NotaFiscal> findByStatus(StatusNF status) {
        String sql = "SELECT * FROM TB_NF NF WHERE NF.CD_STATUS = ?1";
        return this.em.createNativeQuery(sql, NotaFiscal.class)
                .setParameter(1, status.getCodigoStatus())
                .getResultList();
    }

    /**
     * Atualiza o status de múltiplas NFs em uma única operação.
     * @param status novo status.
     * @param cdNFs  códigos das notas fiscais.
     * @return true se houve atualização; false se lista vazia/nula.
     */
    public boolean updateStatusNF(StatusNF status, List<Long> cdNFs) {
        if (cdNFs == null || cdNFs.isEmpty()) return false;   
        Query query = em.createQuery(
                "UPDATE TbNF nf SET nf.status = :status WHERE nf.codigoNF IN :cdNf"
        );
        query.setParameter("status", status).setParameter("cdNf", cdNFs).executeUpdate();  
        return true;    
    }
}
