package org.jobIntegraNf.job;

import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.service.EmailService;
import org.jobIntegraNf.service.impl.GmailEmailServiceImpl;
import org.jobIntegraNf.util.EmailUtil;
import org.jobIntegraNf.util.FileUtil;

import java.io.File;
import java.util.List;

public class JobEnviaNFsEmail {
    private static final EmailService emailService = new GmailEmailServiceImpl();

    public static void executar() {
        try {
            List<File> arquivos = FileUtil.getNFsTxtProcessadas();
            while (!arquivos.isEmpty()) {
                List<File> arquivosParaEnviar = FileUtil.gerarBatchArquivos(arquivos, 50);

                boolean isEmailEnviado = emailService.sendEmail(EmailUtil.gerarEmail(arquivosParaEnviar));
                if (isEmailEnviado) {
                    FileUtil.moverArquivos(arquivosParaEnviar, FileUtil.DIRETORIO_NFS_EXPURGADAS);
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
            throw new ExecutaJobException("Não foi possível executar JobEnviaNFsEmail. Caused By: " + e);
        }
    }
}
