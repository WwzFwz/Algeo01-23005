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
    public int getRowLength () {
        return this.row;
    }

    public int getColLength () {
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
        Matrix colElmts = new Matrix(1,getColLength());
        for (i = 0;i < getCollength() ;i ++){
            colElmts.data[i][0] = this.data[i][col];
        }
        return colElmts;

    }

    public Matrix getRowElmts(int row){
        int i;
        Matrix rowElmts = new Matrix(1,getRowLength());
        for (i = 0;i < getRow() ;i ++){
            rowElmts.data[0][i] = this.data[row][i];
        }
        return rowElmts;

    }

    // SETTER
    public void setElmt(int row, int col,double elmt) {
        this.data[row][col] = elmt;
    }

    // Copy Matrix 
    public Matrix copyMatrix() {
        Matrix copy = new Matrix(this.row, this.col);
        for (int i = 0; i < this.row; i++) {
            System.arraycopy(this.data[i], 0, copy.data[i], 0, this.col);
        }
        return copy;
    }


    // IO

    //readMatrixfromFile

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
                matrix.setElement(i, j, input.nextDouble());
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
                if (this.data[i][j] != other.getElmt(i, j)) {
                    equal = false;
                }
                j++;
            }
            i++;
        }
        return equal;
    }




    // OTHERS UTILITIES


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


    }


  
