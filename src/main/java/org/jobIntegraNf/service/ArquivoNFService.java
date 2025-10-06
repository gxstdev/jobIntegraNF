package org.jobIntegraNf.service;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.model.NotaFiscal;


public interface ArquivoNFService {
    public String gerarNomeArquivo(NotaFiscal nf);
    public List<File> validarNomeArquivos(List<File> arquivosParaProcessar);
    public void gerarNFTxt(NotaFiscal nf);
    public List<Long> extrairCodigosNFs(List<File> arquivosParaProcessar);
    public List<File> getNFsTxtPendentes();
    public List<File> getNFsTxtProcessadas();
    public List<File> getNFsTxtExpurgadas();
} 
