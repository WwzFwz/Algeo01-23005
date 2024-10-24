// PROGRAM K14-GeprekMumbul-F11

// IDENTITAS
// Kelompok : 14 - Geprek Mumbul
// NIM/Nama - 1 : 13523021 - Muhammad Raihan Nazhim Oktana
// NIM/Nama - 2 : 13523005 - Muhammad Alfansya
// NIM/Nama - 3 : 13523065 - Dzaky Aurelia Fawwaz
// Instansi : Sekolah Teknik Elektro dan Informatika (STEI) Institut Teknologi Bandung (ITB)
// Jurusan : Teknik Informatika (IF)
// Nama File : SPL.java
// Topik : Tugas Besar 1 Aljabar Linier dan Geometri 2024 (IF2123-24)
// Tanggal : Kamis, 24 Oktober 2024
// Deskripsi : Subprogram F11 - SPL (Sistem Persamaan Linier)
// Penanggung Jawab F11 : 13523021 - Muhammad Raihan Nazhim Oktana

// KAMUS
// ...

// ALGORITMA
package matrix;
import java.util.Scanner;
import utils.Menu;
import utils.ReadFile;
import utils.SavetoFile;
public class SPL {
    private static final Scanner scanner = new Scanner(System.in);

    public static void menuSPL() {
        Menu.menuInput();
        int choice = scanner.nextInt(); 
        scanner.nextLine();
        if (choice == 1) {
            runSPLFromKeyboard();
        } else if (choice == 2) {
            System.out.print("Masukkan nama file : ");
            String fileName = scanner.nextLine();
            runSPLFromFile(fileName);
        } else {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }

    public static void runSPLFromFile(String fileName) {
        Matrix data = ReadFile.readMatrixFromFile(fileName);
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("                      PILIH METODE                          ");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. SPL Invers");
        System.out.println("2. SPL Cramer");
        System.out.println("3. SPL Gauss");
        System.out.println("4. SPL Gauss Jordan");
        System.out.println("------------------------------------------------------------");
        int choice = scanner.nextInt(); 
        scanner.nextLine();
        if (choice == 1) {
            SPLinvers(data);
        } else if (choice == 2) {
            SPLcramer(data);
        } else if (choice == 3) {
            SPLgauss(data);
        } else {
            SPLgaussJordan(data);
        }
    }

    public static void runSPLFromKeyboard() {
        System.out.print("Masukkan jumlah baris : ");
        int n = scanner.nextInt();
        System.out.print("Masukkan jumlah kolom : ");
        int m = scanner.nextInt();
        Matrix data = new Matrix(n , m);
        System.out.println("Masukkan data matrix : ");
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m ; j++) {
                System.out.printf("M[%d][%d] : " , i + 1 , j + 1);
                double x = scanner.nextDouble();
                data.setElmt(i , j , x);
            }
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("                      PILIH METODE                          ");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. SPL Invers");
        System.out.println("2. SPL Cramer");
        System.out.println("3. SPL Gauss");
        System.out.println("4. SPL Gauss Jordan");
        System.out.println("------------------------------------------------------------");
        int choice = scanner.nextInt(); 
        scanner.nextLine();
        if (choice == 1) {
            SPLinvers(data);
        } else if (choice == 2) {
            SPLcramer(data);
        } else if (choice == 3) {
            SPLgauss(data);
        } else {
            SPLgaussJordan(data);
        }
    }

