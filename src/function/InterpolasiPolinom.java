package function;
import java.util.Scanner;
import matrix.GaussJordan;
import matrix.Matrix;
import utils.Menu;
import utils.ReadFile;
import utils.SavetoFile;
import java.util.InputMismatchException;



public class InterpolasiPolinom{
        private double[] coefficients;
        private static final Scanner scanner = new Scanner(System.in);


        public static void menuInterpolasi(){
            // menampilkan menu interpolasi
            while (true) {
                try {
                    Menu.menuInput();
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (choice == 1) {
                        runInterpolationFromKeyboard();
                        break;
                    } else if (choice == 2) {
                        System.out.print("Masukkan nama file: ");
                        String fileName = scanner.nextLine();
                        runInterpolationFromFile(fileName);
                        break;
                    } else {
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input tidak valid! Ulangi");
                    scanner.next(); 
                }
            }
        }
        //getter koefisien
        public double[] getCoefficients(){

            return this.coefficients;
        }
        // method cari koefisien
        public void calculateCoefficient(Matrix data){
            int n = data.getRow();
            Matrix augmentedMatrix = new Matrix (n ,n +1);
            for (int i = 0 ; i < n ; i ++){
                double xi = data.getElmt(i,0); //col 1
                double yi = data.getElmt(i,1); //col 2
                augmentedMatrix.setElmt(i,n,yi); // insert yi at last col
                for (int j = 0 ; j < n ; j ++){
                    augmentedMatrix.setElmt(i,j,Math.pow(xi,j)); 
                }
            }
            Matrix gaussJordanSolution = GaussJordan.gaussJordan(augmentedMatrix); //find solution
            coefficients = new double[n];
            for (int i = 0 ; i < n ; i ++ ){
                coefficients[i]= gaussJordanSolution.getElmt(i,n);
            }

        }
        // Mengestimani nilai f(x)
        public double estimateY(double x){
            double result = 0 ;
            for(int i = 0 ; i < coefficients.length ; i ++){
                result += coefficients[i]* Math.pow(x,i);
            }
            return result ;
        }

        // Mengubah data koefisien ke bentuk persamaan
        public String coefficientsToEquation(){
            StringBuilder polynomial = new StringBuilder("f(x) = ");
            for ( int i = coefficients.length-1 ; i >= 0 ; i --){
                if (coefficients[i] != 0 ){
                    if (i == coefficients.length -1){
                        polynomial.append(String.format("%.4fx^%d",coefficients[i],i));
                    }
                    else {
                        if (coefficients[i] > 0){
                            polynomial.append(String.format(" + %.4fx^%d",coefficients[i],i));

                        } else {
                            polynomial.append(String.format(" - %.4fx^%d",coefficients[i],i));
                        }
                    }


                }
            }
            String result = polynomial.toString().replace("x^0", "").replace("+-", "-").replace("^1", "").replace("- -", "+ ");
            return result;
        }   

        public void saveInterpolToFile(String fileName){
            String output = coefficientsToEquation();
            SavetoFile.saveResultToFile(output,fileName);
        }

        public String generateOutputString(double x, double yApprox) {
            String equation = coefficientsToEquation();
            return String.format("%s, f(%.4f) = %.4f", equation, x, yApprox);
        }
        // run from file

        public static void runInterpolationFromFile(String fileName) {
            Matrix data = ReadFile.readMatrixFromFile(fileName);

            double x = data.getElmt(data.getRow()-1, 0); // data 

            // Data x, y ada di baris sebelumnya
            int lastRow = data.getRow()-1;
            Matrix xyData = new Matrix(lastRow, 2);
            for (int i = 0; i < lastRow; i++) {
                xyData.setElmt(i, 0, data.getElmt(i, 0));
                xyData.setElmt(i, 1, data.getElmt(i, 1));
            }

            InterpolasiPolinom interpolasi = new InterpolasiPolinom();
            interpolasi.calculateCoefficient(xyData); // kalkulasi interpolasi

            // hasil estimasi
            double yApprox = interpolasi.estimateY(x);

            // Menampilkan hasil
            String output = interpolasi.generateOutputString(x, yApprox);
            System.out.println(output);
            while (true) {
                Menu.subMenuSaveFile();
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    System.out.print("Masukkan nama file: ");
                    String fileOutputName = scanner.nextLine();
                    SavetoFile.saveResultToFile(output, fileOutputName);
                    break;
                } else if (response.equalsIgnoreCase("n")) {
                    System.out.println("Hasil tidak disimpan.");
                    break;
                } else {
                    System.out.println("Input tidak valid! Harap masukkan y atau n.");
                }
            }
        }

        // run from keyboard
        public static void runInterpolationFromKeyboard() {
            
            // Menerima input jumlah titik data (n)
            System.out.print("Masukkan jumlah titik data (n): ");
            int n = scanner.nextInt();

            Matrix data = new Matrix(n, 2);

            System.out.println("Masukkan titik-titik data (x y):");
            for (int i = 0; i < n; i++) {
                double x = 0, y = 0;
                while (true) {
                    try {
                        System.out.print("x" + i + ": ");
                        x = scanner.nextDouble();
                        System.out.print("y" + i + ": ");
                        y = scanner.nextDouble();
                        break;  
                    } catch (InputMismatchException e) {
                        System.out.println("Input tidak valid! Masukkan angka yang benar.");
                        scanner.next(); // Membersihkan buffer
                    }
                }
                data.setElmt(i, 0, x);
                data.setElmt(i, 1, y);
            }

            // Menerima input nilai x 
            double x = 0;
            while (true) {
                try {
                    System.out.print("Masukkan nilai x yang ingin ditaksir: ");
                    x = scanner.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Input tidak valid! Masukkan angka .");
                    scanner.next(); // Membersihkan buffer
                }
            }

            InterpolasiPolinom interpolasi = new InterpolasiPolinom();
            interpolasi.calculateCoefficient(data);

            double yApprox = interpolasi.estimateY(x);

            String output = interpolasi.generateOutputString(x, yApprox);
            System.out.println(output);


            while (true) {
                Menu.subMenuSaveFile();
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    System.out.print("Masukkan nama file: ");
                    String fileOutputName = scanner.nextLine();
                    SavetoFile.saveResultToFile(output, fileOutputName);
                    break;
                } else if (response.equalsIgnoreCase("n")) {
                    System.out.println("Hasil tidak disimpan.");
                    break;
                } else {
                    System.out.println("Input tidak valid! Harap masukkan y atau n.");
                }
            }
        }
}
