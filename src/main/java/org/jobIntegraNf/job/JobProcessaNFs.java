package org.jobIntegraNf.job;

import jakarta.persistence.Query;
import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.exception.FileException;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.service.impl.NFServiceImpl;
import org.jobIntegraNf.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JobProcessaNFs {

    private static NFService nfService = new NFServiceImpl();
    private static List<Long> listCdNFs = new ArrayList<>();

    public static void executar() {
        try {
            List<File> arquivos = FileUtils.getNFsTxtPendentes();
            while (!arquivos.isEmpty()) {
                //Pega 1000 arquivos por vez
                List<File> control = arquivos.subList(0, arquivos.size() >= 1000 ? 1000 : arquivos.size());

                for (File f : control) {
                    listCdNFs.add(Long.valueOf(Arrays.asList(f.getName().split("_")).get(1).replace(".txt", "")));
                }
                boolean isNFsProcessadas = nfService.atualizarStatusNF(StatusNF.NF_PROCESSADA.getCodigoStatus(), listCdNFs);
                if (isNFsProcessadas) {
                    FileUtils.moverArquivos(control, FileUtils.getDirProcessadas());
                }
                control.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException("Não foi possível mover arquivos para o diretório: " + FileUtils.getDirProcessadas());
        }
    }
}
