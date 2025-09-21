package org.jobIntegraNf.util;

import org.jobIntegraNf.model.TbNF;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class NFUtil {
    public static List<TbNF> buildNFsList(){
        List<TbNF> nfs = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            TbNF nf = new TbNF(1L, OffsetDateTime.now(), null);
            nfs.add(nf);
        }
        return nfs;
    }
}
