package sample;

public class Lagrange {
    public static double interpolate(double x, double[] X,double[] Y){
        double L = 0;
        for(int i = 0;i<X.length;i++) {
            double l = 1;
            for(int j = 0;j<X.length;j++){
                if(i==j)
                    continue;
                l*=(x-X[j])/(X[i]-X[j]);
            }
            L += l*Y[i];
        }
        return L;
    }
}
