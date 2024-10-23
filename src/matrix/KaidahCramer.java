package matrix;

public class KaidahCramer {
    
    public static Matrix replaceColumn(Matrix matrix, double[] konstanta, int k) {
        int row = matrix.getRow();
        int col = matrix.getCol();

        Matrix modifiedMatrix = new Matrix(row, col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (j == k) {
                    modifiedMatrix.setElmt(i, j, konstanta[i]); // Gantikan kolom k dengan vektor konstanta
                } else {
                    modifiedMatrix.setElmt(i, j, matrix.getElmt(i, j));// Biarkan kolom lain tetap sama
                }
            }
        }
        
        return modifiedMatrix;
    }

    public static double[] KaidahCramer(Matrix matrix, double[] konstanta) {
        int row = matrix.getRow();
        int col = matrix.getCol();

        double DetA = Determinant.DeterminantCOFACTOR(matrix); // Determinan matriks koefisien
        
        if (DetA == 0) {
            throw new IllegalArgumentException("Sistem tidak memiliki solusi unik karena determinan matriks koefisien adalah 0.");
        }
        
        double[] solutions = new double[row];
        
        for (int i = 0; i < row; i++) {
            Matrix modifiedMatrix = replaceColumn(matrix, konstanta, i); // Ganti kolom ke-i
            solutions[i] = Determinant.DeterminantCOFACTOR(modifiedMatrix) / DetA; // Solusi untuk variabel ke-i
        }
        
        return solutions;
    }
}
