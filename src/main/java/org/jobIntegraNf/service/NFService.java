package org.jobIntegraNf.service;

import org.jobIntegraNf.model.TbNF;

import java.util.List;

public interface NFService {
    void salvarNFs(List<TbNF> nfs);
    List<TbNF> buscarNFsPendentes();
    List<TbNF> buscarNFsProcessadas();
}
