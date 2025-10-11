import org.jobIntegraNf.job.JobExpurgo;
import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobExpurgoTest {

    @Test
    void executaEmDoisBatches() {
        ArquivoNFService arquivoNFService = mock(ArquivoNFService.class);
        ParametroSistemaService parametroSistemaService = mock(ParametroSistemaService.class);

        when(parametroSistemaService.getBatchExpurgo()).thenReturn(2);
        List<File> arquivos = new ArrayList<>(List.of(new File("p1.txt"), new File("p2.txt"), new File("p3.txt")));
        when(arquivoNFService.getNFsTxtExpurgadas()).thenReturn(arquivos);

        class JobSpy extends JobExpurgo {
            final List<List<File>> batches = new ArrayList<>();
            JobSpy(ArquivoNFService a, ParametroSistemaService p) { super(a, p); }
            @Override protected boolean processarBatch(List<File> arquivosBatch) {
                batches.add(new ArrayList<>(arquivosBatch));
                return true;
            }
        }

        JobSpy job = new JobSpy(arquivoNFService, parametroSistemaService);
        job.executar();

        assertEquals(2, job.batches.size(), "Deve processar 2 batches");
        assertEquals(2, job.batches.get(0).size(), "Primeiro batch deve ter 2 arquivos");
        assertEquals(1, job.batches.get(1).size(), "Segundo batch deve ter 1 arquivo");
    }

    @Test
    void listaVaziaNaoProcessa() {
        ArquivoNFService arquivoNFService = mock(ArquivoNFService.class);
        ParametroSistemaService parametroSistemaService = mock(ParametroSistemaService.class);

        when(arquivoNFService.getNFsTxtExpurgadas()).thenReturn(new ArrayList<>());

        JobExpurgo job = new JobExpurgo(arquivoNFService, parametroSistemaService);
        job.executar();

        // Sem batches capturados, pois não há arquivos a expurgar
        verify(arquivoNFService, times(1)).getNFsTxtExpurgadas();
    }
}




