package org.jobIntegraNf.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.enums.StatusNFConverter;

/**
 * Entidade que representa a Nota Fiscal processada pelo sistema de jobs.
 */
@Entity(name = "TbNF")
@Table(name = "TB_NF")
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ger_seq_nf")
    @SequenceGenerator(name = "ger_seq_nf", sequenceName = "seq_cd_nf", allocationSize = 1)
    @Column(name = "CD_NF")
    private Long codigoNF;

    @Convert(converter = StatusNFConverter.class)
    @Column(name = "CD_STATUS", nullable = false)
    private StatusNF status;

    @Column(name = "DT_EMISSAO")
    private OffsetDateTime dataEmissao;

    @Column(name = "DT_PROCESSAMENTO")
    private OffsetDateTime dataProcessamento;

    /**
     * Construtor padrão exigido pelo JPA.
     */
    public NotaFiscal() {
    }

    /**
     * Constrói uma nova instância de Nota Fiscal.
     *
     * @param status             status atual da nota fiscal.
     * @param dataEmissao        data/hora de emissão.
     * @param dataProcessamento  data/hora de processamento (pode ser nula).
     */
    public NotaFiscal(StatusNF status, OffsetDateTime dataEmissao, OffsetDateTime dataProcessamento) {
        this.status = status;
        this.dataEmissao = dataEmissao;
        this.dataProcessamento = dataProcessamento;
    }

    /**
     * @return código identificador da nota fiscal.
     */
    public Long getCodigoNF() {
        return this.codigoNF;
    }

    /**
     * @param codigoNF código identificador da nota fiscal.
     */
    public void setCodigoNF(Long codigoNF) {
        this.codigoNF = codigoNF;
    }

    /**
     * @return status atual da nota fiscal.
     */
    public StatusNF getStatus() {
        return this.status;
    }

    /**
     * @param status novo status da nota fiscal.
     */
    public void setStatus(StatusNF status) {
        this.status = status;
    }

    /**
     * @return data/hora de emissão da nota fiscal.
     */
    public OffsetDateTime getDataEmissao() {
        return this.dataEmissao;
    }

    /**
     * @param dataEmissao data/hora de emissão.
     */
    public void setDataEmissao(OffsetDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    /**
     * @return data/hora de processamento da nota fiscal.
     */
    public OffsetDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    /**
     * @param dataProcessamento data/hora de processamento (pode ser nula).
     */
    public void setDataProcessamento(OffsetDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @Override
    public String toString() {
        return String.format("NF\nCódigo Identificação: %d\nStatus: %s\nData Emissão: %s",
                getCodigoNF(), getStatus(), getDataEmissao());
    }
}

