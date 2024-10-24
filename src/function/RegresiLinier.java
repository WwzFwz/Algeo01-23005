// // PROGRAM K14-GeprekMumbul-F09

// IDENTITAS
// Kelompok : 14 - Geprek Mumbul
// NIM/Nama - 1 : 13523021 - Muhammad Raihan Nazhim Oktana
// NIM/Nama - 2 : 13523005 - Muhammad Alfansya
// NIM/Nama - 3 : 13523065 - Dzaky Aurelia Fawwaz
// Instansi : Sekolah Teknik Elektro dan Informatika (STEI) Institut Teknologi Bandung (ITB)
// Jurusan : Teknik Informatika (IF)
// Nama File : RegresiLinier.java
// Topik : Tugas Besar 1 Aljabar Linier dan Geometri 2024 (IF2123-24)
// Tanggal : Kamis, 24 Oktober 2024
// Deskripsi : Subprogram F09 - Regresi Linier
// Penanggung Jawab F09 : 13523021 - Muhammad Raihan Nazhim Oktana

// // KAMUS
// // ...

// // ALGORITMA
package function;
import java.util.Scanner;
import matrix.*;
import utils.Menu;
import utils.ReadFile;
import utils.SavetoFile;
public class RegresiLinier {
    private static final Scanner scanner = new Scanner(System.in);

    public static void menuRegresiLinier() {
        Menu.menuInput();
        int choice = scanner.nextInt(); 
        scanner.nextLine();
        if (choice == 1) {
            runRLFromKeyboard();
        } else if (choice == 2) {
            System.out.print("Masukkan nama file : ");
            String fileName = scanner.nextLine();
            runRLFromFile(fileName);
        } else {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }

    public static Matrix multipleLinearRegression(Matrix matrixX , Matrix matrixY) {
        Matrix matrixXT = matrixX.transpose();
        Matrix matrixXTX = Matrix.multiplyMatrix(matrixXT , matrixX);
        Matrix matrixXTY = Matrix.multiplyMatrix(matrixXT , matrixY);
        Matrix matrixXTXI = InversIdentity.inversIdentity(matrixXTX);
        Matrix res = Matrix.multiplyMatrix(matrixXTXI , matrixXTY);
        return res;
    }

    public static void runRLFromKeyboard() {
        System.out.print("Masukkan jumlah observasi : ");
        int n = scanner.nextInt();
        System.out.print("Masukkan jumlah variabel independen : ");
        int m = scanner.nextInt();
        Matrix matrixX = new Matrix(n , m + 1);
        Matrix matrixY = new Matrix(n , 1);
        double [] quest = new double[m];
        System.out.println("Masukkan data untuk variabel independen dan dependen :");
        for (int i = 0 ; i < n ; i++) {
            matrixX.setElmt(i , 0 , 1);
            System.out.printf("\nObservasi %d :\n" , i + 1);
            for (int j = 1 ; j <= m ; j++) {
                System.out.printf("X%d : " , j);
                double q = scanner.nextDouble();
                matrixX.setElmt(i , j , q);
            }
            System.out.print("Y  : ");
            double q = scanner.nextDouble();
            matrixY.setElmt(i , 0 , q);
        }
        System.out.print("\nMasukkan pertanyaan nilai yang akan dicari :\n");
        for (int i = 0 ; i < m ; i++) {
            System.out.printf("X%d : " , i + 1);
            double q = scanner.nextDouble();
            quest[i] = q;
        }
        double ans = 0;
        Matrix matrixRES = multipleLinearRegression(matrixX , matrixY);
        System.out.println();
        String output = "F(X1";
        System.out.print("F(X1");
        for (int i = 1 ; i < m ; i++) {
            System.out.printf(" , X%d" , i + 1);
            output += String.format(" , X%d" , i + 1);
        }
        System.out.print(") =");
        output += ") =";
        for (int i = 0 ; i < matrixRES.getRow() ; i++) {
            if (i == 0) {
                ans += matrixRES.getElmt(i , 0);
            } else {
                ans += (matrixRES.getElmt(i , 0) * quest[i - 1]);
            }
            if (matrixRES.getElmt(i , 0) >= 0) {
                if (i == 0) {
                    System.out.printf(" %.4f" , matrixRES.getElmt(i , 0));
                    output += String.format(" %.4f" , matrixRES.getElmt(i , 0));
                } else {
                    System.out.printf(" + %.4f X%d" , matrixRES.getElmt(i , 0) , i);
                    output += String.format(" + %.4f X%d" , matrixRES.getElmt(i , 0) , i);
                }
            } else {
                if (i == 0) {
                    System.out.printf(" -%.4f" , Math.abs(matrixRES.getElmt(i , 0)));
                    output += String.format(" -%.4f" , Math.abs(matrixRES.getElmt(i , 0)));
                } else {
                    System.out.printf(" - %.4f X%d" , Math.abs(matrixRES.getElmt(i , 0)) , i);
                    output += String.format(" - %.4f X%d" , Math.abs(matrixRES.getElmt(i , 0)) , i);
                }
            }
        }
        System.out.printf("\nF(X1 = %.4f" , quest[0]);
        output += String.format("\nF(X1 = %.4f" , quest[0]);
        for (int i = 1 ; i < m ; i++) {
            System.out.printf(" ; X%d = %.4f" , i + 1 , quest[i]);
            output += String.format(" ; X%d = %.4f" , i + 1 , quest[i]);
            
        }
        System.out.printf(") = %.4f\n" , ans);
        output += String.format(") = %.4f\n" , ans);
        Menu.subMenuSaveFile();
        scanner.nextLine();
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file: ");
            String fileOutputName = scanner.nextLine();
            SavetoFile.saveResultToFile(output , fileOutputName);
        }
    }

