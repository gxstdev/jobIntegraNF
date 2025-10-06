package org.jobIntegraNf.service;

import java.io.File;
import java.util.List;

public interface EmailService {
    boolean enviarNFsProcessadas(List<File> anexos);
}