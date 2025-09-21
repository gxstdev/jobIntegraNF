package org.jobIntegraNf.util;

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
import java.util.stream.Collectors;


public class FileUtils {
    private static final ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(TbParametroSistema.class);

    public static final String DIRETORIO_NF_PENDENTES = getDirPendentes();

    public static final String DIRETORIO_NF_PROCESSADAS = getDirProcessadas();

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

    public static List<File> getNFsTxtPendentes() {
        File[] files = new File(DIRETORIO_NF_PENDENTES).listFiles(File::isFile);
        return ((files != null && files.length > 0) ? new ArrayList<>(Arrays.asList(files)) : new ArrayList<>());
    }

    public static void moverArquivos(List<File> arquivos, String destino) {
        try {
            for (File arquivo : arquivos) {
                //para mover, precisa sempre ter o caminho + nome do arquivo
                Files.move(arquivo.toPath(), Path.of(destino + arquivo.getName()), StandardCopyOption.REPLACE_EXISTING);
                log.info("Movendo arquivo: {} - para: {}", arquivo.getName(), destino);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new FileException("Não foi possível mover arquivos para o diretório: "+destino);
        }
    }

    public static List<Long> extrairCodigosNFs(List<File> arquivosParaProcessar) {
        return validarNomeArquivos(arquivosParaProcessar)
                .stream()
                .map(arq -> Long.valueOf(arq.getName().split("_")[1].replace(".txt", "")))
                .collect(Collectors.toList());
    }

    private static List<File> validarNomeArquivos(List<File> arquivosParaProcessar){
        List<File> arquivosValidos =  arquivosParaProcessar.stream().filter(arquivo ->
            arquivo.getName().split("_").length == 2).collect(Collectors.toList());

        List<File> arquivosInvalidos =  arquivosParaProcessar.stream().filter(arquivo ->
               arquivo.getName().split("_").length != 2).collect(Collectors.toList());
        if (!arquivosInvalidos.isEmpty()){
            log.warn("Os arquivos {} possuem nomes inválidos, não podem ser processados", arquivosInvalidos);
        }
        return arquivosValidos;
    }

    /**
     * Gera um batch de até {@code tamanhoBatch} arquivos da lista fornecida.
     * Os arquivos retornados são removidos da lista original.
     *
     * @param arquivos Lista original de arquivos (será modificada).
     * @param tamanhoBatch Número máximo de arquivos no batch.
     * @return Uma nova lista contendo até {@code tamanhoBatch} arquivos.
     */
    public static List<File> gerarBatchArquivos(List<File> arquivos, int tamanhoBatch) {
        try {
            int limite = Math.min(arquivos.size(), tamanhoBatch);
            List<File> batch = new ArrayList<>(arquivos.subList(0, limite));
            arquivos.subList(0, limite).clear();
            return batch;
        }catch (Exception e){
            e.printStackTrace();
            throw new FileException("Não foi possível gerar batch de arquivos" + e);
        }
    }
}
