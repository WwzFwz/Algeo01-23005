// PROGRAM K14-GeprekMumbul-F04

// IDENTITAS
// Kelompok : 14 - Geprek Mumbul
// NIM/Nama - 1 : 13523021 - Muhammad Raihan Nazhim Oktana
// NIM/Nama - 2 : 13523005 - Muhammad Alfansya
// NIM/Nama - 3 : 13523065 - Dzaky Aurelia Fawwaz
// Instansi : Sekolah Teknik Elektro dan Informatika (STEI) Institut Teknologi Bandung (ITB)
// Jurusan : Teknik Informatika (IF)
// Nama File : Determinant.java
// Topik : Tugas Besar 1 Aljabar Linier dan Geometri 2024 (IF2123-24)
// Tanggal : Kamis, 24 Oktober 2024
// Deskripsi : Subprogram F04 - Determinant
// Penanggung Jawab F04 : 13523021 - Muhammad Raihan Nazhim Oktana

// KAMUS
// matrix : package
// Determinant : class
// DeterminantCOFACTOR , DeterminantOBE : function

// ALGORITMA
package matrix;
import java.util.Scanner;
import utils.Menu;
import utils.ReadFile;
import utils.SavetoFile;

public class Determinant {
    private static final Scanner scanner = new Scanner(System.in);

    public static void menuDeterminant() {
        Menu.menuInput();
        int choice = scanner.nextInt(); 
        scanner.nextLine();
        if (choice == 1) {
            runDeterminantFromKeyboard();
        } else if (choice == 2) {
            System.out.print("Masukkan nama file : ");
            String fileName = scanner.nextLine();
            runDeterminantFromFile(fileName);
        } else {
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
    }

    public static String generateOutputString(double x) {
        return String.format("Determinan = %.4f\n" , x);
    }

    public static void runDeterminantFromFile(String fileName) {
        Matrix data = ReadFile.readMatrixFromFile(fileName);
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("                      PILIH METODE                          ");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Determinan Ekspansi Kofaktor");
        System.out.println("2. Determinan OBE");
        System.out.println("------------------------------------------------------------");
        int choice = scanner.nextInt(); 
        scanner.nextLine();
        double det;
        String output;
        if (data.isSquareMatrix()) {
            if (choice == 1) {
                det = DeterminantCOFACTOR(data);
            } else if (choice == 2) {
                det = DeterminantOBE(data);
            } else {
                det = DeterminantCOFACTOR(data);
            }
            System.out.printf("Determinan = %.4f\n" , det);
            output = generateOutputString(det);
        } else {
            System.out.println("Determinan tidak dapat dihitung karena matrix bukan persegi.");
            output = "Determinan tidak dapat dihitung karena matrix bukan persegi.\n";
        }
        Menu.subMenuSaveFile();
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file : ");
            String fileOutputName = scanner.nextLine();
            SavetoFile.saveResultToFile(output , fileOutputName);
        }
    }

