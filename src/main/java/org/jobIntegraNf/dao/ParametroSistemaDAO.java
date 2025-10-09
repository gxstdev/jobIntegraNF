package org.jobIntegraNf.dao;

import jakarta.persistence.EntityManager;
import org.jobIntegraNf.model.ParametroSistema;

/**
 * DAO de {@link org.jobIntegraNf.model.ParametroSistema} com leitura por descrição.
 */
public class ParametroSistemaDAO extends GenericDAO<ParametroSistema> {
    /**
     * @param clazz classe da entidade.
     * @param em    entity manager.
     */
    public ParametroSistemaDAO(Class<ParametroSistema> clazz, EntityManager em) {
        super(clazz, em);
    }

    /**
     * Busca o valor do parâmetro pela descrição.
     * @param descricao chave do parâmetro.
     * @return valor do parâmetro em formato de string.
     */
    public String findByDescricaoParametro(String descricao) {
        String sql = "SELECT tps.TX_PARAMETRO FROM TB_PARAMETRO_SISTEMA tps WHERE tps.DS_PARAMETRO = ?1";
        return String.valueOf(em.createNativeQuery(sql)
                .setParameter(1, descricao).getSingleResult());
    }
}
