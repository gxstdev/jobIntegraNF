package org.jobIntegraNf.job;

import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.exception.FileException;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.service.impl.NFServiceImpl;
import org.jobIntegraNf.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobProcessaNFs {

    private static NFService nfService = new NFServiceImpl();
    private static List<String> listNfsPendentes = new ArrayList<>();

    public static void executar() {
        try {
            StringBuilder cdNFs =  new StringBuilder();
            List<File> arquivos = FileUtils.getNFsTxtPendentes();

            for (File f : arquivos) {
                cdNFs.append(Arrays.asList(f.getName().split("_")).get(1).replace(".txt", ""))
                        .append(",");
            }

            if (!cdNFs.isEmpty()) {
                cdNFs.setLength(cdNFs.length() - 1);
            }

            boolean isNFsProcessadas = nfService.atualizarStatusNF(StatusNF.NF_PROCESSADA.getCodigoStatus(), cdNFs.toString());
            if (isNFsProcessadas) {
                FileUtils.moverArquivos(arquivos, FileUtils.getDirProcessadas());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException("Não foi possível mover arquivos para o diretório: " + FileUtils.getDirProcessadas());
        }
    }


}
