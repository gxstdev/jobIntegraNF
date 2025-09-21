package org.jobIntegraNf.job;

import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.service.impl.NFServiceImpl;
import org.jobIntegraNf.util.FileUtils;

import java.io.File;
import java.util.List;

public class JobProcessaNFs {

    private static NFService nfService = new NFServiceImpl();

    public static void executar() {
        try {
            List<File> arquivos = FileUtils.getNFsTxtPendentes();
            while (!arquivos.isEmpty()) {
                List<File> arquivosParaProcessar = FileUtils.gerarBatchArquivos(arquivos, 1000);

                boolean isNFsProcessadas = nfService.processarNFs(arquivosParaProcessar);
                if (isNFsProcessadas) {
                    FileUtils.moverArquivos(arquivosParaProcessar, FileUtils.DIRETORIO_NF_PROCESSADAS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExecutaJobException("Não foi possível executar JobProcessaNFs. Caused By: " + e);
        }
    }
}
