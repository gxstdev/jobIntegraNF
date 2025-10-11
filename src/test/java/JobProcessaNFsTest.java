import org.jobIntegraNf.job.JobProcessaNFs;
import org.jobIntegraNf.service.ArquivoNFService;
import org.jobIntegraNf.service.NFService;
import org.jobIntegraNf.service.ParametroSistemaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InOrder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobProcessaNFsTest {

    @Test
    void processaEmDoisBatchesComSucesso() {
        ArquivoNFService arquivoNFService = mock(ArquivoNFService.class);
        NFService nfService = mock(NFService.class);
        ParametroSistemaService parametroSistemaService = mock(ParametroSistemaService.class);

        List<File> arquivos = new ArrayList<>(List.of(new File("a1.txt"), new File("a2.txt"), new File("a3.txt")));

        when(arquivoNFService.getNFsTxtPendentes()).thenReturn(arquivos);
        when(parametroSistemaService.getBatchProcessa()).thenReturn(2);
        when(parametroSistemaService.getDirProcessadas()).thenReturn("/dest");
        when(nfService.processarNFs(Mockito.anyList())).thenReturn(true);

        class JobSpy extends JobProcessaNFs {
            private final ParametroSistemaService paramRef;

            final List<List<File>> aposSucessoBatches = new ArrayList<>();

            String destinoUsado;

            JobSpy(NFService n, ArquivoNFService a, ParametroSistemaService p) { 
                super(n, a, p); 
                this.paramRef = p; 
            }

            @Override 
            protected void aposSucesso(List<File> arquivosBatch) {
                aposSucessoBatches.add(new ArrayList<>(arquivosBatch));
                destinoUsado = paramRef.getDirProcessadas();
            }
        }
        
        JobSpy job = new JobSpy(nfService, arquivoNFService, parametroSistemaService);

        job.executar();

        InOrder inOrder = inOrder(nfService);
        inOrder.verify(nfService).processarNFs(argThat(l -> l != null && l.size() == 2));
        inOrder.verify(nfService).processarNFs(argThat(l -> l != null && l.size() == 1));

        assertEquals(2, job.aposSucessoBatches.size(), "aposSucesso deve ser chamado duas vezes");
        assertEquals("/dest", job.destinoUsado, "Diretório de destino deve vir de ParametroSistemaService");
    }

    @Test
    void naoChamaAposSucessoQuandoFalha() {
        ArquivoNFService arquivoNFService = mock(ArquivoNFService.class);
        NFService nfService = mock(NFService.class);
        ParametroSistemaService parametroSistemaService = mock(ParametroSistemaService.class);

        List<File> arquivos = new ArrayList<>(List.of(new File("a1.txt"), new File("a2.txt"), new File("a3.txt")));

        when(arquivoNFService.getNFsTxtPendentes()).thenReturn(arquivos);
        when(parametroSistemaService.getBatchProcessa()).thenReturn(2);
        when(nfService.processarNFs(Mockito.anyList())).thenReturn(false).thenReturn(true);

        class JobSpy extends JobProcessaNFs {
            final List<List<File>> aposSucessoBatches = new ArrayList<>();

            JobSpy(NFService n, ArquivoNFService a, ParametroSistemaService p) { 
                super(n, a, p); 
            }
            
            @Override 
            protected void aposSucesso(List<File> arquivosBatch) { 
                aposSucessoBatches.add(new ArrayList<>(arquivosBatch)); 
            }
        }

        JobSpy job = new JobSpy(nfService, arquivoNFService, parametroSistemaService);

        job.executar();

        InOrder inOrder = inOrder(nfService);
        inOrder.verify(nfService).processarNFs(argThat(l -> l != null && l.size() == 2));
        inOrder.verify(nfService).processarNFs(argThat(l -> l != null && l.size() == 1));
        assertEquals(1, job.aposSucessoBatches.size(), "aposSucesso deve ser chamado apenas uma vez (para batch com sucesso)");
        assertEquals(1, job.aposSucessoBatches.get(0).size(), "Batch com sucesso deve ter 1 arquivo");
    }

    @Test
    void listaVaziaNaoProcessa() {
        ArquivoNFService arquivoNFService = mock(ArquivoNFService.class);
        NFService nfService = mock(NFService.class);
        ParametroSistemaService parametroSistemaService = mock(ParametroSistemaService.class);
        when(arquivoNFService.getNFsTxtPendentes()).thenReturn(new ArrayList<>());

        JobProcessaNFs job = new JobProcessaNFs(nfService, arquivoNFService, parametroSistemaService);
        job.executar();

        verify(nfService, never()).processarNFs(Mockito.anyList());
    }
}


