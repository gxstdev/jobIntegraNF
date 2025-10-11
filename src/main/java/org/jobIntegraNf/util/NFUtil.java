package org.jobIntegraNf.util;

import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.model.NotaFiscal;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitários para criação e manipulação de entidades de Nota Fiscal
 * em cenários de teste e bootstrap.
 */
public class NFUtil {
    /**
     * Gera uma lista de 100 {@link NotaFiscal} com status pendente e
     * data de emissão igual ao momento atual.
     *
     * @return lista de notas fiscais simuladas.
     */
    public static List<NotaFiscal> buildNFsList(){
        List<NotaFiscal> nfs = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            NotaFiscal nf = new NotaFiscal(StatusNF.NF_PENDENTE_PROCESSAMENTO, OffsetDateTime.now(), null);
            nfs.add(nf);
        }
        return nfs;
    }
}
