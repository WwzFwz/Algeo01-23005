package matrix ;
public class InversIdentity {
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
        // try {
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
                // System.out.printf("%d\n", i); 
                // augmentedMatrix.displayMatrix();

                double pivot = augmentedMatrix.getElmt(i, i);
                if (pivot == 0) {
                    boolean swapped = false;
                    for (int swapRow = i + 1; swapRow < length; swapRow++) {
                        if (augmentedMatrix.getElmt(swapRow, i) != 0) {
                            // Tukar baris i dengan baris swapRow
                            for (j = 0; j < 2 * length; j++) {
                                double temp = augmentedMatrix.getElmt(i, j);
                                augmentedMatrix.setElmt(i, j, augmentedMatrix.getElmt(swapRow, j));
                                augmentedMatrix.setElmt(swapRow, j, temp);
                            }
                            swapped = true;
                            break;
                        }
                    }

                    // Jika tidak ada baris untuk ditukar, matriks tidak bisa diinverskan
                    if (!swapped) {
                        throw new IllegalArgumentException("Matriks tidak dapat di-inverskan karena pivot 0 dan tidak ada baris untuk ditukar.");
                    }

                    // Update pivot setelah pertukaran
                    pivot = augmentedMatrix.getElmt(i, i);
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
        // } catch (IllegalArgumentException e) {
        //     System.out.println("Error: " + e.getMessage());
        //     return null;
        // } catch (Exception e) {
        //     System.out.println("Terjadi kesalahan: " + e.getMessage());
        //     return null;
        // }


    }
}

