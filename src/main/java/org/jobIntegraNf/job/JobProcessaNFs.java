package org.jobIntegraNf.job;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.util.FileUtil;

/**
 * Job responsável por processar arquivos de NFs pendentes e atualizar seu status.
 */
public class JobProcessaNFs extends AbstractJobArquivos {
    
    private final NFService nfService;
    private final ArquivoNFService arquivoNFService;
    private final ParametroSistemaService parametroSistemaService;

    public  JobProcessaNFs(NFService nfService, ArquivoNFService arquivoNFService, ParametroSistemaService parametroSistemaService) {
        this.nfService = nfService;
        this.arquivoNFService = arquivoNFService;
        this.parametroSistemaService = parametroSistemaService;
    }

    @Override
    protected List<File> obterArquivos() {
        return this.arquivoNFService.getNFsTxtPendentes();
    }

    @Override
    protected int tamanhoBatch() {
        return this.parametroSistemaService.getBatchProcessa();
    }

    @Override
    protected boolean processarBatch(List<File> arquivosBatch) {
        return this.nfService.processarNFs(arquivosBatch);
    }

    @Override
    protected void aposSucesso(List<File> arquivosBatch) {
        FileUtil.moverArquivos(arquivosBatch, this.parametroSistemaService.getDirProcessadas());
    }
}
