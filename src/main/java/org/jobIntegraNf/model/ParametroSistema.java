package org.jobIntegraNf.model;

import jakarta.persistence.*;

/**
 * Entidade que representa parâmetros de configuração do sistema,
 * armazenados em banco de dados.
 */
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

    /**
     * Construtor padrão exigido pelo JPA.
     */
    public ParametroSistema() {
    }

    /**
     * Constrói uma instância com os valores informados.
     *
     * @param taxaParametro     valor do parâmetro.
     * @param descricaoParametro descrição identificadora do parâmetro.
     */
    public ParametroSistema(String taxaParametro, String descricaoParametro) {
        this.taxaParametro = taxaParametro;
        this.descricaoParametro = descricaoParametro;
    }

    /**
     * @return código identificador do parâmetro.
     */
    public Long getCodigoPametroSistema() {
        return this.codigoPametroSistema;
    }

    /**
     * @param codigoPametroSistema código identificador do parâmetro.
     */
    public void setCodigoPametroSistema(Long codigoPametroSistema) {
        this.codigoPametroSistema = codigoPametroSistema;
    }

    /**
     * @return valor do parâmetro.
     */
    public String getTaxaParametro() {
        return this.taxaParametro;
    }

    /**
     * @param taxaParametro valor do parâmetro.
     */
    public void setTaxaParametro(String taxaParametro) {
        this.taxaParametro = taxaParametro;
    }

    /**
     * @return descrição identificadora do parâmetro.
     */
    public String getDescricaoParametro() {
        return this.descricaoParametro;
    }

    /**
     * @param descricaoParametro descrição identificadora do parâmetro.
     */
    public void setDescricaoParametro(String descricaoParametro) {
        this.descricaoParametro = descricaoParametro;
    }
}