    public static void SPLinvers (Matrix matrix) {
        int i, j;
        Matrix matrixA = new Matrix(matrix.getRow() , matrix.getCol() - 1);
        Matrix matrixB = new Matrix(matrix.getRow() , 1);
        for (i = 0 ; i < matrixA.getRow() ; i++) {
            for (j = 0 ; j < matrix.getCol() ; j++) {
                if (j != matrix.getCol() - 1) {
                    matrixA.setElmt(i , j , matrix.getElmt(i , j));
                } else {
                    matrixB.setElmt(i , 0 , matrix.getElmt(i , j));
                }
            }
        }
        String output;
        if ((matrix.getRow() != matrix.getCol() - 1) || (Determinant.DeterminantCOFACTOR(matrixA) == 0)) {
            System.out.println("SPL tersebut tidak dapat diselesaikan dengan metode invers.");
            output = "SPL tersebut tidak dapat diselesaikan dengan metode invers.\n";
        } else {
            Matrix matrixI = InversIdentity.inversIdentity(matrixA);
            Matrix matrixX = Matrix.multiplyMatrix(matrixI , matrixB);
            output = "Solusi :";
            System.out.print("Solusi :");
            for (i = 0 ; i < matrixX.getRow() ; i++) {
                if (i != matrixX.getRow() - 1) {
                    System.out.printf(" x%d = %.4f ;" , i + 1 , matrixX.getElmt(i , 0));
                    output += String.format(" x%d = %.4f ;" , i + 1 , matrixX.getElmt(i , 0));
                } else {
                    System.out.printf(" x%d = %.4f" , i + 1 , matrixX.getElmt(i , 0));
                    output += String.format(" x%d = %.4f" , i + 1 , matrixX.getElmt(i , 0));
                }
            }
            System.out.println();
            output += "\n";
        }
        Menu.subMenuSaveFile();
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file: ");
            String fileOutputName = scanner.nextLine();
            SavetoFile.saveResultToFile(output , fileOutputName);
        }
    }

    public static Matrix replaceColumn(Matrix matrix , double[] konstanta , int k) {
        int row = matrix.getRow();
        int col = matrix.getCol();
        Matrix matrixMDF = new Matrix(row , col);
        for (int i = 0 ; i < row ; i++) {
            for (int j = 0 ; j < col ; j++) {
                if (j == k) {
                    matrixMDF.setElmt(i , j , konstanta[i]);
                } else {
                    matrixMDF.setElmt(i , j , matrix.getElmt(i , j));
                }
            }
        }
        return matrixMDF;
    }

    public static double[] kaidahCramer(Matrix matrix, double[] konstanta) {
        int row = matrix.getRow();
        double DetA = Determinant.DeterminantCOFACTOR(matrix);
        double[] solutions = new double[row];
        for (int i = 0 ; i < row ; i++) {
            Matrix matrixMDF = replaceColumn(matrix , konstanta , i);
            solutions[i] = Determinant.DeterminantCOFACTOR(matrixMDF) / DetA;
        }
        return solutions;
    }

    public static void SPLcramer (Matrix matrixC) {
        int row = matrixC.getRow();
        int col = matrixC.getCol();
        Matrix matrixK = new Matrix(row , col - 1);
        double[] constant = new double[row];
        for (int i = 0 ; i < row ; i++) {
            for (int j = 0 ; j < col - 1 ; j++) {
                matrixK.setElmt(i , j , matrixC.getElmt(i , j));
            }
            constant[i] = matrixC.getElmt(i , col - 1);
        }
        if (Determinant.DeterminantCOFACTOR(matrixK) != 0 && matrixK.isSquareMatrix()) {
            double[] ans = kaidahCramer(matrixK , constant);
            String output = "Solusi :";
            System.out.print("Solusi :");
            int i;
            for (i = 0 ; i < ans.length ; i++) {
                if (i != ans.length - 1) {
                    System.out.printf(" x%d = %.4f ;" , i + 1 , ans[i]);
                    output += String.format(" x%d = %.4f ;" , i + 1 , ans[i]);
                } else {
                    System.out.printf(" x%d = %.4f" , i + 1 , ans[i]);
                    output += String.format(" x%d = %.4f" , i + 1 , ans[i]);
                }
            }
            System.out.println();
            output += "\n";
            Menu.subMenuSaveFile();
            scanner.nextLine();
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output , fileOutputName);
            }
        } else {
            String output = "SPL tersebut tidak dapat diselesaikan dengan metode cramer!\n";
            System.out.println("SPL tersebut tidak dapat diselesaikan dengan metode cramer!");
            Menu.subMenuSaveFile();
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output , fileOutputName);
            }
        }
    }

    public static int SolutionType (Matrix matrix) {
        int i , j;
        int rank = 0;
        boolean check1 = false;
        int n = matrix.getRow();
        int m = matrix.getCol();
        int ac = m - 1;
        for (i = 0 ; i < n ; i++) {
            boolean check2 = true;
            for (j = 0 ; j < ac ; j++) {
                if (matrix.getElmt(i , j) != 0) {
                    check2 = false;
                    break;
                }
            }
            if (!check2) {
                rank++;
                check1 = true;
            } else if (matrix.getElmt(i , ac) != 0) {
                return 0;
            }
        }
        if (!check1 || rank < ac) {
            return 2;
        } else {
            return 1;
        }
    }

    public static double[] Substitute(Matrix matrix, double[] X) {
        int n = matrix.getRow();
        int m = matrix.getCol();
        for (int i = n - 1 ; i >= 0 ; i--) {
            X[i] = matrix.getElmt(i , m - 1);
            for (int j = i + 1 ; j < n ; j++) {
                X[i] -= matrix.getElmt(i , j) * X[j];
            }
            X[i] /= matrix.getElmt(i , i);
        }
        return X;
    }
        
    public static void solveParametric(Matrix matrix) {
        int n = matrix.getRow();
        int m = matrix.getCol();
        Matrix matrixSP = GaussJordan.gaussJordan(matrix);
        int count = 0;
        String [] ans = new String[m - 1];
        boolean [] check = new boolean[m - 1];
        for (int i = 0 ; i < m - 1 ; i++) {
            check[i] = false;
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < m - 1 ; j++) {
                if (matrixSP.getElmt(i , j) == 1) {
                    check[j] = true;
                    break;
                }
            }
        }
        for (int i = 0 ; i < m - 1 ; i++) {
            if (!check[i]) {
                ans[i] = String.format("%c" , (char) ('a' + count));
                count++;
            }
        }
        for (int i = 0 ; i < n ; i++) {
            String temp = String.format("%.4f" , matrixSP.getElmt(i , m - 1));
            int pos = 0 , j = 0;
            boolean pass = false;
            while ((matrixSP.getElmt(i , j) == 0) && (j < m - 1)) {
                j++;
            }
            if (matrixSP.getElmt(i , j) == 1) {
                pass = true;
                pos = j;
                j++;
            }
            while (j < m - 1) {
                if (matrixSP.getElmt(i , j) > 0) {
                    temp += String.format(" - %.4f%s" , matrixSP.getElmt(i , j) , ans[j]);
                } else if (matrixSP.getElmt(i , j) < 0) {
                    temp += String.format(" + %.4f%s" , Math.abs(matrixSP.getElmt(i , j)) , ans[j]);
                }
                j++;
            }
            if (pass) {
                ans[pos] = temp;
            }
        }
        String output = "Solusi parametrik :\n";
        System.out.println("Solusi parametrik :");
        for (int i = 0 ; i < m - 1 ; i++) {
            System.out.printf("X%d = %s\n" , i + 1 , ans[i]);
            output += String.format("X%d = %s\n" , i + 1 , ans[i]);
        }
        Menu.subMenuSaveFile();
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file: ");
            String fileOutputName = scanner.nextLine();
            SavetoFile.saveResultToFile(output , fileOutputName);
        }
    }
    
    public static void SPLgauss (Matrix matrix) {
        Matrix matrixG = Gauss.gauss(matrix);
        int type = SolutionType(matrixG);
        String output;
        if (type == 0) {
            output = "SPL tersebut tidak memiliki solusi.\n";
            System.out.println("SPL tersebut tidak memiliki solusi.");
            Menu.subMenuSaveFile();
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output , fileOutputName);
            }
        } else if (type == 1) {
            double A[] = new double[matrix.getRow()];
            double X[] = Substitute(matrixG , A);
            boolean check = true;
            for (int i = 0 ; i < matrixG.getRow() ; i++) {
                if (Double.isNaN(X[i]) || Double.isInfinite(X[i])) {
                    check = false;
                    break;
                }
            }
            if (check) {
                output = "Solusi tunggal :";
                System.out.print("Solusi tunggal :");
                for (int i = 0 ; i < matrixG.getRow() ; i++) {
                    if (i != matrixG.getRow() - 1) {
                        output += String.format(" X%d = %.4f ;" , i + 1 , X[i]);
                        System.out.printf(" X%d = %.4f ;" , i + 1 , X[i]);
                    } else {
                        output += String.format(" X%d = %.4f" , i + 1 , X[i]);
                        System.out.printf(" X%d = %.4f" , i + 1 , X[i]);
                    }
                }
            } else {
                output = "SPL tersebut tidak memiliki solusi.";
                System.out.print("SPL tersebut tidak memiliki solusi.");
            }
            output += "\n";
            System.out.println();
            Menu.subMenuSaveFile();
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output , fileOutputName);
            }
        } else {
            for (int i = 0 ; i < matrix.getRow() ; i++) {
                for (int j = 0 ; j < matrix.getCol() ; j++) {
                    System.out.printf("%.2f " , matrix.getElmt(i , j));
                }
                System.out.println();
            }
            solveParametric(matrixG);
        }
    }

    public static void SPLgaussJordan (Matrix matrix) {
        Matrix matrixGJ = GaussJordan.gaussJordan(matrix);
        int type = SolutionType(matrixGJ);
        String output;
        if (type == 0) {
            output = "SPL tersebut tidak memiliki solusi.\n";
            System.out.println("SPL tersebut tidak memiliki solusi.");
            Menu.subMenuSaveFile();
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output , fileOutputName);
            }
        } else if (type == 1) {
            boolean check = true;
            for (int i = 0 ; i < matrixGJ.getRow() ; i++) {
                if (Double.isNaN(matrixGJ.getElmt(i , matrixGJ.getCol() - 1)) || Double.isInfinite(matrixGJ.getElmt(i , matrixGJ.getCol() - 1))) {
                    check = false;
                    break;
                }
            }
            if (check) {
                output = "Solusi tunggal :";
                System.out.print("Solusi tunggal :");
                for (int i = 0 ; i < matrixGJ.getRow() ; i++) {
                    if (i != matrixGJ.getRow() - 1) {
                        output += String.format(" X%d = %.4f ;" , i + 1 , matrixGJ.getElmt(i , matrixGJ.getCol() - 1));
                        System.out.printf(" X%d = %.4f ;" , i + 1 , matrixGJ.getElmt(i , matrixGJ.getCol() - 1));
                    } else {
                        output += String.format(" X%d = %.4f" , i + 1 , matrixGJ.getElmt(i , matrixGJ.getCol() - 1));
                        System.out.printf(" X%d = %.4f" , i + 1 , matrixGJ.getElmt(i , matrixGJ.getCol() - 1));
                    }
                }
            } else {
                output = "SPL tersebut tidak memiliki solusi.";
                System.out.print("SPL tersebut tidak memiliki solusi.");
            }
            output += "\n";
            System.out.println();
            Menu.subMenuSaveFile();
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output , fileOutputName);
            }
        } else {
            solveParametric(matrixGJ);
        }
    }
}