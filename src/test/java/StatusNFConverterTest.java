import org.jobIntegraNf.enums.StatusNF;
import org.jobIntegraNf.enums.StatusNFConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusNFConverterTest {

    private final StatusNFConverter converter = new StatusNFConverter();

    @Test
    void convertToDatabaseColumn_deveRetornarCodigo() {
        assertEquals(1L, converter.convertToDatabaseColumn(StatusNF.NF_PENDENTE_PROCESSAMENTO), "O código deve ser 1");
        assertEquals(2L, converter.convertToDatabaseColumn(StatusNF.NF_PROCESSADA), "O código deve ser 2");
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void convertToEntityAttribute_deveRetornarEnum() {
        assertEquals(StatusNF.NF_PENDENTE_PROCESSAMENTO, converter.convertToEntityAttribute(1L), "O enum deve ser NF_PENDENTE_PROCESSAMENTO");
        assertEquals(StatusNF.NF_PROCESSADA, converter.convertToEntityAttribute(2L), "O enum deve ser NF_PROCESSADA");
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void convertToEntityAttribute_deveLancarExcecaoParaCodigoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute(999L));
    }
}



