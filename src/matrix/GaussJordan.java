package matrix;

public class GaussJordan {
    // asumsi inputan sudah berupa augmented matrix
    public static Matrix gaussJordan(Matrix matrix) {
        int row = matrix.getRow();  
        int col = matrix.getCol(); 
        int pivotCol = 0;  // Kolom pivot 

        // Loop baris
        for (int i = 0; i < row; i++) {
            // Jika elemen diagonal bernilai 0, coba cari baris di bawahnya untuk ditukarr
            while (pivotCol < col && matrix.getElmt(i, pivotCol) == 0) {
                boolean swapped = false;
                for (int swapRow = i + 1; swapRow < row; swapRow++) {
                    if (matrix.getElmt(swapRow, pivotCol) != 0) {
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
                if (!swapped) {
                    pivotCol++;  // Kolom ini tidak memiliki pivot, lanjut ke kolom berikutnya
                    if (pivotCol >= col) {
                        return matrix;  // Jika sudah mentok semua kolom, selesai
                    }
                }
            }

            // Bikin elemen diagonal bernilai 1 (scaling)
            double diagonal = matrix.getElmt(i, pivotCol);
            if (diagonal != 0) {
                for (int j = 0; j < col; j++) {
                    matrix.setElmt(i, j, matrix.getElmt(i, j) / diagonal);
                }
            }

            // Eliminasi elemen di kolom pivot untuk baris lain
            for (int k = 0; k < row; k++) {
                if (k != i) {
                    double multiplier = matrix.getElmt(k, pivotCol);
                    for (int j = 0; j < col; j++) {
                        matrix.setElmt(k, j, matrix.getElmt(k, j) - matrix.getElmt(i, j) * multiplier);
                    }
                }
            }

            pivotCol++;  // Pindah ke kolom pivot berikutnya
        }

        return matrix;
    }
}
