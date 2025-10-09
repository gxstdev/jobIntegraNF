package org.jobIntegraNf.job;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.util.FileUtil;

/**
 * Job responsável por expurgar (deletar) arquivos de NFs já processadas.
 */
public class JobExpurgo extends AbstractJobArquivos {
    
    private final ArquivoNFService arquivoNFService;
    private final ParametroSistemaService parametroSistemaService;
    
    public JobExpurgo(ArquivoNFService arquivoNFService, ParametroSistemaService parametroSistemaService) {
        this.arquivoNFService = arquivoNFService;
        this.parametroSistemaService = parametroSistemaService;
    }

    @Override
    protected List<File> obterArquivos() {
        return this.arquivoNFService.getNFsTxtExpurgadas();
    }

    @Override
    protected int tamanhoBatch() {
        return this.parametroSistemaService.getBatchExpurgo();
    }

    @Override
    protected boolean processarBatch(List<File> arquivosBatch) {
        FileUtil.expurgarArquivos(arquivosBatch);
        return true;
    }

    @Override
    protected void aposSucesso(List<File> arquivosBatch) {}
}
