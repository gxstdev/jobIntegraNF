import org.jobIntegraNf.util.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilsTest {
    @Test
    void testExtrairCodigosNFs() {
        File f1 = new File("nf_1001.txt");
        File f2 = new File("nf_1002.txt");
        List<File> arquivos = List.of(f1, f2);

        List<Long> codigos = FileUtil.extrairCodigosNFs(arquivos);

        assertEquals(2, codigos.size());
        assertTrue(codigos.contains(1001L));
        assertTrue(codigos.contains(1002L));
    }
    @Test
    void testExtrairCodigosNFsComArquivoInvalido() {
        File f1 = new File("nf_1001.txt");
        File f2 = new File("arquivo_invalido.txt");
        List<File> arquivos = List.of(f1, f2);

        List<Long> codigos = FileUtil.extrairCodigosNFs(arquivos);

        // deve ignorar o inválido
        assertEquals(1, codigos.size());
        assertTrue(codigos.contains(1001L));
    }

    @Test
    void testMoverArquivos() throws IOException {
        Path srcDir = Files.createTempDirectory("pendentes");
        Path destDir = Files.createTempDirectory("processadas");

        File f1 = Files.createFile(srcDir.resolve("nf_1001.txt")).toFile();
        List<File> arquivos = List.of(f1);

        FileUtil.moverArquivos(arquivos, destDir.toString());

        assertFalse(f1.exists());
        assertTrue(Files.exists(destDir.resolve("nf_1001.txt")));
    }

    @Test
    void testValidarNomeArquivos(){
        File f1 = new File("nf__1001.txt");
        File f2 = new File("nf_invalido.txt");
        File f3 = new File("nf_4560.txt");

        List<File> arquivos = List.of(f1, f2, f3);

        List<File> arquivosValidos = FileUtil.validarNomeArquivos(arquivos);
        assertEquals(1, arquivosValidos.size());

        arquivos = new ArrayList<>();
        arquivosValidos = FileUtil.validarNomeArquivos(arquivos);

        assertEquals(0, arquivosValidos.size());
    }
}
