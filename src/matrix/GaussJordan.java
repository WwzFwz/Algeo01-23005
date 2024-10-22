package matrix;

public class GaussJordan{
    // asumsi inputan udah berupa augmented matrix
    public static Matrix gaussJordan(Matrix matrix){
        int row = matrix.getRow();  
        int col = matrix.getCol(); 

        // bikin diagonal nya 1
        for (int i=0 ; i<row ; i ++){

            // jika elemen diagonal bernilai 0, tukar dengan baris dibawah nya yang nilai diagonal bukan 0
            if (matrix.getElmt(i, i) == 0) {
                boolean swapped = false;
                for (int swapRow = i + 1; swapRow < row; swapRow++) {
                    if (matrix.getElmt(swapRow, i) != 0) {
                        // Tukar baris i dengan baris swapRow
                        for (int j = 0; j < col; j++) {
                            double temp = matrix.getElmt(i, j);
                            matrix.setElmt(i, j, matrix.getElmt(swapRow, j));
                            matrix.setElmt(swapRow, j, temp);
                        }
                        swapped = true;
                        break;
                    }
                }
            }

            double diagonal = matrix.getElmt(i, i);
            // bikin diagonal nya 1
            for (int j = 0; j<col ; j++){
                matrix.setElmt(i,j,matrix.getElmt(i, j)/diagonal);
            }

            for (int k = 0; k<row ; k++){
                if (k != i){
                    double pengali = matrix.getElmt(k, i);
                    for (int j=0; j<col ; j++){
                        matrix.setElmt(k, j, (matrix.getElmt(k,j) - matrix.getElmt(i,j) * pengali));
                    }
                }
            }
        }

        return matrix;

    }
}


