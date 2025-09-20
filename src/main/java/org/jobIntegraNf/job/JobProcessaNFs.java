package org.jobIntegraNf.job;

import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.service.impl.NFServiceImpl;
import org.jobIntegraNf.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobProcessaNFs {
    private static final String PATH_NFs_PENDENTES = FileUtils.getDirPendentes();

    private static NFService nfService = new NFServiceImpl();

    private static List<String> listNfsPendentes = new ArrayList<>();

    public static void executar() {
        File file = new File(PATH_NFs_PENDENTES);

        List<File> arquivos = Arrays.stream(file.listFiles(File::isFile)).toList();

        for (File f : arquivos) {
            String fileName = f.getName();
            String cdNF = Arrays.asList(f.getName().split("_")).get(1).replace(".txt", "");
            listNfsPendentes.add(cdNF);
            nfService.atualizarStatusNF(StatusNF.NF_PROCESSADA.getCodigoStatus(), Long.valueOf(cdNF));
            System.out.println(cdNF);
            f.delete();
        }
    }
}
