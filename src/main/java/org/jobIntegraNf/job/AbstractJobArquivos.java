package org.jobIntegraNf.job;

import java.io.File;
import java.util.List;

import org.jobIntegraNf.exception.ExecutaJobException;
import org.jobIntegraNf.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Template de execução para jobs baseados em processamento de arquivos em lotes.
 *
 * <p>Define o fluxo: obter arquivos, gerar batches, processar e executar ação
 * pós-sucesso (ex.: mover ou deletar).</p>
 */
public abstract class AbstractJobArquivos {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Executa o fluxo do job em loop até que não haja mais arquivos.
     * @throws ExecutaJobException em caso de erro inesperado.
     */
    public void executar() {
        try {
            List<File> arquivos = obterArquivos();
            while (!arquivos.isEmpty()) {
                List<File> batch = FileUtil.gerarBatchArquivos(arquivos, this.tamanhoBatch());
                boolean sucesso = this.processarBatch(batch);
                if (sucesso) {
                    this.aposSucesso(batch);
                }
            }
        } catch (Exception e) {
            log.error("Erro ao executar {}", getClass().getSimpleName(), e);
            throw new ExecutaJobException("Não foi possível executar " + getClass().getSimpleName(), e);
        }
    }

    /**
     * Obtém a lista de arquivos para processamento.
     */
    protected abstract List<File> obterArquivos();

    /**
     * Define o tamanho do lote a ser processado; padrão 1000.
     */
    protected int tamanhoBatch() { return 1000; }
    
    /**
     * Processa um lote de arquivos retornando sucesso/fracasso.
     */
    protected abstract boolean processarBatch(List<File> arquivosBatch);

    /**
     * Ação a ser executada após processamento bem-sucedido (ex.: mover arquivos).
     */
    protected abstract void aposSucesso(List<File> arquivosBatch);
}

