// PROGRAM K14-GeprekMumbul-F09

// IDENTITAS
// Kelompok : 14 - Geprek Mumbul
// NIM/Nama - 1 : 13523021 - Muhammad Raihan Nazhim Oktana
// NIM/Nama - 2 : 13523005 - Muhammad Alfansya
// NIM/Nama - 3 : 13523065 - Dzaky Aurelia Fawwaz
// Instansi : Sekolah Teknik Elektro dan Informatika (STEI) Institut Teknologi Bandung (ITB)
// Jurusan : Teknik Informatika (IF)
// Nama File : RegresiLinier.java
// Topik : Tugas Besar 1 Aljabar Linier dan Geometri 2024 (IF2123-24)
// Tanggal : Sabtu, 19 Oktober 2024
// Deskripsi : Subprogram F09 - Regresi Linier
// Penanggung Jawab F09 : 13523021 - Muhammad Raihan Nazhim Oktana

// KAMUS
// ...

// ALGORITMA
package function;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import matrix.*;
public class RegresiLinier {
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

    public static void regresiLinearKeyboard(Matrix m) {
        int i, j;
        double temp = 0;
        double res = 0;
        double sum = 0;
        Matrix matrixT;
        double [] x;
        BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));

        x = new double [m.getCol() - 1];
        //Input nilai-nilai xk yang ingin ditaksir
        System.out.println("Masukkan nilai x yang ingin ditaksir: ");
        for(i = 0; i < x.length; i++) {
            x[i] = InputMatrix.input.nextDouble();
        }
        //Membuat matriks baru semacam SPL
        matrixT = new Matrix(m.getCol(), m.getCol() + 1);

        for(i = 0 ; i < matrixT.getRow() ; i++) {
            for(j = 0 ; j < matrixT.getCol() ; j++) {
                if(i == 0 && j == 0) {
                    temp = m.getRow();
                    
                }
                else if (i == 0 && j > 0) {
                    temp = Matrix.sumCol(m, j - 1);
                }
                else if (j == 0 && i > 0) {
                    temp = Matrix.sumCol(m, i - 1);
                }
                else if (i > 0 && j > 0) {
                    temp = Matrix.sumMultiplyCol(m, i-1, j-1);
                }
                matrixT.setElmt(i, j, temp);
            }
        }
        
        // Melakukan Eliminasi Gauss
        matrixT = Matrix.gaussElimination(matrixT);
        double[] matrix1 = new double [matrixT.getRow()];
        Matrix.backSubstitution(matrixT, matrix1); 

        System.out.print("f(x) = ");
        for (i = 0; i < matrixT.getRow(); i++) {
            if (i == 0) {
                res = matrix1[i];
                if (matrix1[i] > 0) {
                    System.out.printf("%.2f ", matrix1[i]);
                } else {
                    matrix1[i] *= -1;
                    System.out.printf("- %.2f ", matrix1[i]);
                }
            } else if ( i > 0 && i < matrixT.getRow() - 1) {
                res = matrix1[i] * x[i - 1];
                if (matrix1[i] > 0) {
                    System.out.printf("+ %.4fx%d ", matrix1[i], i);
                } else {
                    matrix1[i] *= -1;
                    System.out.printf("- %.4fx%d ", matrix1[i], i);
                }
            } else if (i == matrixT.getRow() - 1) {
                res = matrix1[i] * x[i - 1];
                if (matrix1[i] > 0) {
                    System.out.printf("+ %.4fx%d, ", matrix1[i], i);
                } else {
                    matrix1[i] *= -1;
                    System.out.printf("- %.4fx%d, ", matrix1[i], i);
                }
            }
            sum += res;
        }
        System.out.printf("f(xk) = %.2f\n", sum);

        int pil = OutputMatrix.printMenuOutput();
        if (pil == 1) {
            //Mencetak ouput ke dalam bentuk file
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
                file.write("f(x) = ");
                for (i = 0; i < matrixT.getRow(); i++) {
                    if (i == 0) {
                        if (matrix1[i] > 0) {
                            file.write(String.format("%.2f", matrix1[i]));
                        } else {
                            matrix1[i] *= -1;
                            file.write("- "+String.format("%.2f", matrix1[i]));
                        }
                    } else if (i > 0 && i < matrixT.getRow() - 1) {
                        res = matrix1[i] * x[i - 1];
                        if (matrix1[i] > 0) {
                            file.write("+ "+ String.format("%.2f", matrix1[i])+"x"+Integer.toString(i));
                        } else {
                            matrix1[i] *= -1;
                            file.write("- "+ String.format("%.2f", matrix1[i])+"x"+Integer.toString(i));
                        }
                    } else if (i == matrixT.getRow() - 1) {
                        res = matrix1[i] * x[i - 1];
                        if (matrix1[i] > 0) {
                            file.write("+ "+String.format("%.2f", matrix1[i])+"x^"+Integer.toString(i));
                        } else {
                            matrix1[i] *= -1;
                            file.write("- "+String.format("%.2f", matrix1[i])+"x^"+Integer.toString(i));
                        }
                    }
                }
                file.write("\nf(xk) = "+String.format("%.2f", sum));
                file.close();
            }
            catch(IOException err) {
                err.printStackTrace();
            }
        }
    }

    public static void regresiLinearFile (Matrix matrix) {
        int i , j;
        double res = 0;
        double sum = 0;
        double[] x;
        BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));
        Matrix matrix1 = new Matrix(matrix.getRow() - 1 , matrix.getCol());
        for (i = 0 ; i < matrix.getRow() - 1 ; i++) {
            for (j = 0 ; j < matrix.getCol() ; j++) {
                matrix1.setElmt(i , j , matrix.getElmt(i , j));
            }
        }
        double[] array = new double[matrix.getCol() - 1];
        for (i = 0 ; i < matrix.getCol() - 1 ; i++) { 
            array[i] = matrix.getElmt(matrix.getRow() - 1 , i);
        }
        Matrix matrixT = new Matrix(matrix.getCol() , matrix.getCol() + 1);
        double temp;
        for (i = 0 ; i < matrixT.getRow() ; i++) {
            for (j = 0 ; j < matrixT.getCol() ; j++) {
                if (i == 0 && j == 0) {
                    temp = matrix1.getRow();
                }
                else if (i == 0 && j > 0) {
                    temp = Matrix.sumCol(matrix1, j - 1);
                }
                else if (j == 0 && i > 0) {
                    temp = Matrix.sumCol(matrix1, i - 1);
                }
                else if (i > 0 && j > 0) {
                    temp = Matrix.sumMultiplyCol(matrix1, i-1, j-1);
                }
                matrixT.setElmt(i , j , temp);
            }
        }
        
        // Melakukan Eliminasi Gauss
        matrixT = Matrix.gauss(matrixT);
        x = new double [matrixT.getRow()];
        Matrix.backSubstitution(matrixT, x); 

        System.out.print("f(x) = ");
        for (i = 0; i < matrixT.getRow(); i++) {
            if (i == 0) {
                res = x[i];
                if (x[i] > 0) {
                    System.out.printf("%.2f ", x[i]);
                } else {
                    x[i] *= -1;
                    System.out.printf("- %.2f ", x[i]);
                }
            } else if ( i > 0 && i < matrixT.getRow() - 1) {
                res = x[i] * array[i - 1];
                if (x[i] > 0) {
                    System.out.printf("+ %.4fx%d ", x[i], i);
                } else {
                    x[i] *= -1;
                    System.out.printf("- %.4fx%d ", x[i], i);
                }
            } else if (i == matrixT.getRow() - 1) {
                res = x[i] * array[i - 1];
                if (x[i] > 0) {
                    System.out.printf("+ %.4fx%d, ", x[i], i);
                } else {
                    x[i] *= -1;
                    System.out.printf("- %.4fx%d, ", x[i], i);
                }
            }
            sum += res;
        }
         System.out.printf("f(xk) = %.2f\n", sum);
         
        int pil = MenuOutput();
        if (pil == 1) {
            //Mencetak ouput ke dalam bentuk file
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
                file.write("f(x) = ");
                for (i = 0; i < matrixT.getRow(); i++) {
                    if (i == 0) {
                        if (x[i] > 0) {
                            file.write(String.format("%.2f", x[i]));
                        } else {
                            x[i] *= -1;
                            file.write("- "+String.format("%.2f", x[i]));
                        }
                    } else if (i > 0 && i < matrixT.getRow() - 1) {
                        res = x[i] * array[i - 1];
                        if (x[i] > 0) {
                            file.write("+ "+ String.format("%.2f", x[i])+"x"+Integer.toString(i));
                        } else {
                            x[i] *= -1;
                            file.write("- "+ String.format("%.2f", x[i])+"x"+Integer.toString(i));
                        }
                    } else if (i == matrixT.getRow() - 1) {
                        res = x[i] * array[i - 1];
                        if (x[i] > 0) {
                            file.write("+ "+String.format("%.2f", x[i])+"x^"+Integer.toString(i));
                        } else {
                            x[i] *= -1;
                            file.write("- "+String.format("%.2f", x[i])+"x^"+Integer.toString(i));
                        }
                    }
                }
                file.write("\nf(xk) = "+String.format("%.2f", sum));
                file.close();
            }
            catch(IOException err) {
                err.printStackTrace();
            }
        }
    }
}
