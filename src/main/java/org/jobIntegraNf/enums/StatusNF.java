package org.jobIntegraNf.enums;

/**
 * Status possíveis de uma Nota Fiscal no fluxo de processamento.
 */
public enum StatusNF {
    NF_PENDENTE_PROCESSAMENTO(1L),
    NF_PROCESSADA(2L);

    Long codigoStatus;

    StatusNF(Long codigoStatus){
        this.codigoStatus = codigoStatus;
    }

    /**
     * @return código persistido no banco correspondente ao status.
     */
    public Long getCodigoStatus() {
        return codigoStatus;
    }
}
