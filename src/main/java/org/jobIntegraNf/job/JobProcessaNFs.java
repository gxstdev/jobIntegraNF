package org.jobIntegraNf.job;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobProcessaNFs {
    private final Logger log = LoggerFactory.getLogger(JobProcessaNFs.class);
    
    private final NFService nfService;
    private final ArquivoNFService arquivoNFService;
    private final ParametroSistemaService parametroSistemaService;

    public  JobProcessaNFs(NFService nfService, ArquivoNFService arquivoNFService, ParametroSistemaService parametroSistemaService) {
        this.nfService = nfService;
        this.arquivoNFService = arquivoNFService;
        this.parametroSistemaService = parametroSistemaService;
    }

    public void executar() {
        try {
            List<File> arquivos = this.arquivoNFService.getNFsTxtPendentes();
            while (!arquivos.isEmpty()) {
                List<File> arquivosParaProcessar = FileUtil.gerarBatchArquivos(arquivos, 1000);

                boolean isNFsProcessadas = this.nfService.processarNFs(arquivosParaProcessar);
                if (isNFsProcessadas) {
                    FileUtil.moverArquivos(arquivosParaProcessar, this.parametroSistemaService.getDirProcessadas());
                }
            }
        } catch (Exception e) {
            log.error("Erro ao executar JobProcessaNFs", e);
            throw new ExecutaJobException("Não foi possível executar JobProcessaNFs", e);
        }
    }
}
