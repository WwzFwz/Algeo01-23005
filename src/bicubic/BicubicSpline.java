package bicubic;
import matrix.Matrix;
import matrix.InversMatrix;

public class BicubicSpline {

    public static double function(double x, double y,Matrix MatrixY){
        int i,j,k = 0 ;
        double z = 0.0;
        Matrix X = bicubicMatrix();
        Matrix Xinvers = InversMatrix.inversIdentity(X);
        Matrix a = Matrix.multiplyMatrix(Xinvers,MatrixY);
        for (j = 0; j < 4; j++) {
            for (i = 0; i < 4; i++) {
                z += a.getElmt(k, 0) * Math.pow(x, i) * Math.pow(y, j);
                k++;
            }
        }

        return z;
    }

    public static Matrix bicubicMatrix (){
        Matrix X  = new Matrix(16,16);
        int i,j;
        int x,y ;
        int idxCol = 0 ;
        int idxRow = 0;

        // f (x,y) representation   

        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                for (j = 0 ; j < 4; j ++) {
                    for (i = 0 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol,Math.pow(x,i) * Math.pow(y,j));
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }   
        }

        // fx(x,y) representation 
        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                for (j = 0 ; j < 4; j ++) {
                    X.setElmt(idxRow,idxCol,0);
                    idxCol++;
                    for (i = 1 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol, i * Math.pow(x,i-1) * Math.pow(y,j));
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }

        // fy(x,y) representation
        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                for (j = 1 ; j < 4; j ++) {
                    for (i = 0 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol, i * Math.pow(x,i) * Math.pow(y,j-1));
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }

        // fxy(x,y) representation
        for (y = 0 ; y< 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                X.setElmt(idxRow,idxCol,0);
                idxCol++;
                for (j = 1 ; j < 4; j ++) {
                    X.setElmt(idxRow,idxCol,0);
                    idxCol++;
                    for (i = 1 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol,Math.pow(x,i-1) * Math.pow(y,j-1)*i*j);
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }
        return X ;
        
    }

}