package org.jobIntegraNf.composer.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.jobIntegraNf.composer.EmailComposer;
import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.service.ParametroSistemaService;
/**
 * Implementação de {@link org.jobIntegraNf.composer.EmailComposer} que
 * monta mensagens de e-mail com base nos parâmetros do sistema.
 */
public class EmailComposerImpl implements EmailComposer {

    private final ParametroSistemaService parametroSistemaService;

    public EmailComposerImpl(ParametroSistemaService parametroSistemaService) {
        this.parametroSistemaService = parametroSistemaService;
    }

    // TODO: assunto e corpo do e-mail vir de parâmetro do sistema
    // assim pode haver reutilização

    /** {@inheritDoc} */
    public EmailMessage composeNFsProcessadas(List<File> anexos) {
        String subject = String.format("NOTAS FISCAIS - %s", LocalDateTime.now());
        String body = "Segue anexo NFs já processadas.";
        return new EmailMessage(parametroSistemaService.getDestinatario(), subject, body, anexos);
    }
}
