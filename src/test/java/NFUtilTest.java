import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.model.NotaFiscal;
import org.jobIntegraNf.util.NFUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NFUtilTest {

    @Test
    void buildNFsList_deveGerar100Pendentes() {
        List<NotaFiscal> lista = NFUtil.buildNFsList();

        assertEquals(100, lista.size(), "Quantidade de NFs deveria ser 100");
        assertTrue(lista.stream().allMatch(nf -> nf.getStatus() == StatusNF.NF_PENDENTE_PROCESSAMENTO));
        assertTrue(lista.stream().allMatch(nf -> nf.getDataEmissao() != null));
        assertTrue(lista.stream().allMatch(nf -> nf.getDataProcessamento() == null));
    }
}


