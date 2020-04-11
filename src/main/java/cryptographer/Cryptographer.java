package cryptographer;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Cryptographer {

    /*
    * Method for encoding a file
    * @param inputFile - Path for the file from which we are reading text
    * @param outputFile - Path for the file in which we write text
    * @throws IOException
    */

    public void encrypt (Path inputFile, String cryptographerKey, Path outputFile) throws IOException{
   if (outputFile == null) outputFile = inputFile.getFileName();
   try (InputStream inputStream = new FileInputStream(inputFile.toFile())){
    String directoryName = "output";
    File directory = new File(directoryName);
    if (!directory.exists()) directory.mkdir();
    try (OutputStream outputStream = new FileOutputStream(directoryName + File.separatorChar + outputFile.toString() + ".txt")) {
        byte[] input = inputFile.toString().getBytes(); // Array of input characters
        byte[] key = cryptographerKey.getBytes(); // Array of key characters
        byte[] res = new byte[inputFile.toString().length()]; // Array of encoded characters

        for (int i = 0; i < input.length; i++) {
            res[i] = (byte) (input[i] ^ key[i % key.length]);
        }

        writer(res, outputStream);
    }
   }
}

private void writer (byte[] result, OutputStream writer) throws IOException {
    try {
        writer.write(result);
    } catch (IOException e) {
        e.printStackTrace();
        throw e;
    }
}

    public void decrypt (Path inputFile, String cryptographerKey, Path outputFile) throws IOException {
        String name = inputFile.getFileName().toString();
        if (outputFile == null) outputFile = Paths.get(name.substring(0, name.length() - 3));
        try (InputStream inputStream = new FileInputStream(inputFile.toFile())){
            try (OutputStream outputStream = new FileOutputStream("."+ File.separatorChar + "output" + File.separatorChar + outputFile.toString()))
             {
                byte[] input = inputFile.toString().getBytes(); // Array of input characters
                byte[] key = cryptographerKey.getBytes(); // Array of key characters
                byte[] res = new byte[inputFile.toString().length()]; // Array of encoded characters

                for (int i = 0; i < input.length; i++) {
                    res[i] = (byte) (input[i] ^ key[i % key.length]);
                }

                writer(res, outputStream);
            }
        }
    }
}

