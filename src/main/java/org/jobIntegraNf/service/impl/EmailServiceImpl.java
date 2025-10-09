package org.jobIntegraNf.service.impl;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.composer.EmailComposer;
import org.jobIntegraNf.exception.EmailException;
import org.jobIntegraNf.sender.EmailSender;
import org.jobIntegraNf.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementação de {@link org.jobIntegraNf.service.EmailService} que utiliza
 * um {@link org.jobIntegraNf.sender.EmailSender} e um composer para montar e enviar e-mails.
 */
public class EmailServiceImpl implements EmailService {
    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailSender emailSender;
    private final EmailComposer emailComposer;

    public EmailServiceImpl(EmailSender emailSender, EmailComposer emailComposer) {
        this.emailSender = emailSender;
        this.emailComposer = emailComposer;
    }

    /** {@inheritDoc} */
    @Override
    public boolean enviarNFsProcessadas(List<File> anexos) {
        try {
            return this.emailSender.send(this.emailComposer.composeNFsProcessadas(anexos));
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail de NFs processadas.", e);
            throw new EmailException("Erro ao enviar e-mail de NFs processadas.", e);
        }
    }
}

