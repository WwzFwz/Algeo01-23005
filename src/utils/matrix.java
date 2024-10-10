import java.util.*;
public class Matrix {
    private int col;
    private int row;
    private double [][] data;

    // CONSTRUCTOR

    public Matrix (int row, int col){
        this.row = row ;
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
        Matrix rowElmts = mew Matrix(1,getRow());
        for (i = 0;i < getRow() ;i ++){
            rowElmts.data[0][i] = this.data[row][i];
        }
        return rowElmts;

    }

    // SETTER
    public void setElement(int row, int col,double elmt) {
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
        int i;j
        Scanner input = new Scanner(System.in);
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




    // OTHERS UTILITIES

    // Membuat salinan matrix 
    public Matrix copyMatrix() {
        Matrix copy = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(this.data[i],0,copy.data[i],0,this.col);
        }

        return copy;
    }

    }


  




