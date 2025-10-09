package org.jobIntegraNf.service;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.model.NotaFiscal;

/**
 * Serviço responsável por operações relativas a arquivos de NFs,
 * como geração, validação de nomes e recuperação por diretórios.
 */
public interface ArquivoNFService {
    /**
     * Gera o nome de arquivo padrão da NF.
     * @param nf nota fiscal.
     * @return nome do arquivo a ser persistido.
     */
    public String gerarNomeArquivo(NotaFiscal nf);

    /**
     * Filtra e retorna apenas arquivos com nomes válidos para processamento.
     * @param arquivosParaProcessar arquivos candidatos.
     * @return lista contendo somente arquivos válidos.
     */
    public List<File> validarNomeArquivos(List<File> arquivosParaProcessar);

    /**
     * Gera o arquivo .txt correspondente à NF no diretório configurado.
     * @param nf nota fiscal.
     */
    public void gerarNFTxt(NotaFiscal nf);

    /**
     * Extrai os códigos de NF presentes nos nomes dos arquivos válidos.
     * @param arquivosParaProcessar arquivos candidatos.
     * @return lista de códigos de NF extraídos.
     */
    public List<Long> extrairCodigosNFs(List<File> arquivosParaProcessar);

    /**
     * @return lista de arquivos pendentes.
     */
    public List<File> getNFsTxtPendentes();

    /**
     * @return lista de arquivos processados.
     */
    public List<File> getNFsTxtProcessadas();

    /**
     * @return lista de arquivos expurgados (para deleção).
     */
    public List<File> getNFsTxtExpurgadas();

    /**
     * @return lista de arquivos em envio (fila de e-mail).
     */
    public List<File> getNFsTxtEmEnvio();
} 
