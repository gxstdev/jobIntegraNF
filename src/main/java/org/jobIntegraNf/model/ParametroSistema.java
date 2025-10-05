package org.jobIntegraNf.model;

import jakarta.persistence.*;

@Entity(name = "TbParametroSistema")
@Table(name = "TB_PARAMETRO_SISTEMA")
public class ParametroSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ger_seq_param")
    @SequenceGenerator(name = "ger_seq_param", sequenceName = "seq_cd_parametro_sistema", allocationSize = 1)
    @Column(name = "CD_PARAMETRO_SISTEMA")
    private Long codigoPametroSistema;

    @Column(name = "TX_PARAMETRO")
    private String taxaParametro;

    @Column(name = "DS_PARAMETRO")
    private String descricaoParametro;

    public ParametroSistema() {
    }

    public ParametroSistema(String taxaParametro, String descricaoParametro) {
        this.taxaParametro = taxaParametro;
        this.descricaoParametro = descricaoParametro;
    }

    public Long getCodigoPametroSistema() {
        return codigoPametroSistema;
    }

    public void setCodigoPametroSistema(Long codigoPametroSistema) {
        this.codigoPametroSistema = codigoPametroSistema;
    }

    public String getTaxaParametro() {
        return taxaParametro;
    }

    public void setTaxaParametro(String taxaParametro) {
        this.taxaParametro = taxaParametro;
    }

    public String getDescricaoParametro() {
        return descricaoParametro;
    }

    public void setDescricaoParametro(String descricaoParametro) {
        this.descricaoParametro = descricaoParametro;
    }
}
