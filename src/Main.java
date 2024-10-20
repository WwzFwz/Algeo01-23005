import matrix.Matrix ;
import utils.SavetoFile;
import utils.ReadFile;
import utils.Menu;
import matrix.InversMatrix;
import function.BicubicSpline;
import java.util.Scanner;


public class  Main {

    public static  void runSPL(){
        // Matrix m = Matrix.readMatrixFromKeyboard();
        // Matrix gauss = GaussJordan.GaussJordan(m);
        // gauss.displayMatrix();


        

    }

    public static void runDeterminan(){
    }

    public static void runMatrixBalikan(){

    }

    public static void runInterPolinom(){
    }

    public static void runInterBicub(){
        BicubicSpline.menuBicubic();
    }

    public static void runRegresi(){

    }
    public static void main (String[] args){

        boolean exit = false ;
        Menu.welcome();
        Scanner menuScanner = new Scanner(System.in);
        while (exit == false ){
            Menu.menu();
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

        Menu.credit();

    }


}

