package org.jobIntegraNf.enums;

public enum Parametros {

    DIRETORIO_NFS_PENDENTES("dirNFsPendentes"),
    DIRETORIO_NFS_PROCESSADA("dirNFsProcessadas"),
    DIRETORIO_NFS_EXPURGADAS("dirNFsExpurgadas"),
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

    public String getDescricaoParametro() {
        return descricaoParametro;
    }
}
