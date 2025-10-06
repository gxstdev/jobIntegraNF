package org.jobIntegraNf.job;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.EmailService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobEnviaNFsEmail {
    private final Logger log = LoggerFactory.getLogger(JobEnviaNFsEmail.class);
    
    private final ParametroSistemaService parametroSistemaService;

    private final EmailService emailService;
    private final ArquivoNFService arquivoNFService ;

    public JobEnviaNFsEmail(EmailService emailService, ArquivoNFService arquivoNFService, ParametroSistemaService parametroSistemaService) {
        this.emailService = emailService;
        this.arquivoNFService = arquivoNFService;
        this.parametroSistemaService = parametroSistemaService;
    }

    public void executar() {
        try {
            List<File> arquivos = arquivoNFService.getNFsTxtProcessadas();
            while (!arquivos.isEmpty()) {
                List<File> arquivosParaEnviar = FileUtil.gerarBatchArquivos(arquivos, 50);

                boolean isEmailEnviado = emailService.enviarNFsProcessadas(arquivosParaEnviar);
                if (isEmailEnviado) {
                    FileUtil.moverArquivos(arquivosParaEnviar, parametroSistemaService.getDirExpurgadas());
                }
            }
        } catch (Exception e) {
            log.error("Erro ao executar JobEnviaNFsEmail", e);
            throw new ExecutaJobException("Não foi possível executar JobEnviaNFsEmail", e);
        }
    }
}
