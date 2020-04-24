package cryptographer;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.IOException;
import java.nio.file.Path;

public class CryptographerLauncher {
    @Option(name = "-c", metaVar = "CodeKey" , usage = "Task", forbids = {"-d"})
    private String codeKey; // Key of coding

    @Option(name = "-d", metaVar = "DecodeKey", usage = "Task", forbids = {"-c"})
    private String decodeKey; // Key of decoding

    @Option(name = "-o", metaVar = "OutputName", usage = "Output name")
    private Path outputPath; // Name of output file

    @Argument(required = true, metaVar = "InputName", usage = "Input name")
    private Path inputPath; // Name of input file

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

        try {
            if (codeKey == null && decodeKey == null) {
                System.err.println("Use one of -c or -d to use task");
                return;
            }
            if (codeKey != null) cryptographer.encrypt(inputPath, codeKey, outputPath);
            if (decodeKey != null) cryptographer.decrypt(inputPath, decodeKey, outputPath);

            System.out.println("SUCCESS");
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage() + System.lineSeparator());
            throw e;
        }
    }
}