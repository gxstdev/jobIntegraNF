package org.jobIntegraNf.job;

import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.util.FileUtil;

import java.io.File;
import java.util.List;

public class JobExpurgo {
    public static void executar() {
        try {
            List<File> arquivos = FileUtil.getNFsTxtExpurgadas();
            while (!arquivos.isEmpty()) {
                List<File> arquivosParaExpurgar= FileUtil.gerarBatchArquivos(arquivos, 1000);
                FileUtil.expurgarArquivos(arquivosParaExpurgar);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExecutaJobException("Não foi possível executar JobEnviaNFsEmail. Caused By: " + e);
        }
    }
}
