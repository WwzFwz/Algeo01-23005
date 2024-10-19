package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SavetoFile {
    private static final String BASE_DIRECTORY = "../test/output";

    public static void saveResultToFile(String content, String fileName) {
        // CATATAN Content berupa string, jika matrix blm menjadi string maka bisa di convert ke string menggunakan method toString .
        try {
            // Mendefinisikan folder relative "test/output" berdasarkan project root
            // String folderName = System.getProperty("user.dir") + "/test/output";
            String folderName = BASE_DIRECTORY;
            File resultFolder = new File(folderName);
            
            // Jika foldernya tidak ada, buat foldernya
            if (!resultFolder.exists()) {
                resultFolder.mkdirs(); 
            }

            // Buat file 
            File outputFile = new File(resultFolder, fileName);

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            // Menulis konten 
            writer.write(content);
            writer.close();

            System.out.println("File berhasil disimpan di: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error saving result to file: " + e.getMessage());
        }
    }
}