    public static void runDeterminantFromKeyboard() {
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
        System.out.println("1. Determinan Ekspansi Kofaktor");
        System.out.println("2. Determinan OBE");
        System.out.println("------------------------------------------------------------");
        int choice = scanner.nextInt(); 
        scanner.nextLine();
        double det;
        String output;
        if (data.isSquareMatrix()) {
            if (choice == 1) {
                det = DeterminantCOFACTOR(data);
            } else if (choice == 2) {
                det = DeterminantOBE(data);
            } else {
                det = DeterminantCOFACTOR(data);
            }
            System.out.printf("Determinan = %.4f\n" , det);
            output = generateOutputString(det);
        } else {
            System.out.println("Determinan tidak dapat dihitung karena matrix bukan persegi.");
            output = "Determinan tidak dapat dihitung karena matrix bukan persegi.\n";
        }
        Menu.subMenuSaveFile();
        scanner.nextLine();
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file: ");
            String fileOutputName = scanner.nextLine();
            SavetoFile.saveResultToFile(output , fileOutputName);
        }
    }
    
    public static double DeterminantCOFACTOR (Matrix matrix) {
        // SPESIFIKASI LOKAL
        // Menghitung determinan dengan kofaktor.

        // KAMUS LOKAL
        // getRow , getCol , getElmt , setElmt : procedure
        // matrix , part : Matrix
        // i , j , k , col : integer
        // ans : double

        // ALGORITMA LOKAL
        double ans = 0;
        if (matrix.getRow() == 1) {
            return matrix.getElmt(0 , 0);
        }
        for (int i = 0 ; i < matrix.getCol() ; i++) {
            Matrix part = new Matrix(matrix.getRow() - 1 , matrix.getCol() - 1);
            for (int j = 1 ; j < matrix.getRow() ; j++) {
                int col = 0;
                for (int k = 0 ; k < matrix.getCol() ; k++) {
                    if (k != i) {
                        part.setElmt(j - 1 , col , matrix.getElmt(j , k));
                        col++;
                    }
    
                }
            }
            if (i % 2 == 0) {
                ans += matrix.getElmt(0 , i) * DeterminantCOFACTOR(part);
            } else {
                ans -= matrix.getElmt(0 , i) * DeterminantCOFACTOR(part);
            }
        }
        return ans;
    }

    public static double DeterminantCOFACTORPass (Matrix matrix , int row , int col) {
        // SPESIFIKASI LOKAL
        // Menghitung determinan suatu matrix bagian yang mengabaikan suatu row dan col.

        // KAMUS LOKAL
        // getRow , getCol , getElmt , setElmt : procedure
        // i , j , n , row , col : integer
        // matrix , ans : Matrix

        // ALGORITMA LOKAL
		int n = matrix.getRow();
        Matrix ans = new Matrix(n - 1 , n - 1);
        int i = 0;
        while (i < n) {
            if (i == row) {
                i++;
                continue;
            }
            int j = 0;
            while (j < n) {
                if (j == col) {
                    j++;
                    continue;
                }
                if (i < row) {
                    if (j < col) {
						ans.setElmt(i , j , matrix.getElmt(i , j));
                    }
					else {
						ans.setElmt(i , j - 1 , matrix.getElmt(i , j));
                    }
				}
				else{
					if (j < col) {
						ans.setElmt(i - 1 , j , matrix.getElmt(i , j));
                    }
					else {
						ans.setElmt(i - 1 , j - 1 , matrix.getElmt(i , j));
                    }
				}
                j++;
            }
            i++;
        }
		return DeterminantCOFACTOR(ans);
    }

    public static double DeterminantOBE (Matrix matrix) {
        // SPESIFIKASI LOKAL
        // Menghitung determinan dengan OBE (Operasi Baris Elementer).

        // KAMUS LOKAL
        // getRow , getCol , getElmt , setElmt , isSquareMatrix : procedure
        // matrix : Matrix
        // i , j , k , l , row , col : integer
        // x , y , temp , ans : double

        // ALGORITMA LOKAL
        int i , j , k , l;
        double ans = 1;
        double x , y;
        int row = matrix.getRow();
        int col = matrix.getCol();
        if (matrix.isSquareMatrix()) {
            if (row == 1 || col == 1) {
                return matrix.getElmt(0 , 0);
            } else {
                for (i = 0 ; i < row ; i++) {
                    if (i < row) {
                        if (matrix.getElmt(i , i) == 0) {
                            j = i + 1;
                            while (j < row && matrix.getElmt(j , i) == 0) {
                                j++;
                            }
                            if (j < row) {
                                ans *= -1;
                                double temp;
                                for (k = 0 ; k < row ; k++) {
                                    temp = matrix.getElmt(i , k);
                                    matrix.setElmt(i , k , matrix.getElmt(j , k));
                                    matrix.setElmt(j , k , temp);
                                }
                            } else {
                                ans = 0;
                                break;
                            }
                        }
                        x = matrix.getElmt(i , i);
                        ans *= x;
                        if (x != 0) {
                            for (j = 0 ; j < row ; j++) {
                                matrix.setElmt(i , j , matrix.getElmt(i , j) / x);
                            }
                        }
                        for (k = i + 1 ; k < row ; k++) {
                            y = matrix.getElmt(k , i);
                            for (l = 0 ; l < row ; l++) {
                                matrix.setElmt(k , l , matrix.getElmt(k , l) - (y * matrix.getElmt(i , l)));
                            }
                        }
                    }
                }
                return ans;
            }
        } else {
            return Double.NaN;
        }
    }
}