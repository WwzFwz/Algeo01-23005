package function;
import matrix.Matrix;
import matrix.InversMatrix;
import utils.ReadFile;
import utils.SavetoFile;
import matrix.GaussJordan;
import java.util.Scanner;


public class InterpolasiPolinom{
        private double[] coefficients;


        //getter
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
                    augmentedMatrix.setElmt(i,j,Math.pow(xi,j)); // insert xi ,xi^2...xi^n-1 at cols    
                }
            }
            Matrix gaussJordanSolution = GaussJordan.GaussJordan(augmentedMatrix); //find solution
            coefficients = new double[n];
            for (int i = 0 ; i < n ; i ++ ){
                coefficients[i]= gaussJordanSolution.getElmt(i,n);
            }

        }
        public double estimateY(double x){
            double result = 0 ;
            for(int i = 0 ; i < coefficients.length ; i ++){
                result += coefficients[i]* Math.pow(x,i);
            }
            return result ;
        }

        // to string
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
            String result = polynomial.toString().replace("x^0", "").replace("+-", "-").replace("^1", "");
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

            System.out.print("Apakah Anda ingin menyimpan hasil ke file? (y/n): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output, fileOutputName);
            }
        }

        // run from keyboard
        public static void runInterpolationFromKeyboard() {
            Scanner scanner = new Scanner(System.in);
            
            // Menerima input jumlah titik data (n)
            System.out.print("Masukkan jumlah titik data (n): ");
            int n = scanner.nextInt();

            Matrix data = new Matrix(n, 2);

            System.out.println("Masukkan titik-titik data (x y):");
            for (int i = 0; i < n; i++) {
                System.out.print("x" + i + ": ");
                double x = scanner.nextDouble();
                System.out.print("y" + i + ": ");
                double y = scanner.nextDouble();
                data.setElmt(i, 0, x);
                data.setElmt(i, 1, y);
            }

            // Menerima input nilai x yang akan ditaksir
            System.out.print("Masukkan nilai x yang ingin ditaksir: ");
            double x = scanner.nextDouble();

            InterpolasiPolinom interpolasi = new InterpolasiPolinom();
            interpolasi.calculateCoefficient(data);

            double yApprox = interpolasi.estimateY(x);

            String output = interpolasi.generateOutputString(x, yApprox);
            System.out.println(output);


            System.out.print("Apakah Anda ingin menyimpan hasil ke file? (y/n): ");
            scanner.nextLine(); // Membersihkan buffer
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output, fileOutputName);
            }
        }
}

