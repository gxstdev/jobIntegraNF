package org.jobIntegraNf.job;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobExpurgo {
    private final Logger log = LoggerFactory.getLogger(JobExpurgo.class);
    
    private final ArquivoNFService arquivoNFService;
    
    public JobExpurgo(ArquivoNFService arquivoNFService) {
        this.arquivoNFService = arquivoNFService;
    }

    public void executar() {
        try {
            List<File> arquivos = this.arquivoNFService.getNFsTxtExpurgadas();

            while (!arquivos.isEmpty()) {
                List<File> arquivosParaExpurgar= FileUtil.gerarBatchArquivos(arquivos, 1000);
                FileUtil.expurgarArquivos(arquivosParaExpurgar);
            }

        } catch (Exception e) {
            log.error("Erro ao executar JobExpurgo", e);
            throw new ExecutaJobException("Não foi possível executar Job Expurgo NFs", e);
        }
    }
}
