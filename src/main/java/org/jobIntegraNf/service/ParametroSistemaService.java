package org.jobIntegraNf.service;

public interface ParametroSistemaService {
    public String getDirPendentes();
    public String getDirProcessadas();
    public String getDirExpurgadas();
    public String findByDescricaoParametro(String descricaoParametro);
}
