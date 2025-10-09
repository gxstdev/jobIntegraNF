package org.jobIntegraNf.job;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.EmailService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.util.FileUtil;

/**
 * Job responsável por enviar por e-mail os arquivos de NFs processadas e,
 * após sucesso, movê-los para o diretório de expurgo.
 */
public class JobEnviaNFsEmail extends AbstractJobArquivos {

    private final ParametroSistemaService parametroSistemaService;
    private final EmailService emailService;
    private final ArquivoNFService arquivoNFService;

    public JobEnviaNFsEmail(EmailService emailService, ArquivoNFService arquivoNFService,
        ParametroSistemaService parametroSistemaService) {
        this.emailService = emailService;
        this.arquivoNFService = arquivoNFService;
        this.parametroSistemaService = parametroSistemaService;
    }

    @Override
    protected List<File> obterArquivos() {
        return this.arquivoNFService.getNFsTxtProcessadas();
    }

    @Override
    protected int tamanhoBatch() {
        return this.parametroSistemaService.getBatchEmail();
    }

    @Override
    protected boolean processarBatch(List<File> arquivosBatch) {
        return this.emailService.enviarNFsProcessadas(arquivosBatch);
    }

    @Override
    protected void aposSucesso(List<File> arquivosBatch) {
        FileUtil.moverArquivos(arquivosBatch, this.parametroSistemaService.getDirExpurgadas());
    }
}
