package org.jobIntegraNf.enums;

public enum Parametros {

    DIRETORIO_NFS_PENDENTES("dirNFsPendentes"),
    DIRETORIO_NFS_PROCESSADA("dirNFsProcessadas"),
    EMAIL_ENVIO_DIURNO("emailEnvioDiurno"),
    EMAIL_ENVIO_NOTURNO("emailEnvioNoturno");

    String descricaoParametro;

    Parametros(String descricaoParametro){
        this.descricaoParametro = descricaoParametro;
    }

    public String getDescricaoParametro() {
        return descricaoParametro;
    }
}
