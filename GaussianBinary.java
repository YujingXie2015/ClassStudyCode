import java.util.Vector;

/**
 * Created by yujingxie on 10/16/15.
 */
public class GaussianBinary {
    public static void main(String... aArgs) {
        double [] errorrate=new double[100];

        for(int d=0;d<100;d++) {
            double[][] sigmav = new double[9][9];
            for (int i = 0; i < 9; i++) {
                sigmav[i][i] = 2;
            }
            double[][] revesigma = new double[9][9];
            for (int k = 0; k < 9; k++)
                revesigma[k][k] = 1 / sigmav[k][k];

            GaussionGeneration gaussianclass = new GaussionGeneration(sigmav);
            gaussianclass.randomu();
            Vector u1 = new Vector(9);
            u1 = gaussianclass.getu1();
            Vector u2 = new Vector(9);
            u2 = gaussianclass.getu2();
            int N = 500;
            double alpha = 0.90;

            Vector[] train1 = new Vector[N];
            Vector[] train2 = new Vector[N];
            train1=gaussianclass.generateBinaryClass(alpha,N,u1);
            train2=gaussianclass.generateBinaryClass(alpha,N,u2);
            Vector samplemean1=new Vector(9);
            Vector samplemean2=new Vector(9);

            double [] sum1=new double[9];
            for(int count=0;count<9;count++)
                for(int l=0;l<train1.length;l++)
                    sum1[count]+=(double)train1[l].elementAt(count);
            for(int n=0;n<9;n++)
                samplemean1.add((double)sum1[n]/train1.length);

            double [] sum2=new double[9];
            for(int countc2=0;countc2<9;countc2++)
                for(int l=0;l<train2.length;l++)
                    sum2[countc2]+=(double)train2[l].elementAt(countc2);
            for(int n2=0;n2<9;n2++)
                samplemean2.add(sum2[n2]/train2.length);
            Vector middlemean=new Vector(9);
            for(int y=0;y<9;y++)
                middlemean.add((double)((double)samplemean1.elementAt(y)+(double)samplemean2.elementAt(y))/2);

            double [][] sw1=new double[9][9];

            for(int i1=0;i1<train1.length;i1++)
                for (int j1=0;j1<9;j1++)
                    for(int k1=0;k1<9;k1++)
                        sw1[j1][k1]+=((double)train1[i1].elementAt(j1)-(double)samplemean1.elementAt(j1))*((double)train1[i1].elementAt(k1)-(double)samplemean1.elementAt(k1));
            double [][] sw2=new double[9][9];

            for(int i2=0;i2<train2.length;i2++)
                for (int j2=0;j2<9;j2++)
                    for(int k2=0;k2<9;k2++)
                        sw2[j2][k2]+=((double)train2[i2].elementAt(j2)-(double)samplemean2.elementAt(j2))*((double)train2[i2].elementAt(k2)-(double)samplemean2.elementAt(k2));
            double [][] sw=new double[9][9];
            for(int i3=0;i3<9;i3++)
                for(int j3=0;j3<9;j3++)
                    sw[i3][j3]=sw1[i3][j3]+sw2[i3][j3];

            Detcompute det=new Detcompute();
            double detsw=det.detmatrixcompute(sw);
            double [][] detm=new double[sw.length][sw.length];
            detm=det.computem(sw);

            double [][] reversedet=new double[sw[0].length][sw[0].length];
            for(int p=0;p<reversedet.length;p++)
                for(int u=0;u<reversedet.length;u++)
                    reversedet[p][u]=(double)detm[p][u]/detsw;


            double [][] wdet=new double[9][1];
            for(int v=0;v<9;v++)
                for(int g=0;g<1;g++){
                    for(int h=0;h<reversedet[0].length;h++)
                        wdet[v][g]+=reversedet[v][h]*((double)samplemean1.elementAt(h)-(double)samplemean2.elementAt(h));
                }
            Vector w = new Vector(9);
            for(int o=0;o<9;o++)
                w.add(wdet[o][0]);

            Vector y1est=new Vector(N);
            Vector y1real=new Vector(N);
            Vector y2est=new Vector(N);
            Vector y2real=new Vector(N);
            for(int index2=0;index2<N;index2++)
                y1est.add(1);
            for(int index2=0;index2<N;index2++)
                y2est.add(-1);
            Vector[] test1 = new Vector[N];
            Vector[] test2 = new Vector[N];
            test1 = gaussianclass.generateBinaryClass(alpha, N, u1);
            test2 = gaussianclass.generateBinaryClass(alpha, N, u2);


            Vector minusu = new Vector(9);
            for (int index = 0; index < samplemean1.size(); index++)
                minusu.add((double) samplemean1.elementAt(index) - (double) samplemean2.elementAt(index));
            double b = 0;
            for (int size = 0; size < w.size(); size++)
                b-= (double)((double) w.elementAt(size) * (double) middlemean.elementAt(size));
            int count = 0;
            double result = 0.0f;
            for (int j = 0; j < test1.length; j++) {
                for (int index1 = 0; index1 < w.size(); index1++)
                    result += (double) w.elementAt(index1) * (double) test1[j].elementAt(index1);
                result += b;
                y1real.add(j,gaussianclass.classify(result));
                result = 0;
            }
            double result2 = 0.0f;
            for (int j = 0; j < test2.length; j++) {
                for (int index1 = 0; index1 < w.size(); index1++)
                    result2 += (double) w.elementAt(index1) * (double) test2[j].elementAt(index1);
                result2 += b;
                y2real.add(j,gaussianclass.classify(result2));
                result2 = 0;
            }
            for(int k=0;k<y1est.size();k++)
                if(y1est.elementAt(k)!=y1real.elementAt(k)) count++;
            for(int k=0;k<y2est.size();k++)
                if(y2est.elementAt(k)!=y2real.elementAt(k)) count++;
               /*System.out.println(count);*/
            errorrate[d] = (double) count / (2 * N);

        }

        double mean=0.0;
        double standeviation=0.0;
        double sum=0.0;
        for(int count=0;count<errorrate.length;count++){
            sum+=errorrate[count];
        }
        mean=(double)sum/errorrate.length;

        double sume=0.0f;
        for(int s=0;s<errorrate.length;s++)
            sume+=(double)((double)errorrate[s]-mean)*((double)errorrate[s]-mean);
        double stdev=0.0;
        stdev=Math.sqrt(sume/errorrate.length);
        System.out.println(mean+" "+stdev);


    }
}
