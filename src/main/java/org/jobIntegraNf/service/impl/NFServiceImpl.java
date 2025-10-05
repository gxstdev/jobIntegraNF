package org.jobIntegraNf.service.impl;

import org.jobIntegraNf.dao.NFDAO;
import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.exception.ErroAcessoDadosException;
import org.jobIntegraNf.model.NotaFiscal;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class NFServiceImpl implements NFService {
    private static final Logger log = LoggerFactory.getLogger(NFServiceImpl.class);

    private final NFDAO nfDAO = new NFDAO(NotaFiscal.class);

    @Override
    public void salvarNFs(List<NotaFiscal> nfs) {
        if (nfs.isEmpty()) return;
        for (NotaFiscal nf : nfs) {
            nfDAO.salvar(nf);
            FileUtil.gerarNFTxt(nf);
        }
    }

    @Override
    public List<NotaFiscal> buscarNFsPendentes() {
        return nfDAO.findByStatus(StatusNF.NF_PENDENTE_PROCESSAMENTO.getCodigoStatus());
    }

    @Override
    public List<NotaFiscal> buscarNFsProcessadas() {
        return nfDAO.findByStatus(StatusNF.NF_PROCESSADA.getCodigoStatus());
    }

    public boolean atualizarStatusNF(Long codigoStatus, List<Long> cdNfs) throws ErroAcessoDadosException {
        return nfDAO.updateStatusNF(codigoStatus, cdNfs);
    }

    public boolean processarNFs(List<File> arquivosParaProcessar) {
        try {
            List<Long> listCdsNFs = FileUtils.extrairCodigosNFs(arquivosParaProcessar);
            log.info("Processando {} arquivos. Atualizando status das NFs: {}", arquivosParaProcessar.size(), listCdsNFs);
            return atualizarStatusNF(StatusNF.NF_PROCESSADA.getCodigoStatus(), listCdsNFs);
        } catch (Exception e){
            log.warn("Não foi possível processar os arquivos");
            e.printStackTrace();
        }
      return false;
    }
}
