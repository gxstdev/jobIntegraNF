package org.jobIntegraNf.sender;

import org.jobIntegraNf.dto.EmailMessage;

public interface EmailSender {
    boolean send(EmailMessage message);
}
