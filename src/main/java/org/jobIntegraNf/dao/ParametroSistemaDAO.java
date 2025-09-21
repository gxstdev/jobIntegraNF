package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import org.jobIntegraNf.exception.ErroAcessoDadosException;
import org.jobIntegraNf.model.TbParametroSistema;
import org.jobIntegraNf.util.JPAUtil;

public class ParametroSistemaDAO extends GenericDAO<TbParametroSistema> {
    public ParametroSistemaDAO(Class<TbParametroSistema> clazz) {
        super(clazz);
    }

    public String findByDescricaoParametro(String descricao) {
        try(EntityManager em = JPAUtil.getEntityManager()) {
            String sql = "SELECT tps.TX_PARAMETRO FROM TB_PARAMETRO_SISTEMA tps WHERE tps.DS_PARAMETRO = ?1";
            return String.valueOf(em.createNativeQuery(sql)
                    .setParameter(1, descricao).getSingleResult());
        } catch (Exception e) {
            throw new ErroAcessoDadosException("Erro ao buscar parâmetro sistema. Caused By: " + e.getMessage());
        }
    }
}
