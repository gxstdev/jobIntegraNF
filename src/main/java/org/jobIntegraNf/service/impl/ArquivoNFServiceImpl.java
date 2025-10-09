package org.jobIntegraNf.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jobIntegraNf.exception.FileException;
import org.jobIntegraNf.model.NotaFiscal;
import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementação de {@link org.jobIntegraNf.service.ArquivoNFService} para
 * manipulação de arquivos de Notas Fiscais em diretórios configurados.
 */
public class ArquivoNFServiceImpl implements ArquivoNFService {
    private final Logger log = LoggerFactory.getLogger(ArquivoNFServiceImpl.class); 

    private String DIRETORIO_NF_PENDENTES;
    private String DIRETORIO_NF_PROCESSADAS;
    private String DIRETORIO_NF_EXPURGADAS;
    private String DIRETORIO_NF_EM_ENVIO;

    private final ParametroSistemaService parametroSistemaService;

    public ArquivoNFServiceImpl(ParametroSistemaService parametroSistemaService) {
        this.parametroSistemaService = parametroSistemaService;
        this.DIRETORIO_NF_PENDENTES = parametroSistemaService.getDirPendentes();
        this.DIRETORIO_NF_PROCESSADAS = parametroSistemaService.getDirProcessadas();
        this.DIRETORIO_NF_EXPURGADAS = parametroSistemaService.getDirExpurgadas();
        this.DIRETORIO_NF_EM_ENVIO = parametroSistemaService.getDirEmEnvio();
    }

    /** {@inheritDoc} */
    public String gerarNomeArquivo(NotaFiscal nf) {
        return String.format("nf_%d.txt", nf.getCodigoNF());
    }

    /** {@inheritDoc} */
    public List<File> validarNomeArquivos(List<File> arquivosParaProcessar) {
        List<File> arquivosInvalidos = new ArrayList<>();

        List<File> arquivosValidos = arquivosParaProcessar.stream().filter(arquivo -> {
            String[] splNomeArquivo = arquivo.getName().split("_");
            if (splNomeArquivo.length >= 2) {
                String cdNF = splNomeArquivo[1].replace(".txt", "");
                try {
                    Long.parseLong(cdNF);
                    return true;
                } catch (NumberFormatException e) {
                    arquivosInvalidos.add(arquivo);
                    return false;
                }
            }
            arquivosInvalidos.add(arquivo);
            return false;
        }).collect(Collectors.toList());

        if (!arquivosInvalidos.isEmpty()) {
            log.warn("Os arquivos {} possuem nomes inválidos, não podem ser processados", arquivosInvalidos);
        }
        return arquivosValidos;
    }

    public void gerarNFTxt(NotaFiscal nf) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(String.valueOf(Path.of(DIRETORIO_NF_PENDENTES).resolve(gerarNomeArquivo(nf)))))) {
            bw.write(nf.toString());
        } catch (IOException e) {
            String errorMsg = String.format("Erro ao gerar arquivo TXT da NF: %d", nf.getCodigoNF());
            log.error(errorMsg, e);
            throw new FileException(errorMsg, e);
        }
    }

    public List<Long> extrairCodigosNFs(List<File> arquivosParaProcessar) {
        return validarNomeArquivos(arquivosParaProcessar)
                .stream()
                .map(arq -> Long.valueOf(arq.getName().split("_")[1].replace(".txt", "")))
                .collect(Collectors.toList());
    }

    public List<File> getNFsTxtPendentes() {
        File[] files = new File(DIRETORIO_NF_PENDENTES).listFiles(File::isFile);
        return (files != null) ? new ArrayList<>(Arrays.asList(files)) : new ArrayList<>();
    }

    public List<File> getNFsTxtProcessadas() {
        File[] files = new File(DIRETORIO_NF_PROCESSADAS).listFiles(File::isFile);
        return ((files != null && files.length > 0) ? new ArrayList<>(Arrays.asList(files)) : new ArrayList<>());
    }

    public List<File> getNFsTxtExpurgadas() {
        File[] files = new File(DIRETORIO_NF_EXPURGADAS).listFiles(File::isFile);
        return ((files != null && files.length > 0) ? new ArrayList<>(Arrays.asList(files)) : new ArrayList<>());
    }
}
