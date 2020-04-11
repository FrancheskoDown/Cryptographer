package cryptographer;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class CryptographerLauncher {
    @Option(name = "-c", metaVar = "Code", required = true, usage = "Task"/*, forbids ={"-d"}*/)
    private boolean code;

    @Option(name = "-d", metaVar = "Decode",  usage = "Task"/*, forbids = {"-c"}*/)
    private boolean decode;

    @Option(name = "key", metaVar = "Key", required = true, usage = "Key of coding")
    private String key;

    @Option(name = "-o", metaVar = "OutputName", required = true, usage = "Output name")
    private String outputName;

    @Argument(required = true, metaVar = "InputName", usage = "Input name")
    private String inputName;

    public CryptographerLauncher() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        new CryptographerLauncher().launch(args);
    }

    private void launch(String[] args) throws IOException {
       final CmdLineParser parser = new CmdLineParser(this);
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
        if (code) cryptographer.encrypt(inputPath, key, outputPath);
        else if (decode) cryptographer.decrypt(inputPath, key, outputPath);
        else if (key == null) {
            System.err.println("ERROR: Can't define the key. Use key to set the key");
            throw new IllegalArgumentException();
        }
        else {
            System.err.println("ERROR: Can't define the task. Use -d or -c to set the task");
            throw new IllegalArgumentException();
        }
        System.out.println("SUCCESS");
    }
}