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
// Tanggal : Sabtu, 19 Oktober 2024
// Deskripsi : Subprogram F11 - SPL (Sistem Persamaan Linier)
// Penanggung Jawab F11 : 13523021 - Muhammad Raihan Nazhim Oktana

// KAMUS
// ...

// ALGORITMA
package function;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import matrix.*;
public class SPL {
    public static int MenuOutput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------------------------------------------------------------");
        System.out.println("                       MENU OUTPUT                          ");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Buat Output File");
        System.out.println("2. Tidak Menghasilkan Output File");
        System.out.println("------------------------------------------------------------");
        System.out.print("Masukkan pilihan Anda : ");
        int pil = scanner.nextInt();
        while (pil != 1 && pil != 2) {
            System.out.println("Masukan tidak valid! Silakan ulangi inputan pilihan.\n");
            System.out.print("Masukkan pilihan Anda : ");
            pil = scanner.nextInt();
        }
        return pil;
    }

    public static void SPLinvers (Matrix matrix) {
        int i, j;
        BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));
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
        if ((matrix.getRow() != matrix.getCol() - 1) || (Determinant.DeterminantCOFACTOR(matrixA) == 0)) {
            int pil = MenuOutput();
            if (pil == 1) {
                String newfileName = "";
                System.out.print("Masukkan nama file : ");
                try {
                    newfileName = inputFile.readLine();
                    String path = "test/Output/" + newfileName;
                } catch (IOException err) {
                    err.printStackTrace();
                }
                try {
                    FileWriter file = new FileWriter("test/Output/" + newfileName);
                    file.write("SPL tidak dapat diselesaikan dengan metode invers.");
                    file.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            } else {
                System.out.println("SPL tidak dapat diselesaikan dengan metode invers.");
            }
        } else {
            Matrix MatrixX = new Matrix (matrix.getRow() , 1);
            Matrix matrixI = InversMatrix.inversIdentity(matrixA);
            MatrixX = Matrix.multiplyMatrix(matrixI , matrixB);
            int pil = MenuOutput();
            if (pil == 1) {
                String newfileName = "";
                System.out.print("Masukkan nama file: ");
                try {
                    newfileName = inputFile.readLine();
                    String path = "test/Output/" + newfileName;
                } catch (IOException err) {
                    err.printStackTrace();
                }
                try {
                    FileWriter file = new FileWriter("test/Output/" + newfileName);
                    for (i = 0 ; i < MatrixX.getRow() ; i++) {
                        if (i != MatrixX.getRow() - 1) {
                            double temp = x.getElmt(i, 0);
                            file.write("X" + Integer.toString(i + 1) + " = " + String.format("%.2f" , temp) + "\n");
                        }
                        else {
                            file.write("X" + Integer.toString(i + 1) + " = " + String.format("%.2f" , MatrixX.getElmt(i , 0)));
                        }
                    }
                    file.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            } else {
                for (i = 0 ; i < MatrixX.getRow() ; i++) {
                    System.out.printf("X%d = %.2f\n" , i + 1 , MatrixX.getElmt(i , 0));
                }
            }
        }
    }

    public static void SPLcramer (Matrix mCramer) {
        Matrix matrixA;
        Matrix matrixB;
        Matrix x;
        int i, j;
        double det;
        Matrix matrixT;
        BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));

        // Input matrixA dan matrixB (Memisahkan augmented matrix)
        matrixA = new Matrix(mCramer.getRow(), mCramer.getCol()-1);
        matrixB = new Matrix(mCramer.getRow(), 1);
        for (i = 0; i < mCramer.getRow(); i++) {
            for (j = 0; j < mCramer.getCol(); j++) {
                if (j != mCramer.getCol()-1) {
                    matrixA.setElmt(i, j, mCramer.getElmt(i, j));
                }
                else{
                    matrixB.setElmt(i, 0, mCramer.getElmt(i, j));
                }
                
            }
        }
        
        // Jika determinan 0, maka SPL tidak dapat diselesaikan dengan metode cramer.
        if ((mCramer.getRow() != mCramer.getCol()-1) || Determinan.detKofaktor(matrixA) == 0) {
            System.out.println("SPL tidak dapat diselesaikan dengan metode cramer.");
            // output file
            int pil = OutputMatrix.printMenuOutput();
            if (pil == 1) {
                String newfileName = "";
                System.out.print("Masukkan nama file: ");
                try{
                    newfileName = inputFile.readLine();
                    String path = "test/Output/" + newfileName;
                }
                catch(IOException err) {
                    err.printStackTrace();
                }
                try{
                    FileWriter file = new FileWriter("test/Output/" + newfileName);
                    file.write("SPL tidak dapat diselesaikan dengan metode cramer.");
                    file.close();
                }
                catch(IOException err) {
                    err.printStackTrace();
                }
            }
        } else {
            // Konstruktor x
            x = new Matrix(mCramer.getRow(), 1);
            // Determinan
            det = Determinan.detKofaktor(matrixA);
            matrixT = matrixA;
            // Mengloop sehingga setiap kolom diubah dengan matrixB kemudian dicari nilai x[kolom]nya.
            for (j = 0; j < matrixA.getRow(); j++) {
                for (i = 0; i < matrixA.getRow(); i++) {
                    matrixT.setElmt(i, j, mCramer.getElmt(i, matrixA.getRow()));
                }
                double solution = Determinan.detKofaktor(matrixT)/det;
                System.out.printf("X%d = %.2f\n", (j+1), (solution));
                for (i = 0; i < matrixA.getRow(); i++) {
                    matrixT.setElmt(i, j, mCramer.getElmt(i, j));
                }
            }
            // output file
            int pil = OutputMatrix.printMenuOutput();
            if (pil == 1) {
                String newfileName = "";
                System.out.print("Masukkan nama file: ");
                try{
                    newfileName = inputFile.readLine();
                    String path = "test/Output/" + newfileName;
                }
                catch(IOException err) {
                    err.printStackTrace();
                }
                try{
                    FileWriter file = new FileWriter("test/Output/" + newfileName);
                    for (j = 0; j < matrixA.getRow(); j++) {
                        for (i = 0; i < matrixA.getRow(); i++) {
                            matrixT.setElmt(i, j, mCramer.getElmt(i, matrixA.getRow()));
                        }
                        double solution = Determinan.detKofaktor(matrixT)/det;
                        file.write("X"+Integer.toString(j+1)+" = "+String.format("%.2f", solution)+"\n");
                        for (i = 0; i < matrixA.getRow(); i++) {
                            matrixT.setElmt(i, j, mCramer.getElmt(i, j));
                        }
                    }
                    file.close();
                }
                catch(IOException err) {
                    err.printStackTrace();
                }
            }
        }
    }

    public static void gaussSPL (Matrix matrix) {
        BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));
        //Melakukan eliminasi gauss 
        matrix = Matrix.gaussElimination(matrix);
        double X[] = new double[matrix.getRow()];
        //Menganalisis matriks hasil eliminasi gauss apakah memiliki solusi tidak ada, unik atau banyak
        int solutionType = Matrix.SolutionType(matrix);
        int pil;
        Matrix.backSubstitution(matrix , X);

        if (solutionType == 0) {
            System.out.println("Solusi tidak ada.");
            pil = OutputMatrix.printMenuOutput();
            //Mencetak SPL dalam bentuk file
            if(pil == 1) {
                String nameFile = "";
                System.out.println("Masukkan nama file: ");
                try {
                    nameFile = inputFile.readLine();
                    String path = "test/Output/" + nameFile;
                }
                catch (IOException err) {
                    err.printStackTrace();
                }
                try {
                    FileWriter file = new FileWriter("test/Output/" + nameFile);
                    file.write("Solusi tidak ada.");
                    file.close();
                }
                catch(IOException err) {
                    err.printStackTrace();
                }
            }
        } else if (solutionType == 1) {
            System.out.println("Solusi tunggal:");
            for (int i = 0; i < matrix.getRow(); i++) {
                System.out.printf("X[%d] = %.2f%n", i + 1, X[i]);
            }
            pil = OutputMatrix.printMenuOutput();
            //Mencetak SPL dalam bentuk file
            if(pil == 1) {
                String nameFile = "";
                System.out.println("Masukkan nama file: ");
                try {
                    nameFile = inputFile.readLine();
                    String path = "test/Output/" + nameFile;
                }
                catch (IOException err) {
                    err.printStackTrace();
                }
                try {
                    FileWriter file = new FileWriter("test/Output/" + nameFile);
                    file.write("Solusi tunggal:\n");
                    for (int i = 0; i < matrix.getRow(); i++) {
                        String tempString = String.format("%.2f", X[i]);
                        String tempIndex = Integer.toString(i+1);
                        if (i == matrix.getRow() - 1) {
                            file.write("X" + tempIndex + " = " + tempString);
                        } else {
                            file.write("X" + tempIndex + " = " + tempString+ "\n");
                        }
                    }
                    file.close();
                }
                catch(IOException err) {
                    err.printStackTrace();
                }
            } 
        } else {
                //Jika solusi berupa parametrik maka memanggil fungsi solusi parametrik
                System.out.println("Solusi banyak (parametrik):");
                Matrix.solveManySolution(matrix);
     }
}

    public static void gaussJordanSPL (Matrix Mgajo) {
        //Melakukan eliminasi gauss jordan
        Mgajo = Matrix.gaussJordanElimination(Mgajo);
        int pil;
        BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));
        int solutionType = Matrix.SolutionType(Mgajo);
        //Menganalisis matriks hasil eliminasi gauss apakah memiliki solusi tidak ada, unik atau banyak
        if (solutionType == 0) {
            System.out.println("Solusi tidak ada.");
            pil = OutputMatrix.printMenuOutput();
            if(pil == 1) {
                //Mencetak out SPL ke dalam bentuk file
                String nameFile = "";
                System.out.println("Masukkan nama file: ");
                try {
                    nameFile = inputFile.readLine();
                    String path = "test/Output/" + nameFile;
                }
                catch (IOException err) {
                    err.printStackTrace();
                }
                try {
                    FileWriter file = new FileWriter("test/Output/" + nameFile);
                    file.write("Solusi tidak ada.");
                    file.close();
                }
                catch(IOException err) {
                    err.printStackTrace();
                }
            }
        } else if (solutionType == 1) {
            System.out.println("Solusi tunggal:");
            for (int i = 0; i < Mgajo.getRow(); i++) {
                System.out.printf("X[%d] = %.2f%n", i + 1, Mgajo.matrix[i][Mgajo.getCol() - 1]);
            }
            pil = OutputMatrix.printMenuOutput();
            if(pil == 1) {
                 //Mencetak out SPL ke dalam bentuk file
                String nameFile = "";
                System.out.println("Masukkan nama file: ");
                try {
                    nameFile = inputFile.readLine();
                    String path = "test/Output/" + nameFile;
                }
                catch (IOException err) {
                    err.printStackTrace();
                }
                try {
                    FileWriter file = new FileWriter("test/Output/" + nameFile);
                    file.write("Solusi tunggal:\n");
                    for (int i = 0; i < Mgajo.getRow(); i++) {
                        String tempString = String.format("%.2f", Mgajo.matrix[i][Mgajo.getCol() - 1]);
                        String tempIndex = Integer.toString(i + 1);
                        if (i == Mgajo.getRow() - 1) {
                            file.write("X" + tempIndex + " = " + tempString);
                        } else {
                            file.write("X" + tempIndex + " = " + tempString+ "\n");
                        }
                    }
                    file.close();
                }
                catch(IOException err) {
                    err.printStackTrace();
                }
            } 
            } else {
                System.out.println("Solusi banyak (parametrik):");
                //Jika solusi berupa parametrik maka memanggil fungsi solusi parametrik
                Matrix.solveManySolution(Mgajo);
        }
    }
}