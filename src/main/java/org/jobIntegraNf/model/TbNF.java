package org.jobIntegraNf.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "TB_NF")
public class TbNF {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ger_seq_nf")
    @SequenceGenerator(name = "ger_seq_nf", sequenceName = "seq_cd_nf", allocationSize = 1)
    @Column(name = "CD_NF")
    private Long codigoNF;

    @Column(name = "CD_STATUS", nullable = false)
    private Long codigoStatus;

    @Column(name = "DT_EMISSAO")
    private OffsetDateTime dataEmissao;

    @Column(name = "DT_PROCESSAMENTO")
    private OffsetDateTime dataProcessamento;

    public TbNF() {
    }

    public TbNF(Long codigoNF, Long codigoStatus, OffsetDateTime dataEmissao, OffsetDateTime dataProcessamento) {
        this.codigoNF = codigoNF;
        this.codigoStatus = codigoStatus;
        this.dataEmissao = dataEmissao;
        this.dataProcessamento = dataProcessamento;
    }

    public Long getCodigoNF() {
        return codigoNF;
    }

    public void setCodigoNF(Long codigoNF) {
        this.codigoNF = codigoNF;
    }

    public Long getCodigoStatus() {
        return codigoStatus;
    }

    public void setCodigoStatus(Long codigoStatus) {
        this.codigoStatus = codigoStatus;
    }

    public OffsetDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(OffsetDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public OffsetDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(OffsetDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @Override
    public String toString() {
        return  "TbNF |" +
                "codigoNF |" + getCodigoNF() +
                "codigoStatus |" + getCodigoStatus() +
                "dataEmissao |" + getDataEmissao() +
                "dataProcessamento |" + getDataProcessamento();
    }
}

