import org.jobIntegraNf.util.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilsTest {
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
    void testGerarBatchArquivos() throws IOException {
        Path dir = Files.createTempDirectory("lote");
        File f1 = Files.createFile(dir.resolve("nf_1.txt")).toFile();
        File f2 = Files.createFile(dir.resolve("nf_2.txt")).toFile();
        File f3 = Files.createFile(dir.resolve("nf_3.txt")).toFile();

        List<File> original = new java.util.ArrayList<>(List.of(f1, f2, f3));

        List<File> batch = FileUtil.gerarBatchArquivos(original, 2);

        assertEquals(2, batch.size(), "O tamanho do batch deve ser 2");
        assertEquals(1, original.size(), "O tamanho da lista original deve ser 1");
        assertEquals(f3, original.get(0), "O primeiro arquivo da lista original deve ser nf_3.txt");
    }

    @Test
    void testExpurgarArquivos() throws IOException {
        Path dir = Files.createTempDirectory("expurgo");
        File f1 = Files.createFile(dir.resolve("nf_10.txt")).toFile();
        File f2 = Files.createFile(dir.resolve("nf_11.txt")).toFile();

        FileUtil.expurgarArquivos(List.of(f1, f2));

        assertFalse(f1.exists());
        assertFalse(f2.exists());
    }
}
