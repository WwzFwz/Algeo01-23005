import function.BicubicSpline ;
import function.InterpolasiPolinom;
import function.RegresiKuadratik;
// import function.RegresiLinier;
import java.util.Scanner;
import matrix.Determinant;
// import matrix.SPL;
import utils.Menu;
import utils.SavetoFile;
import matrix.InversIdentity;
import matrix.InversAdjoin;
import matrix.Matrix;
import utils.ReadFile;
import matrix.GaussJordan;



public class  Main {

    public static  void runSPL(){
        // SPL.menuSPL();
        Matrix m = Matrix.readMatrixFromKeyboard();
        m.displayMatrix();
        Matrix mGauss = GaussJordan.gaussJordan(m);
        mGauss.displayMatrix();

    }

    public static void runDeterminan() {
        Determinant.menuDeterminant();
    }

    public static void runMatrixBalikan(){
        Scanner input = new Scanner(System.in);
        Matrix matrix = null;
        Matrix inverseMatrix = null;
        int choice = 0;
        while (true) {
            Menu.subMenuMatriksBalikan();
            System.out.print("Pilih metode (1/2): ");
            choice = input.nextInt();
            if (choice == 1 || choice == 2) {
                break;
            } else {
                System.out.println("Pilihan tidak valid! Silakan pilih lagi.");
            }
        }
        while (true) {
            Menu.menuInput();
            System.out.print("Pilih metode input (1/2): ");
            int inputChoice = input.nextInt();
            input.nextLine();  

            if (inputChoice == 1) {
                System.out.println("Masukan matrix dari keyboard:");
                matrix = Matrix.readMatrixSquareFromKeyboard();
                break;
            } else if (inputChoice == 2) {
                while (true) {
                    System.out.print("Masukkan nama file : ");
                    String fileName = input.nextLine();

                    matrix = ReadFile.readMatrixFromFile(fileName);

                    if (matrix != null) {
                        break;
                    } 
                    else {
                        System.out.println("File tidak ditemukan atau tidak valid. Silakan coba lagi.");
                    }
                }
                break;
            } else {
                System.out.println("Pilihan tidak valid! Silakan pilih lagi.");
            }
        }

        // Hitung invers berdasarkan metode yang dipilih 
        if (choice == 1) {
            System.out.println("Menggunakan Metode Adjoin...");
            try {
                inverseMatrix = InversAdjoin.inversAdjoin(matrix);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        } else if (choice == 2) {
            System.out.println("Menggunakan Metode Identitas...");
            try {
                inverseMatrix = InversIdentity.inversIdentity(matrix);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        // Menampilkan hasil invers
        if (inverseMatrix != null) {
            System.out.println("Matrix Invers:");
            inverseMatrix.displayMatrix();

            // Opsi untuk menyimpan hasil ke file
            while (true) {
                Menu.subMenuSaveFile();
                System.out.print("Pilih (y/n): ");
                String saveChoice = input.next();

                if (saveChoice.equalsIgnoreCase("y")) {
                    System.out.print("Masukkan nama file untuk menyimpan hasil: ");
                    String outputFileName = input.next();
                    SavetoFile.saveResultToFile(inverseMatrix.toString(), outputFileName);
                    break;
                } else if (saveChoice.equalsIgnoreCase("n")) {
                    System.out.println("Hasil tidak disimpan.");
                    break;
                } else {
                    System.out.println("Pilihan tidak valid! Silakan pilih lagi.");
                }
            }
        } else {
            System.out.println("Tidak bisa menghitung invers matrix.");
        }
    }





    public static void runInterPolinom(){
        InterpolasiPolinom.menuInterpolasi();
    }

    public static void runInterBicub(){
        BicubicSpline.menuBicubic();
    }

    public static void runRegresi(){
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("------------------------------------------------------------");
        // System.out.println("                   MAU REGRESI APA?                     ");
        // System.out.println("------------------------------------------------------------");        
        // System.out.println("------------------------------------------------------------");
        // System.out.println("1. Regresi Linear");
        // System.out.println("2. Regresi Kuadratik");
        // System.out.println("------------------------------------------------------------");
        // while (true){
        //     int pilihan = scanner.nextInt();        
        //     if (pilihan == 1){
        //         RegresiLinier.menuRegresiLinier();
        //         break;
        //     }
        //     else if (pilihan == 2){
        //         RegresiKuadratik.menuRegresiKuadratik();
        //         break;
        //     }
        //     else{
        //         System.out.println("------------------------------------------------------------");
        //         System.out.println("                   Ulangi pilihan                  ");
        //         System.out.println("------------------------------------------------------------");    
        //     }
        // }
    }
    
    public static void main(String[] args) {
        boolean exit = false;
        Menu.welcome();
        Scanner menuScanner = new Scanner(System.in);

        while (!exit) {
            Menu.menu();
            if (menuScanner.hasNextInt()) {
                int pil = menuScanner.nextInt();
                menuScanner.nextLine();  

                switch (pil) {
                    case 1:
                        runSPL();  
                        break;
                    case 2:
                        runDeterminan();  
                        break;
                    case 3:
                        runMatrixBalikan();  
                        break;
                    case 4:
                        runInterPolinom();  
                        break;
                    case 5:
                        runInterBicub();
                        break;
                    case 6:
                        runRegresi();  
                        break;
                    case 7:
                        exit = true;  
                        System.out.println("Terima kasih !");
                        break;
                    default:
                        System.out.println("Masukan tidak valid! Silakan masukkan kembali pilihan.");
                        break;
                }
            } else {
                System.out.println("Masukan tidak valid! Harap masukkan angka.");
                menuScanner.nextLine();  
            }
        }

        menuScanner.close();
        Menu.credit();
    }

}

