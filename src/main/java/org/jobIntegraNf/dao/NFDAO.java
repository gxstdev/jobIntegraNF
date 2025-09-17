package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import org.jobIntegraNf.model.TbNF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
            log.error("Erro ao buscar NFs por status", e);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
