package cryptographer;

import java.io.*;
import java.nio.file.Path;


public class Cryptographer {

    /*
     * Method for encoding a file
     * @param inputFile - Path for the file from which we are reading text
     * @param cryptographerKey - Encoding key
     * @param outputFile - Path for the file in which we write text
     * @throws IOException
     */
    public void encrypt(Path inputFile, String cryptographerKey, Path outputFile) throws IOException {
        if (outputFile == null) outputFile = inputFile.getFileName();
        try (InputStream inputStream = new FileInputStream(inputFile.toFile())) {
            String directoryName = "output"; // Default output directory name
            File directory = new File(directoryName); // Default output directory
            if (!directory.exists()) directory.mkdir();

            try (OutputStream outputStream = new FileOutputStream(directoryName + File.separatorChar + outputFile.toString() + ".txt")) {
                byte[] key = cryptographerKey.getBytes(); // Array of key characters
                byte cur = (byte) inputStream.read(); // Current byte in input file
                byte next = (byte) inputStream.read(); // Next byte in input file
                int i = 0; // Character counter

                while (cur != -1) {
                    byte codedSymbol = (byte) (cur ^ key[i % key.length]);
                    writer (codedSymbol, outputStream);
                    i++;
                    cur = next;
                    next = (byte) inputStream.read();
                }
            }
        }
    }

    /*
     * Method for recording encoded or decoded result
     * @param result - Encoding or decoding result
     * @param writer - Output stream
     * @throws IOException
     */
    private void writer(byte cur, OutputStream writer) throws IOException {
        try {
            writer.write(cur);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /*
     * Method for decoding a file
     * @param inputFile - Path for the file from which we are reading text
     * @param cryptographerKey - Decoding key
     * @param outputFile - Path for the file in which we write text
     * @throws IOException
     */
    public void decrypt(Path inputFile, String cryptographerKey, Path outputFile) throws IOException {
        if (outputFile == null) outputFile = inputFile.getFileName();
        try (InputStream inputStream = new FileInputStream(inputFile.toFile())) {
            String directoryName = "output"; // Default output directory name
            File directory = new File(directoryName); // Default output directory
            if (!directory.exists()) directory.mkdir();

            try (OutputStream outputStream = new FileOutputStream("." + File.separatorChar + "output" + File.separatorChar + outputFile.toString())) {
                {
                    byte[] key = cryptographerKey.getBytes(); // Array of key characters
                    byte cur = (byte) inputStream.read(); // Current byte in input file
                    byte next = (byte) inputStream.read(); // Next byte in input file
                    int i = 0; // Character counter

                    while (cur != -1) {
                        byte decodedSymbol = (byte) (cur ^ key[i % key.length]);
                        writer (decodedSymbol, outputStream);
                        i++;
                        cur = next;
                        next = (byte) inputStream.read();
                    }
                }

            }
        }
    }
}

