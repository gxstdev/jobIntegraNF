package org.jobIntegraNf.service.impl;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.exception.EmailException;
import org.jobIntegraNf.service.EmailService;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class GmailEmailServiceImpl implements EmailService {

    private final String smtpHost;
    private final int smtpPort;
    private final String username; // exemplo: seu.email@gmail.com
    private final String password; // senha de app
    private final Session session;

    public GmailEmailServiceImpl(String smtpHost, int smtpPort, String username, String password, boolean debug) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
        this.session = createSession(debug);
    }

    private Session createSession(boolean debug) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // usa STARTTLS na porta 587
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        Session s = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        s.setDebug(debug);
        return s;
    }

    @Override
    public void sendEmail(EmailMessage message) throws EmailException {
        try {
            //objeto de e-mail para ser enviado, será enviado a partir dos dados do objeto session
            MimeMessage mime = new MimeMessage(session);

            //pega o e-mail do remetente e transforme em um objeto InternetAddress
            mime.setFrom(new InternetAddress(message.getFrom()));

            InternetAddress[] toAddresses = message.getTo().stream()
                    .map(addr -> {
                        //converte cada string (e-mail destinatário) para um objeto InternetAddress
                        try {
                            return new InternetAddress(addr);
                        } catch (AddressException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(InternetAddress[]::new);

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
            throw new EmailException("Erro ao enviar e-mail para " + message.getTo());
        }
    }

    @Override
    public void sendBatch(List<EmailMessage> messages) throws EmailException {
        for (EmailMessage m : messages) {
            sendEmail(m);
        }
    }
}

