package org.jobIntegraNf.service;

import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.exception.EmailException;

public interface EmailService {
    boolean sendEmail(EmailMessage message) throws EmailException;
}