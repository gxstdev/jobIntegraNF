package org.jobIntegraNf.service;

import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.model.NotaFiscal;

import java.io.File;
import java.util.List;

/**
 * Serviço de regras de negócio de Notas Fiscais (persistência e processamento).
 */
public interface NFService {
    /**
     * Persiste a lista de NFs e gera seus arquivos .txt correspondentes.
     * @param nfs lista de NFs a salvar.
     */
    void salvarNFs(List<NotaFiscal> nfs);

    /**
     * @return NFs com status pendente de processamento.
     */
    List<NotaFiscal> buscarNFsPendentes();

    /**
     * @return NFs com status processada.
     */
    List<NotaFiscal> buscarNFsProcessadas();

    /**
     * Atualiza o status de um conjunto de NFs.
     * @param status novo status.
     * @param cdNfs códigos das NFs para atualização.
     * @return true se houve atualização, false caso contrário.
     */
    boolean atualizarStatusNF(StatusNF status, List<Long> cdNfs);

    /**
     * Processa os arquivos e atualiza suas NFs para status processado.
     * @param arquivosParaProcessar arquivos candidatos.
     * @return true em caso de sucesso.
     */
    boolean processarNFs(List<File> arquivosParaProcessar);
}
