import java.text.DecimalFormat;

/**
 * Created by yujingxie on 10/26/15.
 */
public class KmeansTest {

    public static void main(String [] args){
        double [] sumerror=new double[100];
        double [] excutetime=new double[100];
        double [] accuracy=new double[100];

        for(int m=0;m<100;m++) {
            Kmeans kmeantest = new Kmeans(4, 10, 500);
            kmeantest.generateTraindata();
            int count = 0;
            while (true) {
                kmeantest.KmeansAlgorithm();
                count++;
                if (kmeantest.testConvergence()) break;
            }
            excutetime[m]=count;
            for (int i = 0; i < kmeantest.n; i++)
                for (int j = 0; j < kmeantest.dimension; j++)
                    sumerror[m] += Math.pow((kmeantest.datapoint[i][j] - kmeantest.muk[kmeantest.rnk[i]][j]), 2);
            sumerror[m]=Math.sqrt(sumerror[m]);
            for(int i=0;i<kmeantest.n;i++)
                if(kmeantest.rnk[i]==kmeantest.realrnk[i]) {
                    accuracy[m]++;

                }
            accuracy[m]=accuracy[m]/kmeantest.n;


        }
        double [] sumerrorresult=new  double[2];
        sumerrorresult=Kmeans.meanDeviation(sumerror);
        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.println(df.format(sumerrorresult[0]) + "±" + df.format(sumerrorresult[1]));
        double [] executimeresult=new  double[2];
        executimeresult=Kmeans.meanDeviation(excutetime);
        System.out.println(df.format(executimeresult[0]) + "±" + df.format(executimeresult[1]));
        double [] acuracyresult=new  double[2];
        acuracyresult=Kmeans.meanDeviation(accuracy);
        System.out.println(df.format(accuracy[0]) + "±" + df.format(accuracy[1]));

    }
}
