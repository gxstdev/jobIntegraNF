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
}
