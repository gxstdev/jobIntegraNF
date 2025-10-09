package org.jobIntegraNf.service.impl;

import java.time.LocalTime;

import org.jobIntegraNf.dao.ParametroSistemaDAO;
import org.jobIntegraNf.enums.Parametros;
import org.jobIntegraNf.exception.ErroAcessoDadosException;
import org.jobIntegraNf.model.ParametroSistema;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.util.JPAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

/**
 * Implementação de {@link org.jobIntegraNf.service.ParametroSistemaService}
 * que consulta parâmetros no banco de dados via DAO.
 */
public class ParametroSistemaServiceImpl implements ParametroSistemaService {

    private static final Logger log = LoggerFactory.getLogger(ParametroSistemaServiceImpl.class);

    @Override
    /** {@inheritDoc} */
    public String findByDescricaoParametro(String descricaoParametro) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(ParametroSistema.class, em);
            return parametroSistemaDAO.findByDescricaoParametro(descricaoParametro);
        } catch (PersistenceException e) {
            log.error("Erro ao buscar parâmetro sistema.", e);
            throw new ErroAcessoDadosException("Erro ao buscar parâmetro sistema.", e);
        }
    }

    @Override
    /** {@inheritDoc} */
    public String getDirPendentes() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return this.findByDescricaoParametro(Parametros.DIRETORIO_NFS_PENDENTES.getDescricaoParametro());
        } catch (PersistenceException e) {
            log.error("Erro ao buscar diretório de NFs pendentes.", e);
            throw new ErroAcessoDadosException("Erro ao buscar diretório de NFs pendentes.", e);
        }
    }

    @Override
    /** {@inheritDoc} */
    public String getDirProcessadas() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return this.findByDescricaoParametro(Parametros.DIRETORIO_NFS_PROCESSADA.getDescricaoParametro());
        } catch (PersistenceException e) {
            log.error("Erro ao buscar diretório de NFs processadas.", e);
            throw new ErroAcessoDadosException("Erro ao buscar diretório de NFs processadas.", e);
        }
    }

    @Override
    /** {@inheritDoc} */
    public String getDirExpurgadas() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return this.findByDescricaoParametro(Parametros.DIRETORIO_NFS_EXPURGADAS.getDescricaoParametro());
        } catch (PersistenceException e) {
            log.error("Erro ao buscar diretório de NFs expurgadas.", e);
            throw new ErroAcessoDadosException("Erro ao buscar diretório de NFs expurgadas.", e);
        }
    }

    @Override
    /** {@inheritDoc} */
    public String getDirEmEnvio() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return this.findByDescricaoParametro(Parametros.DIRETORIO_NFS_EM_ENVIO.getDescricaoParametro());
        } catch (PersistenceException e) {
            log.error("Erro ao buscar diretório de NFs em envio.", e);
            throw new ErroAcessoDadosException("Erro ao buscar diretório de NFs em envio.", e);
        }
    }

    @Override
    /** {@inheritDoc} */
    public int getBatchProcessa() {
        return readIntOrDefault(Parametros.JOB_LOTE_PROCESSA.getDescricaoParametro(), 1000);
    }

    @Override
    /** {@inheritDoc} */
    public int getBatchEmail() {
        return readIntOrDefault(Parametros.JOB_LOTE_EMAIL.getDescricaoParametro(), 50);
    }

    @Override
    /** {@inheritDoc} */
    public int getBatchExpurgo() {
        return readIntOrDefault(Parametros.JOB_LOTE_EXPURGO.getDescricaoParametro(), 1000);
    }

    @Override
    /** {@inheritDoc} */
    public String getDestinatario() {
        LocalTime agora = LocalTime.now();
        LocalTime limite = LocalTime.of(18, 0);

        if (agora.isBefore(limite)) {
            return this.findByDescricaoParametro(Parametros.EMAIL_ENVIO_DIURNO.getDescricaoParametro());
        }
        return this.findByDescricaoParametro(Parametros.EMAIL_ENVIO_NOTURNO.getDescricaoParametro());
    }

    private int readIntOrDefault(String descricao, int def) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            String value = this.findByDescricaoParametro(descricao);
            return value == null ? def : Integer.parseInt(value);
        } catch (Exception e) {
            log.warn("Parâmetro {} inválido. Usando padrão {}.", descricao, def);
            return def;
        }
    }
}
