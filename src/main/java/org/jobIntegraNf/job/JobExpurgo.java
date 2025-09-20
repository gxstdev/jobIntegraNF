package org.jobIntegraNf.job;

import java.io.File;

public class JobExpurgo {
    public void executar() {
        System.out.println("Processando NFs...");
        File f = new File("");
        f.delete();
    }
}
