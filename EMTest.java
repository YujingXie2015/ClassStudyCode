import java.text.DecimalFormat;

/**
 * Created by yujingxie on 10/27/15.
 */
public class EMTest {
    public static void main(String [] args){

        double [] probability=new double[10];
        double [] executetime=new double[10];
        double [] accracy=new double[10];

        for(int i=0;i<10;i++) {
            EM emtest = new EM(3, 5, 10);
            emtest.generateTraindata();
            emtest.initialize();
            int count = 0;
            while (true) {
                emtest.emAlgorithm();
                count++;
                /**System.out.println("This is"+count+"excute");*/
                if (emtest.testConvergency()) break;
            }
            executetime[i]=(double)count;
            probability[i]=emtest.probabilityafter;
        }
        double [] probabilityresult=new double[2];
        probabilityresult=EM.meanDeviation(probability);
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(df.format(probabilityresult[0]) + "±" + df.format(probabilityresult[1]));

        double [] executetimeresult=new double[2];
        executetimeresult=EM.meanDeviation(executetime);
        System.out.println(df.format(executetimeresult[0]) + "±" + df.format(executetimeresult[1]));



    }
}
