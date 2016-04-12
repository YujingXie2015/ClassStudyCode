import java.util.Vector;

/**
 * Created by yujingxie on 10/3/15.
 */
public class GaussionRandom2 {

    public static void main(String... aArgs) {
        double[] errorrate = new double[500];

        for (int d = 0; d < 500; d++) {
            double[][] sigmav = new double[9][9];
            for (int i = 0; i < 9; i++) {
                sigmav[i][i] = 10;
            }
            double[][] revesigma = new double[9][9];
            for (int k = 0; k < 9; k++)
                revesigma[k][k] = 1 / sigmav[k][k];

            GaussionGeneration gaussianclass = new GaussionGeneration(sigmav);
            Vector u1 = new Vector(9);
            Vector u2 = new Vector(9);
            /**gaussianclass.generationu();
             Vector u1 = new Vector(9);
             u1 = gaussianclass.getu1();
             Vector u2 = new Vector(9);
             u2 = gaussianclass.getu2();
             for (int index = 0; index < u1.size(); index++)
             System.out.println(u1.elementAt(index) + " ");
             for (int index1 = 0; index1 < u2.size(); index1++)
             System.out.println(u2.elementAt(index1) + " ");
             u1.add(0.019643364717942780:
             u1.add(1.228626281907115);
             u1.add(0.48157306539434774);
             u1.add(0.5774942600912987);
             u1.add(1.0975603119792117);
             u1.add(0.6907574740100566);
             0.5547054922698771
             -0.278653038752326
             -1.4941452669819195
             -0.8079717839528711
             -0.5636429431613578
             -0.1717041503674355
             0.5484103515508564
             -0.12451171197374611
             1.2838826207093488
             0.3378638104650106
             -0.8705921863490497
             -0.2712301104890379
             u1.add(-0.249);
             u1.add(0.27);
             u1.add(3.27);
             u1.add(-0.60);
             u1.add(2.25);
             u1.add(-0.30);
             u1.add(0.68);
             u1.add(7.37);
             u1.add(-0.52);
             u2.add(-5.1);
             u2.add(-3.18);
             u2.add(2.52);
             u2.add(4.53);
             u2.add(-0.22);
             u2.add(-4.79);
             u2.add(-7.40);
             u2.add(-4.31);
             u2.add(-3.66);*/

            u1.add(0.0);
            u1.add(1.0);
            u1.add(0.0);
            u1.add(0.0);
            u1.add(0.0);
            u1.add(1.0);
            u1.add(0.0);
            u1.add(0.0);
            u1.add(1.0);
            u2.add(0.0);
            u2.add(0.0);
            u2.add(0.0);
            u2.add(1.0);
            u2.add(0.0);
            u2.add(1.0);
            u2.add(1.0);
            u2.add(1.0);
            u2.add(0.0);
            int N = 5000;
            double alpha = 0.1;

            Vector[] class1 = new Vector[N];
            Vector[] class2 = new Vector[N];
            Vector y1est = new Vector(N);
            Vector y1real = new Vector(N);
            Vector y2est = new Vector(N);
            Vector y2real = new Vector(N);
            for (int index2 = 0; index2 < N; index2++)
                y1est.add(1);
            for (int index2 = 0; index2 < N; index2++)
                y2est.add(-1);
            Vector[] test1 = new Vector[N];
            Vector[] test2 = new Vector[N];
            test1 = gaussianclass.generateClass(alpha, N, u1);
            test2 = gaussianclass.generateClass(alpha, N, u2);


            Vector minusu = new Vector(9);
            for (int index = 0; index < u1.size(); index++)
                minusu.add((double) u1.elementAt(index) - (double) u2.elementAt(index));
            Vector w = new Vector(9);
            for (int m = 0; m < revesigma[0].length; m++)
                w.add(m, (double) revesigma[m][m] * (double) minusu.elementAt(m));

            Vector middleu = new Vector(9);
            for (int mid = 0; mid < u1.size(); mid++)
                middleu.add(((double) u1.elementAt(mid) + (double) u2.elementAt(mid)) / 2);
            double b = 0;
            for (int size = 0; size < w.size(); size++)
                b -= (double) w.elementAt(size) * (double) middleu.elementAt(size);

            int count = 0;
            double result = 0.0f;
            for (int j = 0; j < test1.length; j++) {
                for (int index1 = 0; index1 < w.size(); index1++)
                    result += (double) w.elementAt(index1) * (double) test1[j].elementAt(index1);
                result += b;
                y1real.add(j, gaussianclass.classify(result));
                result = 0;
            }
            double result2 = 0.0f;
            for (int j = 0; j < test2.length; j++) {
                for (int index1 = 0; index1 < w.size(); index1++)
                    result2 += (double) w.elementAt(index1) * (double) test2[j].elementAt(index1);
                result2 += b;
                /**if(result2>=0) count++;*/
                y2real.add(j, gaussianclass.classify(result2));
                result2 = 0;
            }

            /** System.out.println(count);*/
            for (int k = 0; k < y1est.size(); k++)
                if (y1est.elementAt(k) != y1real.elementAt(k)) count++;
            for (int k = 0; k < y2est.size(); k++)
                if (y2est.elementAt(k) != y2real.elementAt(k)) count++;
           /** System.out.println(count);*/
            errorrate[d] = (double) count / (2 * N);

            /** System.out.println(alpha + " " + N + " " + errorrate);*/
        }

        double mean = 0.0;
        double standeviation = 0.0;
        double sum = 0.0;
        for (int count = 0; count < errorrate.length; count++) {
            sum += errorrate[count];
        }
        mean = (double) sum / errorrate.length;

        double sume = 0.0f;
        for (int s = 0; s < errorrate.length; s++)
            sume += (double) ((double) errorrate[s] - mean) * ((double) errorrate[s] - mean);
        double stdev = 0.0;
        stdev = Math.sqrt(sume / errorrate.length);
        System.out.println(mean + " " + stdev);


    }

}
