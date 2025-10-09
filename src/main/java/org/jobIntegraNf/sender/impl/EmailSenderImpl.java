package org.jobIntegraNf.sender.impl;
import java.io.File;
import java.util.Properties;

import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.enums.Parametros;
import org.jobIntegraNf.exception.EmailException;
import org.jobIntegraNf.sender.EmailSender;
import org.jobIntegraNf.service.ParametroSistemaService;
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

/**
 * Implementação de {@link org.jobIntegraNf.sender.EmailSender} baseada em
 * Jakarta Mail (Angus) para envio de e-mails com ou sem anexos.
 */
public class EmailSenderImpl implements EmailSender {
    private final Logger log = LoggerFactory.getLogger(EmailSenderImpl.class);
    
    private final ParametroSistemaService parametroSistemaService;

    private final String SMTP_HOST;
    private final String SMTP_PORT;
    private final String SMTP_USERNAME;
    private final String SMTP_PASSWORD;

    public EmailSenderImpl(ParametroSistemaService parametroSistemaService) {
        this.parametroSistemaService = parametroSistemaService;
        this.SMTP_HOST = this.getSmtpHost();
        this.SMTP_PORT = this.getSmtpPort();
        this.SMTP_USERNAME = this.getSmtpUsername();
        this.SMTP_PASSWORD = this.getSmtpPassword();
    }

    private String getSmtpHost(){
        return this.parametroSistemaService.findByDescricaoParametro(Parametros.SMTP_HOST.getDescricaoParametro());
    }

    private String getSmtpPort(){
        return this.parametroSistemaService.findByDescricaoParametro(Parametros.SMTP_PORT.getDescricaoParametro());
    }

    private String getSmtpUsername(){
        return this.parametroSistemaService.findByDescricaoParametro(Parametros.SMTP_USERNAME.getDescricaoParametro());
    }

    private String getSmtpPassword(){
        return this.parametroSistemaService.findByDescricaoParametro(Parametros.SMTP_PASSWORD.getDescricaoParametro());
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

    /** {@inheritDoc} */
    @Override
    public boolean send(EmailMessage message){
        try {
            MimeMessage mime = new MimeMessage(createSession());
            mime.setFrom(new InternetAddress(SMTP_USERNAME));
            InternetAddress[] toAddresses = InternetAddress.parse(message.getTo());
            mime.setRecipients(Message.RecipientType.TO, toAddresses);
            mime.setSubject(message.getSubject());

            if (message.getAttachments().isEmpty()) {
                mime.setText(message.getBody(), "utf-8");
            } else {
                Multipart multipart = new MimeMultipart();
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(message.getBody());
                multipart.addBodyPart(textPart);
                for (File file : message.getAttachments()) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    attachPart.attachFile(file);
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
