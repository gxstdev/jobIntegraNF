package org.jobIntegraNf.service;

/**
 * Serviço de leitura de parâmetros de sistema armazenados em banco.
 */
public interface ParametroSistemaService {
    /**
     * @return diretório configurado para NFs pendentes.
     */
    public String getDirPendentes();
    /**
     * @return diretório configurado para NFs processadas.
     */
    public String getDirProcessadas();
    /**
     * @return diretório configurado para NFs expurgadas.
     */
    public String getDirExpurgadas();
    /**
     * @return diretório configurado para NFs em envio.
     */
    public String getDirEmEnvio();
    /**
     * @return tamanho de lote para processamento de NFs.
     */
    public int getBatchProcessa();
    /**
     * @return tamanho de lote para envio de e-mails.
     */
    public int getBatchEmail();
    /**
     * @return tamanho de lote para expurgo de arquivos.
     */
    public int getBatchExpurgo();
    /**
     * Retorna o e-mail de destinatário conforme janela de horário
     * (ex.: diurno/noturno).
     * @return endereço do destinatário.
     */
    public String getDestinatario();
    /**
     * Busca o valor do parâmetro pela descrição.
     * @param descricaoParametro chave do parâmetro.
     * @return valor do parâmetro.
     */
    public String findByDescricaoParametro(String descricaoParametro);
}
