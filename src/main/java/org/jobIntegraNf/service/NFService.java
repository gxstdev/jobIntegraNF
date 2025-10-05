package org.jobIntegraNf.service;

import org.jobIntegraNf.model.NotaFiscal;

import java.io.File;
import java.util.List;

public interface NFService {
    void salvarNFs(List<NotaFiscal> nfs);
    List<NotaFiscal> buscarNFsPendentes();
    List<NotaFiscal> buscarNFsProcessadas();
    boolean atualizarStatusNF(Long codigoStatus, List<Long> cdNfs);
    boolean processarNFs(List<File> arquivosParaProcessar);
}
