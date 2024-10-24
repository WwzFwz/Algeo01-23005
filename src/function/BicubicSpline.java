package function;
import matrix.Matrix;
import matrix.InversIdentity;
import utils.SavetoFile;
import utils.Menu;
import utils.ReadFile;
import java.util.Scanner;
import java.util.InputMismatchException;

public class BicubicSpline{
    private static final Scanner scanner = new Scanner(System.in);

    public static void menuBicubic() {
        // Menammpilkan menu program bicubic
        double result = 0.0;
        String resultString = "";


        double x = 0.0, y = 0.0; 
        Matrix matrix16x1 = new Matrix(16,1); 
        int choice = 0;
        while (true) {
            Menu.menuInput();
            System.out.print("Pilihan (1 atau 2) : ");
            choice = scanner.nextInt();

            if (choice == 1 || choice == 2) {
                break;
            } else {
                System.out.println("Pilihan tidak valid! Silakan coba lagi.");
            }
        }
        if (choice == 1) {
            while (true) {
                try {
                    System.out.print("Masukkan nilai x: ");
                    x = scanner.nextDouble();
                    System.out.print("Masukkan nilai y: ");
                    y = scanner.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Input tidak valid! Masukkan angka yang benar.");
                    scanner.next(); 
                }
            }
            System.out.println("Masukkan elemen matriks 16x1:");
            matrix16x1 = matrix16x1.readMatrix(16,1);

        } else if (choice == 2) {
                while (true) {
                    System.out.print("Masukkan nama file: ");
                    scanner.nextLine(); // Membersihkan buffer
                    String fileName = scanner.nextLine();
                    Matrix inputMatrix = ReadFile.readMatrixFromFile(fileName);

                    if (inputMatrix != null) {
                        x = inputMatrix.getElmt(4, 0);
                        y = inputMatrix.getElmt(4, 1);

                        // Konversi inputMatrix menjadi matrix16x1
                        matrix16x1 = convertTo16x1(inputMatrix);
                        break;
                    } else {
                        System.out.println("File tidak ditemukan atau tidak valid. Silakan coba lagi.");
                    }
                }

        } 
        // hasil aproksimasi f(x, y)
        result = function(x, y, matrix16x1);
        resultString = String.format("f(%.4f, %.4f) = %.4f", x, y, result);

        // Menampilkan hasil
        System.out.println(resultString);

        // Menyimpan hasil ke file jika diminta
        while (true) {
            Menu.subMenuSaveFile();
            String saveResponse = scanner.nextLine();

            if (saveResponse.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file untuk menyimpan hasil: ");
                // scanner.nextLine(); // Membersihkan buffer
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(resultString, fileOutputName);
                break;
            } else if (saveResponse.equalsIgnoreCase("n")) {
                System.out.println("Hasil tidak disimpan.");
                break;
            } else {
                System.out.println("Pilihan tidak valid! Silakan masukkan y atau n.");
            }
        }
    }

    // mengubah matrix input file jadi matrix 16x1
    public static Matrix convertTo16x1(Matrix inputMatrix) {
        // Mengambil kolom terakhir input matrix untuk  dijadikan matrix 16x1
        Matrix matrix16x1 = new Matrix(16, 1); // Matriks 16x1
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
        // Mengembalikan taksiran
        int i,j,k = 0 ;
        double z = 0.0;
        Matrix X = bicubicMatrix();
        Matrix Xinvers = InversIdentity.inversIdentity(X);
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
        // Membuat matrix bicubic
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