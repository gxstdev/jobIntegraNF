package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import org.jobIntegraNf.model.TbParametroSistema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParametroSistemaDAO extends GenericDAO<TbParametroSistema> {
    private static final Logger log = LoggerFactory.getLogger(NFDAO.class);

    public ParametroSistemaDAO(Class<TbParametroSistema> clazz, EntityManager em) {
        super(clazz, em);
    }

    public String findByDescricaoParametro(String descricao){
        String sql = "SELECT tps.DS_PARAMETRO FROM TB_PARAMETRO_SISTEMA tps WHERE tps.DS_PARAMETRO = ?1";
        return String.valueOf(super.em.createNativeQuery(sql, TbParametroSistema.class)
                .setParameter(1, descricao).getSingleResult());
    }

}
