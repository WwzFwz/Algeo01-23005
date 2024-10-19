package matrix;

import java.util.Arrays;

public class GaussJordan{
    // asumsi inputan udah berupa augmented matrix
    public static Matrix GaussJordan(Matrix matrix){
        int row = matrix.getRow();  
        int col = matrix.getCol(); 

        // bikin diagonal nya 1
        for (int i=0 ; i<row ; i ++){
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
    