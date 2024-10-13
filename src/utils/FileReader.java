package utils;
import matrix.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class FileReader{
    private static final String BASE_DIRECTORY = "../test/input/";
    public static Matrix readMatrixFromFile(String fileName) throws FileNotFoundException{
        Matrix matrix = null;
            String filePath = BASE_DIRECTORY + fileName;
        try{
            File inputFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine(); // Baca baris pertama  
            int rows = 0;
            int maxCols = 0;
            //mencari max cols;
            while(line != null){
                String[] elements = line.trim().split("\\s+");
                rows ++;
                maxCols = Math.max(maxCols, elements.length);
                line = reader.readLine();

            }
            // reset reader
            reader.close();
            reader = new BufferedReader(new FileReader(inputFile));
            Matrix matrix = new Matrix (rows,maxCols);


            line = reader.readLine();
            while (line != null){
                String[] elements = line.trim().split("\\s+");
                for (int col = 0; col < maxCols; col++) {
                    if (col < elements.length) {
                        matrix.setElmt(row, col, Double.parseDouble(elements[col]));
                    } else {
                        // Isi elemen yang tidak ada dengan NaN
                        matrix.setElmt(row, col, Double.NaN);
                    }
                }
                row++;
                line = reader.readLine();
            }
            reader.close();
        }catch(IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }
        return matrix;
            
    }
}
