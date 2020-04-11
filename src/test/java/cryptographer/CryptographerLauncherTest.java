package cryptographer;

import org.junit.jupiter.api.Test;
import com.google.common.io.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class CryptographerLauncherTest {
    String sep = System.getProperty("file.separator");

    @Test
    void main() throws IOException {
        String[] args = {"-c", "a", "./src/test/resources/output/newCodeText1.txt",  "./src/test/resources/input/Text1.txt"};
        CryptographerLauncher.main(args);
        String[] args1 = {"-d", "a", "./src/test/resources/output/newDecodeText1.txt", "./src/test/resources/output/newCodeText1.txt"};
        CryptographerLauncher.main(args1);
        File in = new  File("./src/test/resources/input/Text1.txt");
        File out = new File("./src/test/resources/output/newDecodeText1.txt");

        assertEquals(in, out);
    }
}