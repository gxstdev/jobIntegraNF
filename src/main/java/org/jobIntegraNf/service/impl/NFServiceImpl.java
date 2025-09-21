package org.jobIntegraNf.service.impl;

import org.jobIntegraNf.dao.NFDAO;
import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.model.TbNF;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.util.FileUtils;
import org.jobIntegraNf.util.JPAUtil;

import java.util.List;

public class NFServiceImpl implements NFService {

    private final NFDAO nfDAO = new NFDAO(TbNF.class, JPAUtil.getEntityManager());

    @Override
    public void salvarNFs(List<TbNF> nfs) {
        if (nfs.isEmpty()) return;
        for (TbNF nf : nfs){
            nfDAO.salvar(nf);
            FileUtils.gerarNFTxt(nf);
            nfDAO.getEm().clear();
        }
    }

    @Override
    public List<TbNF> buscarNFsPendentes() {
        return nfDAO.findByStatus(StatusNF.NF_PENDENTE_PROCESSAMENTO.getCodigoStatus());
    }

    @Override
    public List<TbNF> buscarNFsProcessadas() {
        return nfDAO.findByStatus(StatusNF.NF_PROCESSADA.getCodigoStatus());
    }

    public boolean atualizarStatusNF(Long codigoStatus, List<Long> cdNfs) {
       return nfDAO.updateStatusNF(codigoStatus, cdNfs);
    }
}
