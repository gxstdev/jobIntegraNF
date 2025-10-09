package org.jobIntegraNf.exception;

/**
 * Exceção de runtime para falhas na execução de jobs.
 */
public class ExecutaJobException extends RuntimeException {
    public ExecutaJobException(String message, Throwable cause) {
        super(message, cause);
    }
}
