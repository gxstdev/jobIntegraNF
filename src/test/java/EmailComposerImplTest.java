import org.jobIntegraNf.composer.impl.EmailComposerImpl;
import org.jobIntegraNf.dto.EmailMessage;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmailComposerImplTest {

    @Test
    void composeNFsProcessadas_deveMontarMensagem() throws IOException {
        ParametroSistemaService parametroService = Mockito.mock(ParametroSistemaService.class);
        Mockito.when(parametroService.getDestinatario()).thenReturn("destinatario@teste.com");

        EmailComposerImpl composer = new EmailComposerImpl(parametroService);

        File anex1 = Files.createTempFile("nf_anexo_", ".txt").toFile();
        File anex2 = Files.createTempFile("nf_anexo_", ".txt").toFile();

        EmailMessage msg = composer.composeNFsProcessadas(List.of(anex1, anex2));


        assertEquals("destinatario@teste.com", msg.getTo(), "O destinatário deve ser destinatario@teste.com");
        assertTrue(msg.getSubject().startsWith("NOTAS FISCAIS - "), "O assunto deve começar com NOTAS FISCAIS - ");
        assertEquals("Segue anexo NFs já processadas.", msg.getBody(), "O corpo da mensagem deve ser Segue anexo NFs já processadas.");
        assertEquals(2, msg.getAttachments().size());
    }
}



