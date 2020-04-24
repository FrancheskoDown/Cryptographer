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
    public void encrypt(Path inputFile,   String cryptographerKey, Path outputFile) throws IOException {
        if (outputFile == null) outputFile = inputFile.getFileName();
        try (InputStream inputStream = new FileInputStream(inputFile.toFile())) {
            String directoryName = "output"; // Default output directory name
            File directory = new File(directoryName); // Default output directory
            if (!directory.exists()) directory.mkdir();

            try (OutputStream outputStream = new FileOutputStream(directoryName + File.separatorChar + outputFile.toString() + ".txt")) {
                cryptographer (inputStream, cryptographerKey, outputStream);
            }
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
               cryptographer (inputStream, cryptographerKey, outputStream);
            }
        }
    }

    /*
     * Method for coding or decoding
     * @param inputStream - input stream of file
     * @param cryptographerKey - Encoding key
     * @param outputStream - output stream of file
     * @throws IOException
     */
    private void cryptographer (InputStream inputStream, String cryptographerKey, OutputStream outputStream) throws IOException {
        byte[] byteKeyArray = byteKeyGenerator(cryptographerKey); // Array of byte key characters
        byte cur = (byte) inputStream.read(); // Current byte in input file
        byte next = (byte) inputStream.read(); // Next byte in input file
        int i = 0; // Character counter

        while (cur != -1) {
            if (i == byteKeyArray.length) i = 0;
            byte symbol = (byte) (cur ^ byteKeyArray[i % byteKeyArray.length]); // Current coded or decoded symbol
            writer (symbol, outputStream);
            i++;
            cur = next;
            next = (byte) inputStream.read();
        }
    }

    /*
     * Method for byte key generating
     * @param cryptographerKey - Encoding key
     * @throws IOException
     */
    private byte[] byteKeyGenerator (String cryptographerKey) throws IOException {
        char[] keyArray = cryptographerKey.toCharArray(); // Array of key characters
        byte[] byteKeyArray = new byte[cryptographerKey.length()]; // Array of byte key characters
        char cur; // Current key char
        for (int i = 0 ; i < keyArray.length ; i++) {
            cur = keyArray[i];
            byteKeyArray[i] = (byte) cur;
        }
        return byteKeyArray;
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
            e.getMessage();
            throw e;
        }
    }
}

