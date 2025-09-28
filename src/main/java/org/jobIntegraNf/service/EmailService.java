package org.jobIntegraNf.service;

import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.exception.EmailException;

import java.util.List;

public interface EmailService {
    boolean sendEmail(EmailMessage message) throws EmailException;
}