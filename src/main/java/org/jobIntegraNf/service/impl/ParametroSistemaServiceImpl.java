package org.jobIntegraNf.service.impl;

import org.jobIntegraNf.dao.ParametroSistemaDAO;
import org.jobIntegraNf.enums.Parametros;
import org.jobIntegraNf.exception.ErroAcessoDadosException;
import org.jobIntegraNf.model.ParametroSistema;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.util.JPAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;

public class ParametroSistemaServiceImpl implements ParametroSistemaService {
    
    private static final Logger log = LoggerFactory.getLogger(ParametroSistemaServiceImpl.class);

    public String getDirPendentes() {
    try (EntityManager em = JPAUtil.getEntityManager()) {
        ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(ParametroSistema.class, em);
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.DIRETORIO_NFS_PENDENTES.getDescricaoParametro());
    } catch (Exception e) {
        log.error("Erro ao buscar diretório de NFs pendentes.", e);
        throw new ErroAcessoDadosException("Erro ao buscar diretório de NFs pendentes.", e);
    }        
    }

    public String getDirProcessadas() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(ParametroSistema.class, em);
            return parametroSistemaDAO.findByDescricaoParametro(Parametros.DIRETORIO_NFS_PROCESSADA.getDescricaoParametro());
        } catch (Exception e) {
            log.error("Erro ao buscar diretório de NFs processadas.", e);
            throw new ErroAcessoDadosException("Erro ao buscar diretório de NFs processadas.", e);
        }
    }

    public String getDirExpurgadas() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(ParametroSistema.class, em);
            return parametroSistemaDAO.findByDescricaoParametro(Parametros.DIRETORIO_NFS_EXPURGADAS.getDescricaoParametro());
        } catch (Exception e) {
            log.error("Erro ao buscar diretório de NFs expurgadas.", e);
            throw new ErroAcessoDadosException("Erro ao buscar diretório de NFs expurgadas.", e);
        }
    }
    
    @Override
    public String findByDescricaoParametro(String descricaoParametro) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(ParametroSistema.class, em);
            return parametroSistemaDAO.findByDescricaoParametro(descricaoParametro);
        } catch (Exception e) {
            log.error("Erro ao buscar parâmetro sistema.", e);
            throw new ErroAcessoDadosException("Erro ao buscar parâmetro sistema.", e);
        }
    }
}
