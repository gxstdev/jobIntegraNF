package org.jobIntegraNf.exception;

/**
 * Exceção de runtime para erros de acesso/consistência de dados na camada DAO.
 */
public class ErroAcessoDadosException extends RuntimeException {
    public ErroAcessoDadosException(String message, Throwable cause) {
        super(message, cause);
    }
}
