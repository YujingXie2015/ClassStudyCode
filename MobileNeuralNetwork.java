import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by yujingxie on 12/6/15.
 */
public class MobileNeuralNetwork {

    double [][] train;
    int [][] target;
    double [][] output;
    double [][] test;
    double [][] testouput;
    int [][] testtarget;
    double lanmdar;
    double learnrate;
    int iteration;
    double [][] w1;
    double [][] w2;
    int hid;
    int ntrain;
    int ntest;
    int dim;
    double [][]  hidden;
    double [][]  testhid;
     public MobileNeuralNetwork(double [][] traindata, double [][] testdata, int [][]traintarget, int [][] testt,int hidde){
         train=traindata;
         test=testdata;
         target=traintarget;
         testtarget=testt;
         hid=hidde;
         ntrain=train.length;
         ntest=test.length;
         hidden=new double[ntrain][hid];
         testhid=new double[ntest][hid];
         w1=new double[hid][train[0].length];
         w2=new double[target[0].length][hid];
         output=new double[train.length][target[0].length];
         testouput=new double[test.length][target[0].length];
         dim=train[0].length;
         for(int i=0;i<hid;i++)
             for(int j=0;j<w1[0].length;j++)
                 w1[i][j]=(double)getGaussian(0.0,0.01);

         for(int i=0;i<w2.length;i++)
             for(int j=0;j<hid;j++)
                 w2[i][j]=(double)getGaussian(0.0,0.01);

     }


    public double neuralLearning(int itime,double lan, double lrate) {
            iteration = itime;
            lanmdar = lan;
            learnrate = lrate;
            double error=0.0;

            for (int k = 0; k < iteration; k++) {
                double trainError = 0.0;
                feedForward(ntrain, train,output,hidden);
                double[] sum = new double[ntrain];
                for(int i=0;i<output.length;i++)
                    for(int j=0;j<output[0].length;j++)
                        sum[i]+=Math.pow((output[i][j]-target[i][j]),2);

                for(int i=0;i<sum.length;i++)
                    trainError+=sum[i];
                trainError = trainError / ntrain;

                double wsum1 = 0;
                for (int y = 0; y < hid; y++)
                    for (int z = 0; z < dim; z++)
                        wsum1 += (double) Math.pow(w1[y][z], 2);
                double wsum2 = 0;
                for (int y1 = 0; y1 < w2.length; y1++)
                    for (int z1 = 0; z1 < hid; z1++)
                        wsum2 += (double) Math.pow(w2[y1][z1], 2);
                /** use decay to regulation*/
                trainError = (trainError + lanmdar * wsum1 + lanmdar * wsum2) / 2;

                Random ran = new Random();
                int next = ran.nextInt(ntrain);
                for(int i=0;i<output[next].length;i++)
                    for(int j=0;j<hid;j++){
                        double delta=output[next][i]-target[next][i];
                        w2[i][j]-=learnrate*delta*hidden[next][j]*(1-Math.pow(output[next][i],2));
                    }

                double [] delta=new double[target[0].length];
                for(int i=0;i<delta.length;i++)
                    delta[i]=output[next][i]-target[next][i];


                for(int i=0;i<hid;i++)
                    for(int j=0;j<train[0].length;j++){
                        double sumdelta=0.0;
                        for(int t=0;t<output[0].length;t++)
                            sumdelta+=w2[t][i]*delta[t]*(1-Math.pow(output[next][t],2));
                        w1[i][j]-=learnrate*(1-Math.pow(hidden[next][i],2))*train[next][j]*sumdelta;

                    }
//
//                for (int jj = 0; jj < hid; jj++)
//                    for (int ii = 0; ii < dim; ii++) {
//                        double sumk = 0.0;
//                        for (int kk = 0; kk < dim; kk++)
//                            sumk += w2[kk][jj] * delta[kk] * (1 - Math.pow(((double) output[next].elementAt(kk)), 2));
//                        w1[jj][ii] -= learnrate * (1 - (double) Math.pow((double) hidden[next].elementAt(jj), 2)) * (double) input[next].elementAt(ii) * sumk;
//                    }
                learnrate = learnrate / (1 + k / iteration);
                lanmdar=lanmdar/(1+2*k/lanmdar);


/// /                for (int kk = 0; kk < output[next].length; kk++)
//                    for (int jj = 0; jj < hid; jj++) {
//                        double deltakk = (double) output[next].elementAt(kk) - (double) input[next].elementAt(kk);
//                        w2[kk][jj] -= learnrate * deltakk * (double) hidden[next].elementAt(jj) * (1 - Math.pow(output[next][kk], 2);
//                    }
//                double[] delta = new double[dim];
//                for (int ss = 0; ss < dim; ss++)
//                    delta[ss] = (double) output[next].elementAt(ss) - (double) input[next].elementAt(ss);

                if(k==iteration-1){

                    error=trainError;
                }
            }

            return error;
        }

