package org.jobIntegraNf.util;

import org.jobIntegraNf.model.NotaFiscal;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class NFUtil {
    public static List<NotaFiscal> buildNFsList(){
        List<NotaFiscal> nfs = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            NotaFiscal nf = new NotaFiscal(1L, OffsetDateTime.now(), null);
            nfs.add(nf);
        }
        return nfs;
    }
}
