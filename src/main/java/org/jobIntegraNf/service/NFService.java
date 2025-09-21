package org.jobIntegraNf.service;

import org.jobIntegraNf.model.TbNF;

import java.io.File;
import java.util.List;

public interface NFService {
    void salvarNFs(List<TbNF> nfs);
    List<TbNF> buscarNFsPendentes();
    List<TbNF> buscarNFsProcessadas();
    boolean atualizarStatusNF(Long codigoStatus, List<Long> cdNfs);
    boolean processarNFs(List<File> arquivosParaProcessar);
}
