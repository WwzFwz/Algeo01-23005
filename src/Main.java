import matrix.Matrix ;
import matrix.InversMatrix;
public class  Main {
    public static void main (String[] args){
        Matrix m = Matrix.readMatrixFromKeyboard();
        Matrix invers = InversMatrix.inversIdentity(m);
    }


}

