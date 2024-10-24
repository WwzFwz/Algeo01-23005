package matrix;
import java.util.*;
import java.io.File;
    
public class Matrix {
    private int col;
    private int row;
    private double [][] data;

    // scanner 
    private static Scanner input = new Scanner(System.in);


    // CONSTRUCTOR

    public Matrix (int row, int col){
        this.row = row;
        this.col = col;
        this.data = new double[row][col];
    }

    // GETTER
    public int getRow () {
        return this.row;
    }

    public int getCol () {
        return this.col;
    }

    public int getLastRowIdx(){
        return this.row - 1;
    }

    public int getLastColIdx(){
        return this.col - 1;
    }

    public double getElmt(int row, int col){
        return this.data[row][col];
    }

    public Matrix getColElmts(int col){
        int i;
        Matrix colElmts = new Matrix(1,getCol());
        for (i = 0;i < getCol() ;i ++){
            colElmts.data[i][0] = this.data[i][col];
        }
        return colElmts;

    }

    public Matrix getRowElmts(int row){
        int i;
        Matrix rowElmts = new Matrix(1,getRow());
        for (i = 0;i < getRow() ;i ++){
            rowElmts.data[0][i] = this.data[row][i];
        }
        return rowElmts;

    }

    public void deleteLastRow() {
    //Mengapus  last row matrix
        if (this.row <= 1) {
            throw new IllegalArgumentException(" matriks harus memiliki setidaknya satu baris.");
        }
        
        Matrix newMatrix = new Matrix(this.row - 1, this.col);

        for (int i = 0; i < this.row - 1; i++) {
            for (int j = 0; j < this.col; j++) {
                newMatrix.setElmt(i, j, this.data[i][j]);
            }
        }

        this.row = newMatrix.getRow();
        this.data = newMatrix.data;
    }

    //SETTER    
    
    public void setElmt(int row, int col,double elmt) {
        this.data[row][col] = elmt;
    }

    public static Matrix readMatrix(int row, int col){
        Matrix matrix = new Matrix(row, col); // Inisialisasi matriks
         for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print("Elemen [" + i + "][" + j + "]: ");
                matrix.setElmt(i, j, input.nextDouble());
            }
        }
        return matrix;
    }

    public void rowSwap(Matrix m, int rows1, int rows2){
		double temp;
		for (int i = 0; i < m.col; i++){
			temp = m.getElmt(rows1, i);
			m.setElmt(rows1, i, m.getElmt(rows2, i));
			m.setElmt(rows2, i, temp);
		}
    }


    //readMatrixfromKeyboard

    public static Matrix readMatrixFromKeyboard() {
        int i,j;
        System.out.print("Masukkan jumlah baris: ");
        int row = input.nextInt();
        System.out.print("Masukkan jumlah kolom: ");
        int col = input.nextInt();

        Matrix matrix = new Matrix(row, col);
        System.out.println("Masukkan elemen matriks:");

        for (i = 0; i < row; i++) {
            for (j = 0; j < col; j++) {
                System.out.print("Elemen [" + i + "][" + j + "]: ");
                matrix.setElmt(i, j, input.nextDouble());
            }
        }
        return matrix;
    }
    public static Matrix readMatrixSquareFromKeyboard() {
        int i,j;
        System.out.print("Masukkan n : ");
        int n = input.nextInt();

        Matrix matrix = new Matrix(n, n);
        System.out.println("Masukkan elemen matriks:");

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.print("Elemen [" + i + "][" + j + "]: ");
                matrix.setElmt(i, j, input.nextDouble());
            }
        }
        return matrix;
    }
    // Menampilkan matriks
    public void displayMatrix() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                System.out.print(this.data[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    // VALIDATION


    public boolean isSquareMatrix() {
        return this.row == this.col;
    }

    public boolean isSymmetric() {
        int i = 0, j = 0;
        boolean symmetric = true;
        if (!isSquareMatrix()) {
            symmetric = false;
        }
        while (i < this.row && symmetric) {
            j = 0;
            while (j < this.col && symmetric) {
                if (this.data[i][j] != this.data[j][i]) {
                    symmetric = false;
                }
                j++;
            }
            i++;
        }
        return symmetric;
    }

    public boolean isMatrixSizeEqual(Matrix Matrix2) {
        return this.row == Matrix2.getRow() && this.col == Matrix2.getCol();
    }


    public boolean isMatrixEqual(Matrix Matriks2) {
        int i = 0, j;
        boolean equal = true;
        if (!isMatrixSizeEqual(Matriks2)) {
            equal = false;
        }
        while (i < this.row && equal) {
            j = 0;
            while (j < this.col && equal) {
                if (this.data[i][j] != Matriks2.getElmt(i, j)) {
                    equal = false;
                }
                j++;
            }
            i++;
        }
        return equal;
    }

    public boolean isIdentity() {
        if (!this.isSquareMatrix()) {
            return false;
        }

        for (int i = 0; i < this.row; i++) {
            if (this.data[i][i] != 1) {
                return false;
            }

            for (int j = 0; j < this.col; j++) {
                if (i != j && this.data[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }




    // OTHERS UTILITIES


    // Mengirimkan matrix yang telah di conversi ke format string 
    public String toString() {
        StringBuilder StringMatrix = new StringBuilder();

        for (int i = 0; i < this.getRow() ; ++i) {
            for (int j = 0; j < this.getCol(); ++j) {
                StringMatrix.append(String.format("%5.2f ", this.getElmt(i,j)));
            }
            StringMatrix.append("\n");
        }

        return StringMatrix.toString();
    }




    public Matrix copyMatrix() {
        Matrix copy = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(this.data[i],0,copy.data[i],0,this.col);
        }

        return copy;
    }

    public Matrix transpose() {
        Matrix transpose = new Matrix(this.col, this.row);

        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.row; j++) {
                transpose.data[i][j] = this.data[j][i];
            }
        }

        return transpose;
    }
    public static Matrix multiplyMatrix(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getCol() != matrix2.getRow()) {
            throw new IllegalArgumentException("Ukuran matriks tidak valid untuk perkalian karena jumlah kol matrix 1 != jumlah row matrix 2 .");
        }

        Matrix result = new Matrix(matrix1.getRow(),matrix2.getCol());
        int i,j,k;
        for (i =0 ; i < matrix1.getRow();i++){
            for ( j = 0; j < matrix2.getCol(); j++){
                double sum = 0;
                for ( k = 0; k < matrix2.getRow(); k++){
                    sum += (matrix1.getElmt(i,k) * matrix2.getElmt(k,j));
                }
                result.setElmt(i,j,sum);
            }
        }
        return result;
    }

}


  
