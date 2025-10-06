package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import org.jobIntegraNf.model.ParametroSistema;

public class ParametroSistemaDAO extends GenericDAO<ParametroSistema> {
    public ParametroSistemaDAO(Class<ParametroSistema> clazz, EntityManager em) {
        super(clazz, em);
    }

    public String findByDescricaoParametro(String descricao) {
        String sql = "SELECT tps.TX_PARAMETRO FROM TB_PARAMETRO_SISTEMA tps WHERE tps.DS_PARAMETRO = ?1";
        return String.valueOf(em.createNativeQuery(sql)
                .setParameter(1, descricao).getSingleResult());
    }
}
