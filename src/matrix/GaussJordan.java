package matrix;

public class GaussJordan{
    // asumsi inputan udah berupa augmented matrix
    public static Matrix GaussJordan(Matrix matrix){
        int row = matrix.getRow();  
        int col = matrix.getCol(); 

        // bikin diagonal nya 1
        for (int i=0 ; i<row ; i ++){
            double diagonal = matrix.getElmt(i, i);
            // bikin diagonal nya 1
            for (int j = 0; j<row ; j++){
                matrix.setElmt(i,j,matrix.getElmt(i, j)/diagonal);

                for (int k = 0; k<col ; k++){
                    if(i!=j){
                        double pengali = matrix.getElmt(j, i);
                        matrix.setElmt(j, k, (matrix.getElmt(j,k) - matrix.getElmt(i,k) * pengali));
                    }
                }
            }
            
        }

        return matrix;

    }
}
    