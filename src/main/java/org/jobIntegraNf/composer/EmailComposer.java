package org.jobIntegraNf.composer;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.dto.EmailMessage;

/**
 * Responsável por montar mensagens de e-mail a partir de dados do domínio.
 */
public interface EmailComposer {
    /**
     * Monta a mensagem de envio de NFs processadas.
     * @param anexos lista de arquivos de NFs processadas.
     * @return mensagem pronta para envio.
     */
    EmailMessage composeNFsProcessadas(List<File> anexos);
}
