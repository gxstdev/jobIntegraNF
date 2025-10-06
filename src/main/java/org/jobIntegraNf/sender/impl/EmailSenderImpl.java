package org.jobIntegraNf.sender.impl;
import java.io.File;
import java.util.Properties;

import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.enums.Parametros;
import org.jobIntegraNf.exception.EmailException;
import org.jobIntegraNf.sender.EmailSender;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.jobIntegraNf.service.impl.ParametroSistemaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailSenderImpl implements EmailSender {
    private final Logger log = LoggerFactory.getLogger(EmailSenderImpl.class);
    
    private final ParametroSistemaService parametroSistemaService = new ParametroSistemaServiceImpl();

    private final String SMTP_HOST = getSmtpHost();
    private final String SMTP_PORT = getSmtpPort();
    private final String SMTP_USERNAME = getSmtpUsername();
    private final String SMTP_PASSWORD = getSmtpPassword();
    
    private String getSmtpHost(){
        return parametroSistemaService.findByDescricaoParametro(Parametros.SMTP_HOST.getDescricaoParametro());
    }

    private String getSmtpPort(){
        return parametroSistemaService.findByDescricaoParametro(Parametros.SMTP_PORT.getDescricaoParametro());
    }

    private String getSmtpUsername(){
        return parametroSistemaService.findByDescricaoParametro(Parametros.SMTP_USERNAME.getDescricaoParametro());
    }

   

    private String getSmtpPassword(){
        return parametroSistemaService.findByDescricaoParametro(Parametros.SMTP_PASSWORD.getDescricaoParametro());
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // usa STARTTLS na porta 587
        props.put("mail.smtp.connectiontimeout", "90000"); // 90 segundos
        props.put("mail.smtp.timeout", "90000");           // 90 segundos
        props.put("mail.smtp.writetimeout", "90000");      // 90 segundos

        Session s = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        s.setDebug(true);
        return s;
    }

    @Override
    public boolean send(EmailMessage message){
        try {
            //objeto de e-mail para ser enviado, será enviado a partir dos dados do objeto session
            MimeMessage mime = new MimeMessage(createSession());
            //pega o e-mail do remetente e transforme em um objeto InternetAddress
            mime.setFrom(new InternetAddress(SMTP_USERNAME));
            InternetAddress[] toAddresses = InternetAddress.parse(message.getTo());
            //define os destinatários
            mime.setRecipients(Message.RecipientType.TO, toAddresses);
            //define o assunto do e-mail
            mime.setSubject(message.getSubject());

            if (message.getAttachments().isEmpty()) {
                // corpo simples
                mime.setText(message.getBody(), "utf-8");
            } else {
                // multipart com anexos
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(message.getBody());
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(textPart);
                for (File file : message.getAttachments()) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    attachPart.attachFile(file); // usa java.nio internamente
                    multipart.addBodyPart(attachPart);
                }
                mime.setContent(multipart);
            }
            Transport.send(mime);
            return true;
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail para " + message.getTo(), e);
            throw new EmailException("Erro ao enviar e-mail para " + message.getTo(), e);
        }
    }
}
