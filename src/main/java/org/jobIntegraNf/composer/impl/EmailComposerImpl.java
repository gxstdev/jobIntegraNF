package org.jobIntegraNf.composer.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.jobIntegraNf.composer.EmailComposer;
import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.enums.Parametros;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.service.impl.ParametroSistemaServiceImpl;

public class EmailComposerImpl implements EmailComposer {

    private final ParametroSistemaService parametroSistemaService = new ParametroSistemaServiceImpl();

    private String getDestinatario() {
        LocalTime agora = LocalTime.now();
        LocalTime limite = LocalTime.of(18, 0);

        if (agora.isBefore(limite)) {
            return parametroSistemaService
                    .findByDescricaoParametro(Parametros.EMAIL_ENVIO_DIURNO.getDescricaoParametro());
        }
        return parametroSistemaService.findByDescricaoParametro(Parametros.EMAIL_ENVIO_NOTURNO.getDescricaoParametro());
    }

    // TODO: assunto e corpo do e-mail vir de parâmetro do sistema
    // assim pode haver reutilização

    public EmailMessage gerarEmail(List<File> arquivosParaEnviar) {
        String subject = String.format("NOTAS FISCAIS - %s", LocalDateTime.now());
        String body = "Segue anexo NFs já processadas.";
        return new EmailMessage(getDestinatario(), subject, body, arquivosParaEnviar);
    }
}
