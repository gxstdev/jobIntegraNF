package org.jobIntegraNf.exception;

/**
 * Exceção de runtime para erros relacionados ao envio de e-mails.
 */
public class EmailException extends RuntimeException {
    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