    public static void runRLFromFile (String fileName) {
        Matrix data = ReadFile.readMatrixFromFile(fileName);
        int n = data.getRow();
        int m = data.getCol() - 1;
        Matrix matrixX = new Matrix(n - 1 , m + 1);
        Matrix matrixY = new Matrix(n - 1 , 1);
        double [] quest = new double[m];
        for (int i = 0 ; i < n - 1 ; i++) {
            matrixX.setElmt(i , 0 , 1);
            for (int j = 1 ; j <= m ; j++) {
                matrixX.setElmt(i , j , data.getElmt(i , j - 1));
            }
            matrixY.setElmt(i , 0 , data.getElmt(i , m));
        }
        for (int i = 0 ; i < m ; i++) {
            quest[i] = data.getElmt(n - 1 , i);
        }
        double ans = 0;
        Matrix matrixRES = multipleLinearRegression(matrixX , matrixY);
        System.out.println();
        String output = "F(X1";
        System.out.print("F(X1");
        for (int i = 1 ; i < m ; i++) {
            System.out.printf(" , X%d" , i + 1);
            output += String.format(" , X%d" , i + 1);
        }
        System.out.print(") =");
        output += ") =";
        for (int i = 0 ; i < matrixRES.getRow() ; i++) {
            if (i == 0) {
                ans += matrixRES.getElmt(i , 0);
            } else {
                ans += (matrixRES.getElmt(i , 0) * quest[i - 1]);
            }
            if (matrixRES.getElmt(i , 0) >= 0) {
                if (i == 0) {
                    System.out.printf(" %.4f" , matrixRES.getElmt(i , 0));
                    output += String.format(" %.4f" , matrixRES.getElmt(i , 0));
                } else {
                    System.out.printf(" + %.4f X%d" , matrixRES.getElmt(i , 0) , i);
                    output += String.format(" + %.4f X%d" , matrixRES.getElmt(i , 0) , i);
                }
            } else {
                if (i == 0) {
                    System.out.printf(" -%.4f" , Math.abs(matrixRES.getElmt(i , 0)));
                    output += String.format(" -%.4f" , Math.abs(matrixRES.getElmt(i , 0)));
                } else {
                    System.out.printf(" - %.4f X%d" , Math.abs(matrixRES.getElmt(i , 0)) , i);
                    output += String.format(" - %.4f X%d" , Math.abs(matrixRES.getElmt(i , 0)) , i);
                }
            }
        }
        System.out.printf("\nF(X1 = %.4f" , quest[0]);
        output += String.format("\nF(X1 = %.4f" , quest[0]);
        for (int i = 1 ; i < m ; i++) {
            System.out.printf(" ; X%d = %.4f" , i + 1 , quest[i]);
            output += String.format(" ; X%d = %.4f" , i + 1 , quest[i]);
            
        }
        System.out.printf(") = %.4f\n" , ans);
        output += String.format(") = %.4f\n" , ans);
        Menu.subMenuSaveFile();
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file: ");
            String fileOutputName = scanner.nextLine();
            SavetoFile.saveResultToFile(output , fileOutputName);
        }
    }
}