package org.jobIntegraNf.util;

import org.jobIntegraNf.dao.NFDAO;
import org.jobIntegraNf.dao.ParametroSistemaDAO;
import org.jobIntegraNf.enums.Parametros;
import org.jobIntegraNf.exception.FileException;
import org.jobIntegraNf.model.TbNF;
import org.jobIntegraNf.model.TbParametroSistema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileUtils {
    private static final ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(TbParametroSistema.class,
            JPAUtil.getEntityManager());

    private static final String DIRETORIO_NF_PENDENTES = getDirPendentes();

    private static final String DIRETORIO_NF_PROCESSADAS = getDirProcessadas();

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);


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

    public static String getDirProcessadas() {
        return parametroSistemaDAO.findByDescricaoParametro(Parametros.DIRETORIO_NFS_PROCESSADA.getDescricaoParametro());
    }

    public static List<File> getNFsTxtPendentes(){
        String path = getDirPendentes();
        File[] files = new File(path).listFiles(File::isFile);
        return ((files != null && files.length > 0) ? Arrays.stream(files).toList() : new ArrayList<>());
    }

    public static void moverArquivos(List<File> arquivos, String destino) throws Exception{
        for (File arquivo : arquivos){
           Files.move(Path.of(arquivo.getPath()), Path.of(destino), StandardCopyOption.REPLACE_EXISTING);
           log.info("Movendo arquivo: {} - para: {}", arquivo.getName(), destino);
        }
    }
}
