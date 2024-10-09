public class Matrix {
    private final int col;
    private final int row;
    private final double [][] data;

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


    //displayMatrix


}

