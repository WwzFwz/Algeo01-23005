package matrix ;
public class InversMatrix {
    // Membuat matrix identitas
    public static Matrix createIdentityMatrix(int size){
        int i;
        Matrix identityMatrix = new Matrix(size,size);
        for (i = 0 ; i< size; i ++) {
            identityMatrix.setElmt(i,i,1);
        }
        return identityMatrix;
    }


    public static Matrix inversIdentity (Matrix matrix) {
        // Mengirimkan invers balikan , Matrix matrix merupakan matrix persegi (n x n)
        int i ,j,k;
        int length = matrix.getCol();
        Matrix augmentedMatrix = new Matrix(length, length*2);
        Matrix identityMatrix  = createIdentityMatrix(length);

        for(i = 0 ; i < length; i ++) {
            for (j=0; j < length ; j ++) {
                augmentedMatrix.setElmt(i,j,matrix.getElmt(i,j));
                augmentedMatrix.setElmt(i,j + length, identityMatrix.getElmt(i,j));
            }
        }
        for ( i = 0; i < length; i++) {
            // augmentedMatrix.displayMatrix();

            double pivot = augmentedMatrix.getElmt(i, i);
            if (pivot == 0) {
                throw new IllegalArgumentException("Matriks tidak dapat di-inverskan");
            }
            // Bagi tiap elemen pada baris i dengan elemen i,i(pivot)
            for (j = 0; j < 2 * length; j++) {
                augmentedMatrix.setElmt(i, j, augmentedMatrix.getElmt(i, j) / pivot);
            }

            //Eliminasi baris lain dengan gauss jordan 
            for (k = 0; k < length; k ++) {
                if (k != i) {
                    double factor = augmentedMatrix.getElmt(k,i);
                    for (j=0; j < 2*length; j ++){
                        augmentedMatrix.setElmt(k,j,augmentedMatrix.getElmt(k,j)-factor*augmentedMatrix.getElmt(i,j));
                    }
                }
            }
        }
        Matrix inverseMatrix = new Matrix(length, length);
        for (i = 0; i < length; i++) {
            for (j = 0; j < length; j++) {  
                inverseMatrix.setElmt(i, j, augmentedMatrix.getElmt(i, j + length));
            }
        }
        return inverseMatrix;


    }
}
