package org.jobIntegraNf.exception;

/**
 * Exceção de runtime para falhas de I/O na manipulação de arquivos.
 */
public class FileException extends RuntimeException {
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
