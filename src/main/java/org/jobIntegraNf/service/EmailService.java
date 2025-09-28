package org.jobIntegraNf.service;

import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.exception.EmailException;

import java.util.List;

public interface EmailService {
    /**
     * Envia um e-mail simples (com ou sem anexos). Pode lançar EmailException em caso de falha.
     */
    void sendEmail(EmailMessage message) throws EmailException;

    /**
     * Envia vários e-mails (padrão: envia um por vez, com retry/controle interno).
     */
    void sendBatch(List<EmailMessage> messages) throws EmailException;
}

