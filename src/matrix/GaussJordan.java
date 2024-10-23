package matrix;

public class GaussJordan{
    // asumsi inputan udah berupa augmented matrix
    public static Matrix gaussJordan(Matrix matrix){
        int row = matrix.getRow();  
        int col = matrix.getCol(); 
       
        // bikin diagonal nya 1
        for (int i=0 ; i<row ; i ++){

            // jika elemen diagonal bernilai 0, tukar dengan baris dibawah nya yang nilai diagonal bukan 0
            if(i<col-1){
                double diagonal = matrix.getElmt(i, i);
                if (diagonal == 0) {
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
                    if(!swapped){
                        for(int j = i+1 ; j<col; j++){
                            if (matrix.getElmt(i,j) == 0){
                                for (int swapRow = i + 1; swapRow < row; swapRow++) {
                                    if (matrix.getElmt(swapRow, j) != 0) {
                                        // Tukar baris i dengan baris swapRow
                                        for (int k = 0; k < col; k++) {
                                            double temp = matrix.getElmt(i, k);
                                            matrix.setElmt(i, k, matrix.getElmt(swapRow, k));
                                            matrix.setElmt(swapRow, k, temp);
                                        }
                                        swapped = true;
                                        break;
                                    }
                                }
                            }
                            diagonal = matrix.getElmt(i, j);
                        }
                    }
                }

                
                
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
        }
        if(row>=col){
            for (int i = col; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    matrix.setElmt(i, j, 0);  // Setiap elemen di baris tersebut menjadi 0
                }
            }
        }
        return matrix;

    }
}


