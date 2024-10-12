package matrix;

public class BicubicSpline {


    public static Matrix bicubicMatrix (){
        System.out.println("test");
        Matrix X  = new Matrix(16,16);
        int i,j;
        int x,y ;
        int idxCol = 0 ;
        int idxRow = 0;

        // f (x,y) representation   

        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                for (j = 0 ; j < 4; j ++) {
                    for (i = 0 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol,Math.pow(x,i) * Math.pow(y,j));
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }

        // fx(x,y) representation 
        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                for (j = 0 ; j < 4; j ++) {
                    for (i = 0 ; i < 4 ; i ++) {
                        if (i != 1){
                            X.setElmt(idxRow,idxCol, i * Math.pow(x,i-1) * Math.pow(y,j))
                            };
                        else {
                            X.setElmt(idxRow,idxCol,0)

                        }
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }

        // fy(x,y) representation
        for (y = 0 ; y < 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                for (j = 1 ; j < 4; j ++) {
                    for (i = 0 ; i < 4 ; i ++) {
                        if (j != 1){
                            X.setElmt(idxRow,idxCol, i * Math.pow(x,i) * Math.pow(y,j-1));
                        }
                        else {
                            X.setElmt(idxRow,idxCol,0)
                        }
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }

        // fxy(x,y) representation
        // idxRow = 13;
        for (y = 0 ; y< 2 ; y ++){
            for (x= 0 ; x < 2 ;  x ++){
                for (j = 0 ; j < 4; j ++) {
                    for (i = 0 ; i < 4 ; i ++) {
                        X.setElmt(idxRow,idxCol,Math.pow(x,i-1) * Math.pow(y,j-1)*i*j);
                        idxCol ++;
                    }
                }
                idxCol= 0;
                idxRow ++;

            }
        }
        return X ;
        
    }

}