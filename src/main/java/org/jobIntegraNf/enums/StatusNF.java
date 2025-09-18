package org.jobIntegraNf.enums;

public enum StatusNF {
    NF_PENDENTE_PROCESSAMENTO(1L),
    NF_PROCESSADA(2L);

    Long codigoStatus;

    StatusNF(Long codigoStatus){
        this.codigoStatus = codigoStatus;
    }

    public Long getCodigoStatus() {
        return codigoStatus;
    }
}
