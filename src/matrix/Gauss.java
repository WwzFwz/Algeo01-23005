package matrix;

public class Gauss {
    public static Matrix Gauss(Matrix matrix){
        int row = matrix.getRow();  
        int col = matrix.getCol(); 

        // bikin diagonal nya 1
        for (int i=0 ; i<row ; i ++){
            double diagonal = matrix.getElmt(i, i);
            // bikin diagonal nya 1 (membuat 1 utama)
            for (int j = 0; j<col ; j++){
                matrix.setElmt(i,j,matrix.getElmt(i, j)/diagonal);
            }

            // membuat elemen dibawah 1 utama menjadi 0
            for (int j = i + 1; j < row; j++) {
                double pengali = matrix.getElmt(j, i);
                for (int k = 0; k<col ; k++){
                    matrix.setElmt(j, k, (matrix.getElmt(j,k) - matrix.getElmt(i,k) * pengali));
                }
            }
            
        }

        return matrix;
    }
}