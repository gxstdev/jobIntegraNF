package org.jobIntegraNf.util;

import org.jobIntegraNf.dao.ParametroSistemaDAO;
import org.jobIntegraNf.enums.Parametros;
import org.jobIntegraNf.exception.FileException;
import org.jobIntegraNf.model.TbNF;
import org.jobIntegraNf.model.TbParametroSistema;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtils {
    private static final ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(TbParametroSistema.class,
            JPAUtil.getEntityManager());

    private static final String DIRETORIO_NF_PENDENTES = getDirPendentes();

    private static final String DIRETORIO_NF_PROCESSADAS = getDirProcessadas();

    public static void gerarNFTxt(TbNF nf) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DIRETORIO_NF_PENDENTES + gerarNomeArquivo(nf)))) {
            bw.write(nf.toString());
        } catch (IOException e) {
            String errorMsg = String.format("Erro ao gerar arquivo TXT da NF: %d. Caused by %s",
                    nf.getCodigoNF(), e.getMessage());
            throw new FileException(errorMsg + e.getMessage());
        }
    }

    private static String gerarNomeArquivo(TbNF nf) {
        return String.format("nf_%d.txt", nf.getCodigoNF());
    }

    public static String getDirPendentes() {
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.DIRETORIO_NFS_PENDENTES.getDescricaoParametro());
    }

    private static String getDirProcessadas() {
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.DIRETORIO_NFS_PROCESSADA.getDescricaoParametro());
    }
}
