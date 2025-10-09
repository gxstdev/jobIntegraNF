package org.jobIntegraNf.job;

import java.time.OffsetDateTime;

import org.jobIntegraNf.composer.EmailComposer;
import org.jobIntegraNf.composer.impl.EmailComposerImpl;
import org.jobIntegraNf.sender.EmailSender;
import org.jobIntegraNf.sender.impl.EmailSenderImpl;
import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.EmailService;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.service.impl.ArquivoNFServiceImpl;
import org.jobIntegraNf.service.impl.EmailServiceImpl;
import org.jobIntegraNf.service.impl.NFServiceImpl;
import org.jobIntegraNf.service.impl.ParametroSistemaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Classe principal para execução manual dos jobs de processamento, e-mail e expurgo.
 */
public class ExecutaJob {
    private static final Logger log = LoggerFactory.getLogger(ExecutaJob.class);

    public static void main(String[] args) {
   
        ParametroSistemaService parametroSistemaService = new ParametroSistemaServiceImpl();
        ArquivoNFService arquivoNFService = new ArquivoNFServiceImpl(parametroSistemaService);
        NFService nfService = new NFServiceImpl(arquivoNFService);
        EmailSender emailSender = new EmailSenderImpl(parametroSistemaService);
        EmailComposer emailComposer = new EmailComposerImpl(parametroSistemaService);
        EmailService emailService = new EmailServiceImpl(emailSender, emailComposer);
        
        String[] opt = new String[]{"PROCESSA", "EMAIL", "EXPURGO"};

        String job = opt[2];

        switch (job) {
            case "PROCESSA":
                log.info("Executando - JOB PROCESSA NFs - {}", OffsetDateTime.now());
                new JobProcessaNFs(nfService, arquivoNFService, parametroSistemaService).executar();
                log.info("Finalizando - JOB PROCESSA NFs - {}", OffsetDateTime.now());
                break;
            case "EMAIL":
                log.info("Executando - JOB ENVIA NFs E-MAIL - {}", OffsetDateTime.now());
                new JobEnviaNFsEmail(emailService, arquivoNFService, parametroSistemaService).executar();
                log.info("Finalizando - JOB ENVIA NFs E-MAIL  - {}", OffsetDateTime.now());
                break;
            case "EXPURGO":
                log.info("Executando - JOB EXPURGO NFs - {}", OffsetDateTime.now());
                new JobExpurgo(arquivoNFService, parametroSistemaService).executar();
                log.info("Finalizando - JOB EXPURGO NFs  - {}", OffsetDateTime.now());
                break;
        }
    }
}
