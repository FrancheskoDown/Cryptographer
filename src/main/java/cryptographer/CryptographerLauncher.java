package cryptographer;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

public class CryptographerLauncher {
    @Option(name = "-c", metaVar = "CodeKey" , usage = "Task")
    private String codeKey; // Key of coding

    @Option(name = "-d", metaVar = "DecodeKey", usage = "Task")
    private String decodeKey; // Key of decoding

    @Option(name = "-o", metaVar = "OutputName", usage = "Output name")
    private String outputName; // Name of output file

    @Argument(required = true, metaVar = "InputName", usage = "Input name")
    private String inputName; // Name of input file

    public CryptographerLauncher() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        new CryptographerLauncher().launch(args);
    }

    /*
     * The method calls the packer class methods for execution, and also throws errors if the commands are entered incorrectly
     * @param args - Command line arguments
     * @throws IOException
     */
    private void launch(String[] args) throws IOException {
        Collection<Character> validValuesOfKey = Arrays.asList( // List of valid patterns for the encryption key
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        );

       CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Cryptographer.jar -c key|-d key InputName -o OutputName");
            parser.printUsage(System.err);
            throw  new IllegalArgumentException();
        }

        Cryptographer cryptographer = new Cryptographer();
        Path inputPath = FileSystems.getDefault().getPath(inputName);
        Path outputPath = outputName != null? FileSystems.getDefault().getPath(outputName) : null;

        try {
            if (codeKey != null && decodeKey != null) {
                System.err.println("ERROR: Use one of -d or -c to set the task");
                throw new IllegalArgumentException();
            } else if (codeKey != null) {
                int codeKeyLength = codeKey.length(); // Length of coding key
                char[] codeKeyArray = codeKey.toCharArray(); // Array of characters included in the encoding key

                for (int i = 0; i < codeKeyLength; i++) {
                    if (!validValuesOfKey.contains(codeKeyArray[i])) {
                        System.err.println("ERROR: encryption key must be in a hexadecimal number system");
                        throw new IllegalArgumentException();
                    }
                }

                cryptographer.encrypt(inputPath, codeKey, outputPath);
            } else if (decodeKey != null) {
                int decodeKeyLength = decodeKey.length(); // Length of decoding key
                char[] decodeKeyArray = decodeKey.toCharArray(); // Array of characters included in the decoding key

                for (int i = 0; i < decodeKeyLength; i++) {
                    if (!validValuesOfKey.contains(decodeKeyArray[i])) {
                        System.err.println("ERROR: decryption key must be in a hexadecimal number system");
                        throw new IllegalArgumentException();
                    }
                }

                cryptographer.decrypt(inputPath, decodeKey, outputPath);
            } else {
                System.err.println("ERROR: Can't define the task. Use -d or -c to set the task");
                throw new IllegalArgumentException();
            }

            System.out.println("SUCCESS");
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage() + System.lineSeparator());
            throw e;
        }
    }
}