package org.jobIntegraNf.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;


public class ExecutaJob {
    private static final Logger log = LoggerFactory.getLogger(ExecutaJob.class);

    public static void main(String[] args) {

        String[] opt = new String[]{"PROCESSA", "EMAIL", "EXPURGO"};

        String job = opt[1];

        switch (job) {
            case "PROCESSA":
                log.info("Executando - JOB PROCESSA NFs - {}", OffsetDateTime.now());
                JobProcessaNFs.executar();
                log.info("Finalizando - JOB PROCESSA NFs - {}", OffsetDateTime.now());
                break;
            case "EMAIL":
                log.info("Executando - JOB ENVIA NFs E-MAIL - {}", OffsetDateTime.now());
                JobEnviaNFsEmail.executar();
                log.info("Finalizando - JOB ENVIA NFs E-MAIL  - {}", OffsetDateTime.now());
                break;
        }

    }
}