        public static double [] meanDeviation(double [] data){
            double [] meanDeviation=new double[2];
            double mean=0.0;
            double sum=0.0;
            for(int i=0;i<data.length;i++)
                sum+=data[i];
            mean=sum/data.length;
            double standeviation;
            double sumv=0.0;
            for(int j=0;j<data.length;j++)
                sumv=Math.pow((data[j]-mean),2);
            standeviation=Math.sqrt(sumv);
            meanDeviation[0]=mean;
            meanDeviation[1]=standeviation;
            return meanDeviation;
        }

        public  double testNeuralNetwork(){
            feedForward(ntest,test,testouput,testhid);
            double testError=0.0;
            double[] sum = new double[ntest];
            for(int i=0;i<ntest;i++)
                for(int j=0;j<testouput[0].length;j++)
                    sum[i]+=Math.pow((testouput[i][j]-testtarget[i][j]),2);

            for(int i=0;i<ntest;i++)
                testError+=sum[i];
            testError=testError/ntest;

            double w1sum = 0;
            for(int i=0;i<hid;i++)
                for (int j=0;j<w1[0].length;j++)
                    w1sum+=Math.pow(w1[i][j],2);

            double w2sum=0;
            for(int i=0;i<w2.length;i++)
                for(int j=0;j<w2[0].length;j++)
                    w2sum+=Math.pow(w2[i][j],2);

            testError= (testError+ lanmdar * w1sum + lanmdar * w2sum) / 2;
            return testError;


        }


        public void feedForward(int ntrain, double [][] input,double [][] outputp,double [][] hidp){
            double [][] aj=new double[ntrain][hid];

            //compute aj
            for(int k=0;k<ntrain;k++)
                for(int i=0;i<hid;i++)
                    for(int j=0;j<input[0].length;j++){
                        aj[k][i]+=w1[i][j]*input[k][j];
                    }
            //compute hidden layer
            for(int i=0;i<aj.length;i++)
                for(int j=0;j<aj[0].length;j++)
                    hidp[i][j]=Math.tanh(aj[i][j]);
            // compute ak
            double [][] ak=new double[ntrain][outputp[0].length];
            for(int i=0;i<ak.length;i++)
                for(int j=0;j<ak[0].length;j++)
                    for(int k=0;k<hid;k++)
                        ak[i][j]+=w2[j][k]*hidp[i][k];
            //compute output
            for(int i=0;i<outputp.length;i++)
                for(int j=0;j<outputp[0].length;j++)
                    outputp[i][j]=Math.tanh(ak[i][j]);
        }


        public void writeFile(double [][] array,String filename) throws IOException {
            BufferedWriter bw= new BufferedWriter(new FileWriter("/Users/yujingxie/documents/map"+filename+".txt"));

            for(int i=0;i<array.length;i++) {
                for (int j = 0; j < array[0].length; j++)
                    bw.write(array[i][j]+"\t");
                bw.write("\n");
            }
            bw.close();
        }

        private Random gRandom = new Random();
        private double getGaussian(double aMean, double aVariance){
            return aMean + gRandom.nextGaussian() * aVariance;
        }
    }

