package org.jobIntegraNf.service.impl;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.exception.EmailException;
import org.jobIntegraNf.service.EmailService;
import org.jobIntegraNf.util.EmailUtil;

import java.io.File;
import java.util.Properties;

public class GmailEmailServiceImpl implements EmailService {

    public GmailEmailServiceImpl() {
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", EmailUtil.SMTP_HOST);
        props.put("mail.smtp.port", EmailUtil.SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // usa STARTTLS na porta 587
        props.put("mail.smtp.connectiontimeout", "90000"); // 10 segundos
        props.put("mail.smtp.timeout", "90000");           // 20 segundos
        props.put("mail.smtp.writetimeout", "90000");

        Session s = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailUtil.SMTP_USERNAME, EmailUtil.SMTP_PASSWORD);
            }
        });

        s.setDebug(true);
        return s;
    }

    @Override
    public boolean sendEmail(EmailMessage message) throws EmailException {
        try {
            //objeto de e-mail para ser enviado, será enviado a partir dos dados do objeto session
            MimeMessage mime = new MimeMessage(createSession());
            //pega o e-mail do remetente e transforme em um objeto InternetAddress
            mime.setFrom(new InternetAddress(EmailUtil.SMTP_USERNAME));
            InternetAddress[] toAddresses = InternetAddress.parse(EmailUtil.DESTINATARIO);
            //define os destinatários
            mime.setRecipients(Message.RecipientType.TO, toAddresses);
            //define o assunto do e-mail
            mime.setSubject(message.getSubject());

            if (message.getAttachments().isEmpty()) {
                // corpo simples
                mime.setText(message.getBody());
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
        } catch (Exception e) {
            throw new EmailException("Erro ao enviar e-mail para " + EmailUtil.DESTINATARIO);
        }
        return true;
    }
}

