package org.jobIntegraNf.service;

import java.io.File;
import java.util.List;

/**
 * Serviço de envio de e-mails do sistema.
 */
public interface EmailService {
    /**
     * Envia um e-mail com as NFs processadas em anexo.
     * @param anexos arquivos de NFs processadas.
     * @return true em caso de envio bem-sucedido.
     */
    boolean enviarNFsProcessadas(List<File> anexos);
}