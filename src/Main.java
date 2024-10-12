import matrix.Matrix ;
import matrix.InversMatrix;
import matrix.BicubicSpline;

public class  Main {
    public static void main (String[] args){
        // Matrix m = Matrix.readMatrixFromKeyboard();
        // Matrix invers = InversMatrix.inversIdentity(m);
        Matrix vikub = BicubicSpline.bicubicMatrix();
        vikub.displayMatrix();
    }


}

