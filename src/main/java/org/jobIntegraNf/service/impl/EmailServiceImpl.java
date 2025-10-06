package org.jobIntegraNf.service.impl;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.composer.EmailComposer;
import org.jobIntegraNf.composer.impl.EmailComposerImpl;
import org.jobIntegraNf.exception.EmailException;
import org.jobIntegraNf.sender.EmailSender;
import org.jobIntegraNf.sender.impl.EmailSenderImpl;
import org.jobIntegraNf.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailServiceImpl implements EmailService {
    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailSender emailSender = new EmailSenderImpl();
    private final EmailComposer emailComposer = new EmailComposerImpl();

    @Override
    public boolean enviarNFsProcessadas(List<File> anexos) {
        try {
            return emailSender.send(emailComposer.gerarEmail(anexos));
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail de NFs processadas.", e);
            throw new EmailException("Erro ao enviar e-mail de NFs processadas.", e);
        }
    }
}

