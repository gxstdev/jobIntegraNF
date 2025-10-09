package org.jobIntegraNf.sender;

import org.jobIntegraNf.dto.EmailMessage;

/**
 * Componente responsável por enviar mensagens de e-mail.
 */
public interface EmailSender {
    /**
     * Envia a mensagem informada.
     * @param message mensagem já composta.
     * @return true em caso de sucesso.
     */
    boolean send(EmailMessage message);
}
