import matrix.Matrix ;
import matrix.InversMatrix;
import matrix.BicubicSpline;
import java.util.Scanner;


public class  Main {

    public static void welcome(){
        System.out.println("            Selamat Datang di Kalkulator Matrix Geprek Mumbul           ");
        System.out.println("-------------------------------------------------------------------------");
    }

    public static void Menu(){
        System.out.println("                           MENU                             ");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Sistem Persamaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Interpolasi Bicubic Spline");
        System.out.println("6. Regresi Linier dan Kuadratik Berganda");
        System.out.println("7. Keluar\n");
        System.out.println("------------------------------------------------------------");
    }
    public static void subMenuSPL(){
        System.out.println("------------------------------------------------------------");
        System.out.println("                   MAU PAKE METODE APA?                     ");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Metode Eliminasi Gauss");
        System.out.println("2. Metode Eliminasi Gauss-Jordan");
        System.out.println("3. Metode Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.println("------------------------------------------------------------");

    }

    public static void subMenuDeterminan(){
        System.out.println("------------------------------------------------------------");
        System.out.println("                   MAU PAKE METODE APA?                     ");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Metode Kofaktor");
        System.out.println("2. Metode OBE");
        System.out.println("------------------------------------------------------------");
    }
    public static void subMenuMatriksBalikan(){
        System.out.println("------------------------------------------------------------");
        System.out.println("                   MAU PAKE METODE APA?                     ");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Metode Adjoin");
        System.out.println("2. Metode Identitas");
        System.out.println("------------------------------------------------------------");
    }
    public static void subMenuRegresi(){
        System.out.println("------------------------------------------------------------");
        System.out.println("                   MAU JENIS APA?                     ");
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Regresi Linear Berganda");
        System.out.println("2. Regresi Kuadratik Berganda");
        System.out.println("------------------------------------------------------------");
    }
    public static void menuInput(){
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("                      MENU INPUT                            ");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Masukan dari Keyboard");
        System.out.println("2. Masukan dari File");
        System.out.println("------------------------------------------------------------");

    }
    public static void credit(){
        System.out.println("------------------------------------------------------------");
        System.out.println("------------------------------------------------------------"); 
        System.out.println("                     ALHAMDULILLAH KELAR                    ");
        System.out.println("------------------------------------------------------------");
        System.out.println("                      BISMILLAH DAPET A                     ");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Muhammad Alfansya/13523005");
        System.out.println("2. Muhammad Raihan Nazhim Oktana/13523021");
        System.out.println("2. Dzaky Aurelia Fawwaz/13523065");
        System.out.println("------------------------------------------------------------");

    }
    public static  void runSPL(){
    }

    public static void runDeterminan(){
    }

    public static void runMatrixBalikan(){

    }

    public static void runInterPolinom(){
    }

    public static void runInterBicub(){

    }

    public static void runRegresi(){

    }
    public static void main (String[] args){

        boolean exit = false ;
        welcome();
        Scanner menuScanner = new Scanner(System.in);
        while (exit == false ){
            int pil = menuScanner.nextInt();
            if(pil == 1){
                runSPL();
            }
            else if (pil == 2){
                runDeterminan();
            }
            else if (pil == 3) {
                runMatrixBalikan();
            }
            else if (pil == 4 ){
                runInterPolinom();
            }
            else if (pil == 5){
                runInterBicub();
            }
            else if (pil == 6){
                runRegresi();
            }
            else if (pil == 7){
                exit = true ;
                menuScanner.close();
            }
            else {
                System.out.printf("Masukan tidak valid! silahkan masukkan kembali pilihan \n");
            }

        }

        credit();

    }


}

