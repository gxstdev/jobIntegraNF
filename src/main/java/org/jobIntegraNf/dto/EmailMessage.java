package org.jobIntegraNf.dto;

import java.io.File;
import java.util.List;

/**
 * DTO imutável que representa uma mensagem de e-mail com assunto, corpo e anexos.
 */
public class EmailMessage {
    private final String to;
    private final String subject;
    private final String body;
    private final List<File> attachments;

    /**
     * Cria uma nova mensagem.
     * @param to destinatário.
     * @param subject assunto.
     * @param body corpo do e-mail.
     * @param attachments lista de anexos (pode ser nula).
     */
    public EmailMessage(String to, String subject, String body, List<File> attachments) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments == null ? List.of() : List.copyOf(attachments);
    }

    /**
     * @return assunto do e-mail.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return corpo do e-mail.
     */
    public String getBody() {
        return body;
    }

    /**
     * @return anexos do e-mail (lista imutável).
     */
    public List<File> getAttachments() {
        return attachments;
    }

    /**
     * @return destinatário do e-mail.
     */
    public String getTo() {
        return to;
    }
}

