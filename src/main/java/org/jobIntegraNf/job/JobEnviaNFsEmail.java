package org.jobIntegraNf.job;

import org.jobIntegraNf.dao.ParametroSistemaDAO;
import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.model.TbParametroSistema;
import org.jobIntegraNf.service.EmailService;
import org.jobIntegraNf.service.impl.GmailEmailServiceImpl;
import org.jobIntegraNf.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JobEnviaNFsEmail {
    private static final ParametroSistemaDAO parametroSistemaDAO = new ParametroSistemaDAO(TbParametroSistema.class);

    public static void executar() {
        try {
            List<File> arquivos = FileUtils.getNFsTxtProcessadas();

            EmailService emailService = new GmailEmailServiceImpl("smtp.gmail.com",587,
                    "gxstdev@gmail.com", "qfwb juff qvmy kmwf", true);

            EmailMessage msg = new EmailMessage("gxstdev@gmail.com", List.of("gabrielaxavierst@gmail.com"),
                    "TESTE", "teste", new ArrayList<>());

            emailService.sendEmail(msg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
