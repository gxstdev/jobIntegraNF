package org.jobIntegraNf.job;

import java.time.OffsetDateTime;

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


public class ExecutaJob {
    private static final Logger log = LoggerFactory.getLogger(ExecutaJob.class);

    public static void main(String[] args) {
        NFService nfService = new NFServiceImpl();
        ArquivoNFService arquivoNFService = new ArquivoNFServiceImpl();
        ParametroSistemaService parametroSistemaService = new ParametroSistemaServiceImpl();
        EmailService emailService = new EmailServiceImpl();
        
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
                new JobExpurgo(arquivoNFService).executar();
                log.info("Finalizando - JOB EXPURGO NFs  - {}", OffsetDateTime.now());
                break;
        }

    }
}
