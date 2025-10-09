package org.jobIntegraNf.enums;

/**
 * Enumeração de chaves de parâmetros do sistema utilizadas para configurar
 * diretórios, lotes de processamento e SMTP.
 */
public enum Parametros {

    DIRETORIO_NFS_PENDENTES("dirNFsPendentes"),
    DIRETORIO_NFS_PROCESSADA("dirNFsProcessadas"),
    DIRETORIO_NFS_EXPURGADAS("dirNFsExpurgadas"),
    DIRETORIO_NFS_EM_ENVIO("dirNFsEmEnvio"),
    JOB_LOTE_PROCESSA("job.lote.processa"),
    JOB_LOTE_EMAIL("job.lote.email"),
    JOB_LOTE_EXPURGO("job.lote.expurgo"),
    EMAIL_ENVIO_DIURNO("emailEnvioDiurno"),
    EMAIL_ENVIO_NOTURNO("emailEnvioNoturno"),
    SMTP_HOST("smtp.host"),
    SMTP_PORT("smtp.port"),
    SMTP_PASSWORD("smtp.password"),
    SMTP_USERNAME("smtp.username");

    String descricaoParametro;

    Parametros(String descricaoParametro){
        this.descricaoParametro = descricaoParametro;
    }

    /**
     * @return chave descritiva do parâmetro no banco de dados.
     */
    public String getDescricaoParametro() {
        return descricaoParametro;
    }
}

