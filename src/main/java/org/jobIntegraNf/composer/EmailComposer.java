package org.jobIntegraNf.composer;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.dto.EmailMessage;

public interface EmailComposer {
    public EmailMessage gerarEmail(List<File> arquivosParaEnviar);
}
