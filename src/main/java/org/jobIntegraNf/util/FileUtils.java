package org.jobIntegraNf.util;

import org.jobIntegraNf.exception.FileException;
import org.jobIntegraNf.model.TbNF;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtils {

    public static void gerarNFTxt(TbNF nf, String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho + gerarNomeArquivo(nf)))) {
                bw.write(nf.toString());
        } catch (IOException e) {
            String errorMsg = String.format("Erro ao gerar arquivo TXT da NF: %d. Caused by %s",
                    nf.getCodigoNF() , e.getMessage());
            throw new FileException(errorMsg + e.getMessage());
        }
    }

    private static String gerarNomeArquivo(TbNF nf){
        return String.format("nf_%d.txt", nf.getCodigoNF());
    }
}
