package org.jobIntegraNf.service.impl;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.dao.NFDAO;
import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.exception.ErroAcessoDadosException;
import org.jobIntegraNf.model.NotaFiscal;
import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.util.JPAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

/**
 * Implementação de {@link org.jobIntegraNf.service.NFService} que coordena
 * persistência, atualização de status e processamento de NFs.
 */
public class NFServiceImpl implements NFService {
    private static final Logger log = LoggerFactory.getLogger(NFServiceImpl.class);

    private final ArquivoNFService arquivoNFService;

    public NFServiceImpl(ArquivoNFService arquivoNFService) {
        this.arquivoNFService = arquivoNFService;
    }

    /** {@inheritDoc} */
    @Override
    public void salvarNFs(List<NotaFiscal> nfs) {
        if (nfs.isEmpty()) return;
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try (em) {
            NFDAO nfDAO = new NFDAO(NotaFiscal.class, em);
            for (NotaFiscal nf : nfs) {
                tx.begin();
                nfDAO.salvar(nf);
                tx.commit();
                arquivoNFService.gerarNFTxt(nf);
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error("Erro ao salvar NFs.", e);
            throw new ErroAcessoDadosException("Erro ao salvar NFs.", e);
        }
    }

    @Override
    public List<NotaFiscal> buscarNFsPendentes() {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            NFDAO nfDAO = new NFDAO(NotaFiscal.class, em);
            return nfDAO.findByStatus(StatusNF.NF_PENDENTE_PROCESSAMENTO.getCodigoStatus());
        } catch (Exception e) {
            log.error("Erro ao buscar NFs pendentes.", e);
            throw new ErroAcessoDadosException("Erro ao buscar NFs pendentes.", e);
        }
    }

    @Override
    public List<NotaFiscal> buscarNFsProcessadas() {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            NFDAO nfDAO = new NFDAO(NotaFiscal.class, em);
            return nfDAO.findByStatus(StatusNF.NF_PROCESSADA.getCodigoStatus());
        } catch (Exception e) {
            log.error("Erro ao buscar NFs processadas.", e);
            throw new ErroAcessoDadosException("Erro ao buscar NFs processadas.", e);
        }
    }

    public boolean atualizarStatusNF(Long codigoStatus, List<Long> cdNfs) throws ErroAcessoDadosException {
        boolean result = false;

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try (em) {
            tx.begin();
            NFDAO nfDAO = new NFDAO(NotaFiscal.class, em);
            result = nfDAO.updateStatusNF(codigoStatus, cdNfs);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error("Erro ao atualizar status da NF.", e);
            throw new ErroAcessoDadosException("Erro ao atualizar status da NF.", e);
        }
    }

    public boolean processarNFs(List<File> arquivosParaProcessar) {
        try {
            List<Long> listCdsNFs = arquivoNFService.extrairCodigosNFs(arquivosParaProcessar);
            return atualizarStatusNF(StatusNF.NF_PROCESSADA.getCodigoStatus(), listCdsNFs);
        } catch (Exception e) {
            log.error("Erro ao processar NFs.", e);
            throw new ErroAcessoDadosException("Erro ao processar NFs.", e);
        }
    }
}
