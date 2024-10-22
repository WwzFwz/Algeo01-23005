import function.BicubicSpline ;
import function.InterpolasiPolinom;
import java.util.Scanner;
import matrix.Determinant;
import matrix.SPL;
import utils.Menu;


public class  Main {

    public static  void runSPL(){
        SPL.menuSPL();
    }

    public static void runDeterminan() {
        Determinant.menuDeterminant();
    }

    public static void runMatrixBalikan(){

    }

    public static void runInterPolinom(){
        InterpolasiPolinom.menuInterpolasi();
    }

    public static void runInterBicub(){
        BicubicSpline.menuBicubic();
    }

    public static void runRegresi(){

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

