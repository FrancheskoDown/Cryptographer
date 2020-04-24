package cryptographer;

import com.google.common.io.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


class CryptographerLauncherTest {

    @Test
    void basicTest() throws IOException {
        String[] args = {"-c", "A", "-o", "newCodeText1",  "./src/test/resources/input/Text1"};
        CryptographerLauncher.main(args);
        String[] args1 = {"-d", "A", "-o", "newDecodeText1", "./output/newCodeText1.txt"};
        CryptographerLauncher.main(args1);

        File in = new  File("./src/test/resources/input/Text1");
        File out = new File("./output/newDecodeText1");

        assertTrue(Files.equal(in, out));

        args = new String[]{"-c", "123CB", "-o", "newCodeText2",  "./src/test/resources/input/Text2"};
        CryptographerLauncher.main(args);
        args1 = new String[]{"-d", "123CB", "-o", "newDecodeText2", "./output/newCodeText2.txt"};
        CryptographerLauncher.main(args1);

        in = new  File("./src/test/resources/input/Text2");
        out = new File("./output/newDecodeText2");

        assertTrue(Files.equal(in, out));
    }

    @Test
    void exceptionTests() {
        String [] args1 = {"-c", "LIL", "-d", "lil", "-o", "Output", "./src/test/resources/input/Exception"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> CryptographerLauncher.main(args1));
        String [] args2 = {"-c", "-o", "out", "./src/test/resources/input/Exception"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> CryptographerLauncher.main(args2));
        String [] args3 = {};
        Assertions.assertThrows(IllegalArgumentException.class, () -> CryptographerLauncher.main(args3));
        String [] args4 = {"-j", "-o", "out", "./src/test/resources/input/Exception"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> CryptographerLauncher.main(args4));

    }
}