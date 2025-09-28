package org.jobIntegraNf.dto;

import java.io.File;
import java.util.List;

public class EmailMessage {
    private final String from;
    private final List<String> to;
    private final String subject;
    private final String body;
    private final List<File> attachments;

    public EmailMessage(String from, List<String> to, String subject, String body, List<File> attachments) {
        this.from = from;
        this.to = List.copyOf(to);
        this.subject = subject;
        this.body = body;
        this.attachments = attachments == null ? List.of() : List.copyOf(attachments);
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public List<File> getAttachments() {
        return attachments;
    }
}

