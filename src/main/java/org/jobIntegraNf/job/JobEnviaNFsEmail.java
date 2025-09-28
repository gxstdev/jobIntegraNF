package org.jobIntegraNf.job;

import org.jobIntegraNf.dao.ParametroSistemaDAO;
import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.model.TbParametroSistema;
import org.jobIntegraNf.service.EmailService;
import org.jobIntegraNf.service.impl.GmailEmailServiceImpl;
import org.jobIntegraNf.util.EmailUtils;
import org.jobIntegraNf.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JobEnviaNFsEmail {
    private static final EmailService emailService = new GmailEmailServiceImpl();

    public static void executar() {
        try {
            List<File> arquivos = FileUtils.getNFsTxtProcessadas();

            while (!arquivos.isEmpty()) {
                List<File> arquivosParaEnviar = FileUtils.gerarBatchArquivos(arquivos, 50);

                boolean isEmailEnviado = emailService.sendEmail(EmailUtils.gerarEmail(arquivosParaEnviar));

                if (isEmailEnviado) {
                    FileUtils.moverArquivos(arquivosParaEnviar, FileUtils.DIRETORIO_NFS_EXPURGADAS);
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
            throw new ExecutaJobException("Não foi possível executar JobEnviaNFsEmail. Caused By: " + e);
        }
    }
}
