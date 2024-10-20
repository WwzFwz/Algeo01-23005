package function;
import matrix.Matrix;
import matrix.InversMatrix;
import utils.SavetoFile;
import utils.Menu;
import utils.ReadFile;
import java.util.Scanner;
public class BicubicSpline{
    private static final Scanner scanner = new Scanner(System.in);

    public static void menuBicubic() {
        double result = 0.0;
        String resultString = "";

        Menu.menuInput();
        int choice = scanner.nextInt();

        double x = 0.0, y = 0.0; 
        Matrix matrix16x1 = new Matrix(16,1); 

        if (choice == 1) {
            // Input dari keyboard
            System.out.print("Masukkan nilai x: ");
            x = scanner.nextDouble();
            System.out.print("Masukkan nilai y: ");
            y = scanner.nextDouble();

            System.out.println("Masukkan elemen matriks 16x1:");
            matrix16x1 = matrix16x1.readMatrix(16,1);
            
        } else if (choice == 2) {
            // Input dari file
            System.out.print("Masukkan nama file: ");
            scanner.nextLine(); // Membersihkan buffer
            String fileName = scanner.nextLine();
            Matrix inputMatrix = ReadFile.readMatrixFromFile(fileName);

            x = inputMatrix.getElmt(4, 0);
            y = inputMatrix.getElmt(4, 1);

            // Konversi inputMatrix menjadi matrix16x1
            matrix16x1 =  convertTo16x1(inputMatrix);
        } else {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            return;
        }

        // Menghitung hasil aproksimasi f(x, y)
        result = function(x, y, matrix16x1);
        resultString = String.format("f(%.4f, %.4f) = %.4f", x, y, result);

        // Menampilkan hasil
        System.out.println(resultString);

        // Menyimpan hasil ke file jika diminta
        Menu.subMenuSaveFile();
        String saveResponse = scanner.next();
        if (saveResponse.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file untuk menyimpan hasil: ");
            scanner.nextLine();
            String fileOutputName = scanner.nextLine();
            SavetoFile.saveResultToFile(resultString, fileOutputName);
        }
    }
    // public static void menuBicubic (){
    //     // Scanner scanner = new Scanner(System.in);
    //     double result = 0.0;
    //     String resultString = "";

    //     Menu.menuInput();
    //     int choice = scanner.nextInt();

    //     if (choice == 1) {
    //         // Input dari keyboard
    //         result = solveFromKeyboard();
    //         resultString = String.format("APROKSIMASI = %.4f", result);
    //     } else if (choice == 2) {
    //         // Input dari file
    //         System.out.print("Masukkan nama file: ");
    //         scanner.nextLine(); // Membersihkan buffer
    //         String fileName = scanner.nextLine();
    //         result = solveFromFile(fileName);
    //         resultString = String.format("APROKSIMASI = %.4f", result);
    //     } else {
    //         System.out.println("Pilihan tidak valid. Silakan coba lagi.");
    //         return;
    //     }

    //     // Menampilkan hasil
    //     System.out.println(resultString);

    //     // save ke file ?
    //     Menu.saveResultToFile();
    //     String saveResponse = scanner.next();
    //     if (saveResponse.equalsIgnoreCase("y")) {
    //         System.out.print("Masukkan nama file untuk menyimpan hasil: ");
    //         scanner.nextLine(); 
    //         String fileOutputName = scanner.nextLine();
    //         SavetoFile.saveResultToFile(resultString, fileOutputName);
    //     }
    // }




    
    // public static double solveFromKeyboard() {
    //     // Scanner scanner = new Scanner(System.in);

    //     System.out.print("Masukkan nilai x: ");
    //     double x = scanner.nextDouble();
    //     System.out.print("Masukkan nilai y: ");
    //     double y = scanner.nextDouble();

    //     //matrix 16 x 1
    //     Matrix matrix1x16 = new Matrix(16, 1);
    //     System.out.println("Masukkan elemen matriks 16x1:");
    //     for (int i = 0; i < 16; i++) {
    //         System.out.print("Elemen [" + i + "][0]: ");
    //         matrix1x16.setElmt(i, 0, scanner.nextDouble());
    //     }

    //     double result = function(x, y, matrix1x16);

    //     return result;
    // }

    // public static double solveFromFile(String fileName) {
    //     Matrix inputMatrix = ReadFile.readMatrixFromFile(fileName);
    //     Matrix matrix16x1= convertTo16x1(inputMatrix);
    //     double x = inputMatrix.getElmt(4, 0);
    //     double y = inputMatrix.getElmt(4, 1);
    //     double result = function(x, y, matrix16x1);
    //     return result;
    // }

    // mengubah matrix input file jadi matrix 16x1
    public static Matrix convertTo16x1(Matrix inputMatrix) {
        Matrix matrix16x1 = new Matrix(16, 1); // Matriks 1x16
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix16x1.setElmt(index, 0, inputMatrix.getElmt(i, j));
                index++;
            }
        }
        return matrix16x1;
    }



    public static double function(double x, double y,Matrix MatrixY){
        int i,j,k = 0 ;
        double z = 0.0;
        Matrix X = bicubicMatrix();
        Matrix Xinvers = InversMatrix.inversIdentity(X);
        Matrix a = Matrix.multiplyMatrix(Xinvers,MatrixY);
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 4; i++) {
                z += a.getElmt(k, 0) * Math.pow(x, i) * Math.pow(y, j);
                k++;
            }
        }

        return z;
    }

    public static Matrix bicubicMatrix (){
        Matrix X  = new Matrix(16,16);
        int i,j;
        int x,y ;
        int idxCol = 0 ;
        int idxRow = 0;

        // f (x,y) representation   

        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                for (j = 0 ; j < 4; j ++) {
                    for (i = 0 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol,Math.pow(x,i) * Math.pow(y,j));
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }   
        }

        // fx(x,y) representation 
        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                for (j = 0 ; j < 4; j ++) {
                    X.setElmt(idxRow,idxCol,0);
                    idxCol++;
                    for (i = 1 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol, i * Math.pow(x,i-1) * Math.pow(y,j));
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }

        // fy(x,y) representation
        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                for (j = 1 ; j < 4; j ++) {
                    for (i = 0 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol, j * Math.pow(x,i) * Math.pow(y,j-1));
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }

        // fxy(x,y) representation
        for (y = 0 ; y< 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                for (j = 1 ; j < 4; j ++) {
                    X.setElmt(idxRow,idxCol,0);
                    idxCol++;
                    for (i = 1 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol,Math.pow(x,i-1) * Math.pow(y,j-1)*i*j);
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }
        return X ;
        
    }

}