import java.lang.Math;
import java.util.*;

public static class Generador{
    public static int poissonRand(double lambda)
    {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;
        int range=10;
        do {
            k++;
            p = p * Math.random();
        } while (p > L);

        return k - 1;
    }
    public static double normalRand(double prom, double stdev)
    {
        double normal;
        Random r = new Random();
        normal = Math.ceil(r.nextGaussian() * prom + stdev);
        return normal;
    }
}
