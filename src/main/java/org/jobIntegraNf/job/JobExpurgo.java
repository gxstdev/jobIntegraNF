package org.jobIntegraNf.job;

import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.util.FileUtils;

import java.io.File;
import java.util.List;

public class JobExpurgo {
    public static void executar() {
        try {
            List<File> arquivos = FileUtils.getNFsTxtExpurgadas();
            while (!arquivos.isEmpty()) {
                List<File> arquivosParaExpurgar= FileUtils.gerarBatchArquivos(arquivos, 1000);
                FileUtils.expurgarArquivos(arquivosParaExpurgar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExecutaJobException("Não foi possível executar JobEnviaNFsEmail. Caused By: " + e);
        }
    }
}
