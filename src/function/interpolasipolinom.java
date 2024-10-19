package function;
import matrix.Matrix;
import matrix.InversMatrix;
import utils.ReadFile;
import utils.SavetoFile;
import matrix.GaussJordan;

public class InterpolasiPolinom{
        private double[] coefficients;


        //getter
        public double[] getCoefficients(){
            return this.coefficients;
        }
        // method cari koefisien
        public void calculateCoefficient(Matrix data){
            int n = data.getRow();
            Matrix augmentedMatrix = new Matrix (n ,n +1);
            for (int i = 0 ; i < n ; i ++){
                double xi = data.getElmt(i,0); //col 1
                double yi = data.getElmt(i,1); //col 2
                augmentedMatrix.setElmt(i,n,yi); // insert yi at last col
                for (int j = 0 ; j < n ; j ++){
                    augmentedMatrix.setElmt(i,j,Math.pow(xi,j)); // insert xi ,xi^2...xi^n-1 at cols    
                }
            }
            Matrix gaussJordanSolution = GaussJordan.GaussJordan(augmentedMatrix); //find solution
            coefficients = new double[n];
            for (int i = 0 ; i < n ; i ++ ){
                coefficients[i]= gaussJordanSolution.getElmt(i,n);
            }

        }
        public double estimateY(double x){
            double result = 0 ;
            for(int i = 0 ; i < coefficients.length ; i ++){
                result += coefficients[i]* Math.pow(x,i);
            }
            return result ;
        }

        // to string
        public String coefficientsToString(){
            StringBuilder polynomial = new StringBuilder("f(x) = ");
            for ( int i = coefficients.length-1 ; i >= 0 ; i --){
                if (coefficients[i] != 0 ){
                    if (i == coefficients.length -1){
                        polynomial.append(String.format("%.4fx^%d",coefficients[i],i));
                    }
                    else {
                        if (coefficients[i] > 0){
                            polynomial.append(String.format(" + %.4fx^%d",coefficients[i],i));

                        } else {
                            polynomial.append(String.format(" - %.4fx^%d",coefficients[i],i));
                        }
                    }


                }
            }
            String result = polynomial.toString().replace("x^0", "").replace("+-", "-").replace("^1", "");
            return result;
        }

        public void saveInterpolToFile(String fileName){
            String output = coefficientsToString();
            SavetoFile.saveResultToFile(output,fileName);
        }


}

