package org.jobIntegraNf.util;

import org.jobIntegraNf.exception.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static void moverArquivos(List<File> arquivos, String destino) {
        try {
            for (File arquivo : arquivos) {
                //para mover, precisa sempre ter o caminho + nome do arquivo
                Files.move(arquivo.toPath(), Path.of(destino).resolve(arquivo.getName()), StandardCopyOption.REPLACE_EXISTING);
                log.info("Movendo arquivo: {} - para: {}", arquivo.getName(), destino);
            }
        } catch (Exception e) {
            log.error("Não foi possível mover arquivos para o diretório: " + destino, e);
            throw new FileException("Não foi possível mover arquivos para o diretório: " + destino, e);
        }
    }

    /**
     * Gera um batch de até {@code tamanhoBatch} arquivos da lista fornecida.
     * Os arquivos retornados são removidos da lista original.
     *
     * @param arquivos     Lista original de arquivos (será modificada).
     * @param tamanhoBatch Número máximo de arquivos no batch.
     * @return Uma nova lista contendo até {@code tamanhoBatch} arquivos.
     */
    public static List<File> gerarBatchArquivos(List<File> arquivos, int tamanhoBatch) {
        try {
            int limite = Math.min(arquivos.size(), tamanhoBatch);

            List<File> batch = new ArrayList<>(arquivos.subList(0, limite));
            arquivos.subList(0, limite).clear();
            return batch;
        } catch (Exception e) {
            log.error("Não foi possível gerar batch de arquivos", e);
            throw new FileException("Não foi possível gerar batch de arquivos", e);
        }
    }

    public static void expurgarArquivos(List<File> arquivos){
        if (arquivos.isEmpty()) return;
        try {
            for (File arq : arquivos){
                arq.delete();
            }
        }catch (Exception e) {
            log.error("Não foi possível deletar arquivos", e);
            throw new FileException("Não foi possível deletar arquivos", e);
        }

    }
}
