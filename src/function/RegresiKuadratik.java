package function;
import matrix.Matrix;
import matrix.InversMatrix;
import utils.ReadFile;
import utils.SavetoFile;
import matrix.GaussJordan;
import java.util.Scanner;
import utils.Menu;

public class RegresiKuadratik {
    private static final Scanner scanner = new Scanner(System.in);


    public static void menuRegresiKuadratik(){
            Menu.menuInput();
            int choice = scanner.nextInt(); 
            scanner.nextLine();
            if (choice == 1){
                runRegKuadratikFromKeyboard();
            }
            else if ( choice == 2){
                System.out.print("Masukkan nama file: ");
                String fileName = scanner.nextLine();
                runRegKuadratikFromFile(fileName);
            }
            else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                return;

            }
        }

        public static void runRegKuadratikFromFile(String fileName){
            Matrix data = ReadFile.readMatrixFromFile(fileName);
            
            Matrix Mat_Xk = data.getRowElmts(data.getRow()-1);
            double[] xk = new double[Mat_Xk.getCol()];
            for (int i = 0; i<Mat_Xk.getCol(); i++){
                xk[i] = Mat_Xk.getElmt(0, i);
            }
            data.deleteLastRow();

            Matrix Mat_y = data.getRowElmts(data.getRow()-1);
            double[] y = new double[Mat_y.getCol()];
            for (int i = 0; i<Mat_y.getCol(); i++){
                xk[i] = Mat_y.getElmt(0, i);
            }
            data.deleteLastRow();

            Matrix x = data.copyMatrix();

            int m = x.getRow();
            int n = x.getCol();

            Matrix matriksAugmented = AugmentMatrix(x, y, n, m);

            Matrix GaussJordan_ed = GaussJordan.GaussJordan(matriksAugmented);
            int kolom = 1 + 2 * n + (n * (n - 1)) / 2;
            double[] koefisien = new double[m];
            for (int i = 0; i<m; i++){
                koefisien[i] = GaussJordan_ed.getElmt(i, kolom);
            }

            DisplayEquation(koefisien, m);

            double TaksiranFXk = estimateYXk(koefisien, xk);
            System.out.println("f(xk) = " + TaksiranFXk);

            String output = RegresiKuadratik.generateOutputString(TaksiranFXk, koefisien);

            Menu.subMenuSaveFile();
            scanner.nextLine(); // Membersihkan buffer
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output, fileOutputName);
            }
        }

        public static void runRegKuadratikFromKeyboard() {

            // Menerima input jumlah peubah x (n)
            System.out.print("Masukkan jumlah peubah x (n): ");
            int n = scanner.nextInt();

            System.out.print("Masukkan jumlah sampel (m): ");
            int m = scanner.nextInt();

            Matrix x = new Matrix(m, n);
            double[] y = new double[m];

            System.out.println("Masukkan nilai-nilai xi:");
            for (int i = 0; i < m; i++) {
                System.out.println("Sampel " + (i + 1) + ":");
                    for (int j = 0; j < n; j++) {
                        System.out.print("x" + (j + 1) + i + " : ");
                        double X = scanner.nextDouble();
                        x.setElmt(i, j, X);
                    }
                System.out.print("y" + i + ": ");
                y[i] = scanner.nextDouble();
            }

            double[] xk = new double[m];
            System.out.println("Masukkan nilai xk untuk menaksir nilai fungsi:");
            for (int j = 0; j < m; j++) {
                System.out.print("xk" + (j + 1) + ": ");
                xk[j] = scanner.nextDouble();
            }

            Matrix matriksAugmented = AugmentMatrix(x, y, n, m);

            Matrix GaussJordan_ed = GaussJordan.GaussJordan(matriksAugmented);
            int kolom = 1 + 2 * n + (n * (n - 1)) / 2;
            double[] koefisien = new double[m];
            for (int i = 0; i<m; i++){
                koefisien[i] = GaussJordan_ed.getElmt(i, kolom);
            }

            DisplayEquation(koefisien, m);

            double TaksiranFXk = estimateYXk(koefisien, xk);
            System.out.println("f(xk) = " + TaksiranFXk);

            String output = RegresiKuadratik.generateOutputString(TaksiranFXk, koefisien);

            Menu.subMenuSaveFile();
            scanner.nextLine(); // Membersihkan buffer
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                System.out.print("Masukkan nama file: ");
                String fileOutputName = scanner.nextLine();
                SavetoFile.saveResultToFile(output, fileOutputName);
            }
        }

        public static Matrix AugmentMatrix(Matrix x, double[] y, int n, int m) {
            // Membentuk matriks augmented untuk sistem persamaan linear
            int kolom = 1 + 2 * n + (n * (n - 1)) / 2; // 1 konstan, n variabel linier, n variabel kuadrat, n(n-1)/2 variabel interaksi
            Matrix augmentedMatrix = new Matrix(kolom, kolom+1);

            // Isi matriks augmented 
            for (int i = 0; i < m; i++) {
                // Konstanta
                augmentedMatrix.setElmt(i, 0, 1);

                for (int j = 0; j < n; j++) {
                    // Variabel linier
                    augmentedMatrix.setElmt(i, j+1, x.getElmt(i, j)); // x1, x2, ..., xn
                    
                    // Variabel kuadrat
                    augmentedMatrix.setElmt(i, n + j + 1, x.getElmt(i, j) * x.getElmt(i, j) ); // x1^2, x2^2, ..., xn^2
                }
                
                int idxInteraksi = 2 * n + 1; // Mulai dari kolom setelah semua variabel kuadrat
                for (int j = 0; j < n; j++) {
                    for (int k = j + 1; k < n; k++) {
                        augmentedMatrix.setElmt(i, idxInteraksi, x.getElmt(i, j) * x.getElmt(i, k)); // x1 * x2, x1 * x3, ..., xn-1 * xn
                        idxInteraksi++;
                    }
                }
                augmentedMatrix.setElmt(i, kolom, y[i]);
            }

            return augmentedMatrix;
        }

        public static void DisplayEquation(double[] koefisien, int n) {
            // Menampilkan persamaan regresi dalam bentuk f(x) = b0 + b1x1 + b2x2 + ...
            System.out.print("Persamaan regresi: f(x) = ");
            System.out.printf("%.4f", koefisien[0]);
    
            for (int i = 1; i <= n; i++) {
                if (koefisien[i] >= 0){
                    System.out.printf(" + %.4fx%d", koefisien[i], i);
                }
                else{
                    System.out.printf(" - %.4fx%d", koefisien[i], i);
                }
            }
            System.out.println();
        }

        public static double estimateYXk(double[] koefisien, double[] xk) {
            // Menghitung nilai taksiran fungsi f(xk)
            double hasil = koefisien[0];
    
            for (int i = 1; i < koefisien.length; i++) {
                hasil += koefisien[i] * xk[i - 1];
            }
    
            return hasil;
        }

        public static String coefficientsToEquation(double[] koefisien){
            StringBuilder polynomial = new StringBuilder("f(x) = ");
            polynomial.append(String.format("%.4f",koefisien[0]));
            for ( int i = 1 ; i <=koefisien.length  ; i ++){
                if (koefisien[i] >= 0 ){
                    polynomial.append(String.format(" + %.4fx%d", koefisien[i], i));
                }
                else{
                    polynomial.append(String.format(" - %.4fx%d", koefisien[i], i));
                }

            }
            String result =  polynomial.toString();
            return result;
        }

        public static String generateOutputString(double yApprox, double[] koefisien) {
            String equation = coefficientsToEquation(koefisien);
            return String.format("%s, f(Xk) = %.4f", equation, yApprox);
        }
}
