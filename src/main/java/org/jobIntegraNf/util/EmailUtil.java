package org.jobIntegraNf.util;

import org.jobIntegraNf.dao.ParametroSistemaDAO;
import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.enums.Parametros;
import org.jobIntegraNf.model.ParametroSistema;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EmailUtil {
    private static final ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(ParametroSistema.class);

    public static final String SMTP_HOST = getSmtpHost();
    public static final String SMTP_PORT = getSmtpPort();
    public static final String SMTP_USERNAME = getSmtpUsername();
    public static final String SMTP_PASSWORD = getSmtpPassword();
    public static final String DESTINATARIO = getDestinatario();
    public static final Integer TAMANHO_ANEXOS = 25 * 1024 * 1024;

    private static String getSmtpHost(){
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.SMTP_HOST.getDescricaoParametro());
    }

    private static String getSmtpPort(){
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.SMTP_PORT.getDescricaoParametro());
    }

    private static String getSmtpUsername(){
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.SMTP_USERNAME.getDescricaoParametro());
    }

    private static String getDestinatario(){
        LocalTime agora = LocalTime.now();
        LocalTime limite = LocalTime.of(18, 0);

        if (agora.isBefore(limite)){
            return parametroSistemaDAO.findByDescricaoParametro(Parametros.EMAIL_ENVIO_DIURNO.getDescricaoParametro());
        }
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.EMAIL_ENVIO_NOTURNO.getDescricaoParametro());
    }

    private static String getSmtpPassword(){
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.SMTP_PASSWORD.getDescricaoParametro());
    }

    public static EmailMessage gerarEmail(List<File> arquivosParaEnviar){
        String subject = String.format("NOTAS FISCAIS - %s", LocalDateTime.now());
        String body = "Segue anexo NFs já processadas.";
        return new EmailMessage(subject, body, arquivosParaEnviar);
    }
}
