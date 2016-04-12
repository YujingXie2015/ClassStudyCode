import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;



/**
 * Created by yujingxie on 10/14/15.
 */
public class NeutralNetwork {
    int ntrain;
    int ntest;
    int dim;
    int hid;
    double [][] w1;
    double [][] w2;
    Vector [] input;
    Vector [] output;
    Vector [] testdata;
    Vector [] hidden;
    int [] taget;
    int iteration;
    double lanmdar;
    double learnrate;
    double threshold;
    public  NeutralNetwork(int np, int dimp,int test){
        ntrain=np;
        ntest=test;
        dim=dimp;
        hid=30;
        w1=new double[hid][dim];
        w2=new  double[dim][hid];
        for(int i=0;i<hid;i++)
            for(int j=0;j<dim;j++)
                w1[i][j]=(double)getGaussian(0.0,0.01);

        for(int i=0;i<dim;i++)
            for(int j=0;j<hid;j++)
                w2[i][j]=(double)getGaussian(0.0,0.01);
        /**System.out.println(w1[0][2]+"w02");*/
    }

    public  NeutralNetwork(int np, int dimp,int test,double threshold1){
        ntrain=np;
        ntest=test;
        dim=dimp;
        hid=dimp/2;
        threshold=threshold1;
        w1=new double[hid][dim];
        w2=new  double[1][hid];
        for(int i=0;i<hid;i++)
            for(int j=0;j<dim;j++)
                w1[i][j]=(double)getGaussian(0.0,0.01);

        for(int i=0;i<1;i++)
            for(int j=0;j<hid;j++)
                w2[i][j]=(double)getGaussian(0.0,0.01);
    }


    public void generateInput(){
        input=new Vector[ntrain];
        for(int i=0;i<ntrain;i++)
            input[i]=new Vector(dim);
        double [] randomv=new double[dim];
        for(int i=0;i<100;i++){
            if(i<30)
                randomv[i]=Math.random()+3;
            else randomv[i]=Math.random()*0.1;
        }
        for(int k=0;k<ntrain;k++)
            for(int j=0;j<dim;j++)
                input[k].add((double)getGaussian(0,randomv[j]));

    }

    public void generateTestdata(){
        testdata=new Vector[ntest];
        for(int i=0;i<ntest;i++)
            testdata[i]=new Vector(dim);
        double [] randomv=new double[dim];
        for(int i=0;i<100;i++){
            if(i<30)
                randomv[i]=Math.random()+3;
            else randomv[i]=Math.random();
        }
        for(int k=0;k<ntest;k++)
            for(int j=0;j<dim;j++)
                testdata[k].add((double)getGaussian(0,randomv[j]));
    }

    public void generateTrainImage(){
        input=new Vector[ntrain];
        for(int i=0;i<ntrain;i++)
            input[i]=new Vector(dim);
        for(int i=0;i<ntrain;i++)
            for(int j=0;j<dim;j++)
                input[i].add(0);
        for(int j=0;j<ntrain;j++) {
            int k1 = (int) (2 + Math.random() * 33);
            int[] index1 = new int[k1 + 1];
            index1[0] = 0;
            int index;
            for (int count = 0; count < k1; count++) {
                index = (int) (Math.random() * 35);
                if (index != index1[count]) {
                    input[j].set(index, 1);
                }
                else while (true) {
                    index = (int) (Math.random() * 35);
                    if (index != index1[count]) break;
                }
                index1[count + 1] = index;
            }
        }

    }

    public void generateTestImage(){
        testdata=new Vector[ntest];
        for(int i=0;i<ntest;i++)
            testdata[i]=new Vector(dim);
        for(int i=0;i<ntest;i++)
            for(int j=0;j<dim;j++)
                testdata[i].add(0);
        for(int j=0;j<ntest;j++) {
            int k1 = (int) (2 + Math.random() * 33);
            int[] index1 = new int[k1 + 1];
            index1[0] = 0;
            int index;
            for (int count = 0; count < k1; count++) {
                index = (int) (Math.random() * 35);
                if (index != index1[count]) {
                    testdata[j].set(index, 1);
                }
                else while (true) {
                    index = (int) (Math.random() * 35);
                    if (index != index1[count]) break;
                }
                index1[count + 1] = index;
            }
        }

    }


    public void generateTrainBinary(){
        input=new Vector[ntrain];
        for(int i=0;i<ntrain;i++)
            input[i]=new Vector(dim);
        for(int j=0;j<ntrain;j++)
            for (int k=0;k<dim;k++)
                input[j].add(Math.random());
        for(int j=0;j<ntrain;j++)
            for(int k=0;k<dim;k++)
                input[j].set(k, ((double) input[j].elementAt(k) >= 0.5 ? 1 : 0));
    }

    public void generateTestBinary(){
        testdata=new Vector[ntest];
        for(int i=0;i<ntest;i++)
            testdata[i]=new Vector(dim);
        for(int j=0;j<ntest;j++)
            for (int k=0;k<dim;k++)
                testdata[j].add(Math.random());
        for(int j=0;j<ntest;j++)
            for(int k=0;k<dim;k++)
                testdata[j].set(k, ((double) testdata[j].elementAt(k) >= 0.5 ? 1 : 0));
    }

    public void binaryTagert(Vector [] input){
       taget=new int[ntrain];
        for(int i=0;i<ntrain;i++){
            int sum=0;
            for(int j=0;j<dim;j++)
                sum+=(int)input[i].elementAt(j);
            if(sum==1) taget[i]=1;
            else  taget[i]=0;
        }

    }

    public double neuralLearning(int itime,double lan, double lrate) {
        iteration = itime;
        lanmdar = lan;
        learnrate = lrate;
        double trainError = 0.0;
        for (int k = 0; k < iteration; k++) {
            feedForward(ntrain, input);
            double[] sum = new double[ntrain];
            for (int e = 0; e < ntrain; e++)
                for (int c = 0; c < dim; c++)
                    sum[e] += (double) Math.pow(((double) output[e].elementAt(c) - (double) input[e].elementAt(c)), 2);

            for (int ee = 0; ee < ntrain; ee++)
                trainError += sum[ee];
            trainError = trainError / ntrain;
            double wsum1 = 0;
            for (int y = 0; y < hid; y++)
                for (int z = 0; z < dim; z++)
                    wsum1 += (double) Math.pow(w1[y][z], 2);
            double wsum2 = 0;
            for (int y1 = 0; y1 < dim; y1++)
                for (int z1 = 0; z1 < hid; z1++)
                    wsum2 += (double) Math.pow(w2[y1][z1], 2);
            /** use decay to regulation*/
            trainError = (trainError + lanmdar * wsum1 + lanmdar * wsum2) / 2;

            Random ran = new Random();
            int next = ran.nextInt(ntrain);
            for (int kk = 0; kk < dim; kk++)
                for (int jj = 0; jj < hid; jj++) {
                    double deltakk = (double) output[next].elementAt(kk) - (double) input[next].elementAt(kk);
                    w2[kk][jj] -= learnrate * deltakk * (double) hidden[next].elementAt(jj) * (1 - Math.pow(((double) output[next].elementAt(kk)), 2));
                }
            double[] delta = new double[dim];
            for (int ss = 0; ss < dim; ss++)
                delta[ss] = (double) output[next].elementAt(ss) - (double) input[next].elementAt(ss);

            for (int jj = 0; jj < hid; jj++)
                for (int ii = 0; ii < dim; ii++) {
                    double sumk = 0.0;
                    for (int kk = 0; kk < dim; kk++)
                        sumk += w2[kk][jj] * delta[kk] * (1 - Math.pow(((double) output[next].elementAt(kk)), 2));
                    w1[jj][ii] -= learnrate * (1 - (double) Math.pow((double) hidden[next].elementAt(jj), 2)) * (double) input[next].elementAt(ii) * sumk;
                }
            learnrate = learnrate / (1 + k / iteration);
            lanmdar=lanmdar/(1+2*k/lanmdar);
        }

            return trainError;
    }


    public double neuralLearningImage(int itime,double lan, double lrate) {
        iteration = itime;
        lanmdar = lan;
        learnrate = lrate;
        double trainError=0.0;
        for (int k = 0; k < iteration; k++) {
            feedForwardImage(ntrain, input);
            double[] sum = new double[ntrain];
            for (int e = 0; e < ntrain; e++)
                for (int c = 0; c < dim; c++)
                    sum[e] += (double) Math.pow(((double) output[e].elementAt(c) - (int) input[e].elementAt(c)), 2);

            for (int ee = 0; ee < ntrain; ee++)
                trainError += sum[ee];
            trainError=trainError/ntrain;
            double wsum1 = 0;
            for (int y = 0; y < hid; y++)
                for (int z = 0; z < dim; z++)
                    wsum1 += (double) Math.pow(w1[y][z], 2);
            double wsum2 = 0;
            for (int y1 = 0; y1 < dim; y1++)
                for (int z1 = 0; z1 < hid; z1++)
                    wsum2 += (double) Math.pow(w2[y1][z1], 2);
            /** use decay to regulation*/
            trainError= (trainError+ lanmdar * wsum1 + lanmdar * wsum2) / 2;

            Random ran = new Random();
            int next = ran.nextInt(ntrain);
            for (int kk = 0; kk < dim; kk++)
                for (int jj = 0; jj < hid; jj++) {
                    double deltakk = (double) output[next].elementAt(kk) - (int) input[next].elementAt(kk);
                    double derivationkk = 1 - Math.pow((double) output[next].elementAt(kk), 2);
                    w2[kk][jj] -= learnrate * deltakk * derivationkk * (double) hidden[next].elementAt(jj);
                }
            double[] delta = new double[dim];
            for (int ss = 0; ss < dim; ss++)
                delta[ss] = (double) output[next].elementAt(ss) - (int) input[next].elementAt(ss);

            for (int jj = 0; jj < hid; jj++)
                for (int ii = 0; ii < dim; ii++) {
                    double sumk = 0.0;
                    for (int kk = 0; kk < dim; kk++)
                        sumk += w2[kk][jj] * delta[kk] * (1 - (double) Math.pow((double) output[next].elementAt(kk), 2));
                    w1[jj][ii] -= learnrate * (1 - (double) Math.pow((double) hidden[next].elementAt(jj), 2)) * (int) input[next].elementAt(ii)*sumk;
                }
            learnrate=learnrate/(1+2*k/iteration);
            lanmdar=lanmdar/(1+2*k/lanmdar);
        }

        return trainError;
    }

    public double neuralLearningBinary(int itime,double lan, double lrate) {
        iteration = itime;
        lanmdar = lan;
        learnrate = lrate;
        double trainError=0.0;
        for (int k = 0; k < iteration; k++) {
            feedForwardBinary(ntrain, input);
            double[] sum = new double[ntrain];
            for (int e = 0; e < ntrain; e++)
                for (int c = 0; c < 1; c++)
                    sum[e] += (double) Math.pow(((double) output[e].elementAt(c) - taget[e]), 2);

            for (int ee = 0; ee < ntrain; ee++)
                trainError += sum[ee];
            trainError=trainError/ntrain;
            double wsum1 = 0;
            for (int y = 0; y < hid; y++)
                for (int z = 0; z < dim; z++)
                    wsum1 += (double) Math.pow(w1[y][z], 2);
            double wsum2 = 0;
            for (int y1 = 0; y1 < 1; y1++)
                for (int z1 = 0; z1 < hid; z1++)
                    wsum2 += (double) Math.pow(w2[y1][z1], 2);
            trainError= (trainError+ lanmdar * wsum1 + lanmdar * wsum2) / 2;

            Random ran = new Random();
            int next = ran.nextInt(ntrain);
             for(int kk=0;kk<1;kk++)
                for (int jj = 0; jj < hid; jj++) {
                    double deltakk = (double) output[next].elementAt(kk)- taget[next];
                    double derivationkk = 1 - Math.pow((double) output[next].elementAt(kk), 2);
                    w2[kk][jj] -= learnrate * deltakk * derivationkk * (double) hidden[next].elementAt(jj);
                }
            double delta = 0.0;
            for(int i=0;i<1;i++)
                delta = (double)output[next].elementAt(i) - taget[next];

            for (int jj = 0; jj < hid; jj++)
                for (int ii = 0; ii < dim; ii++) {
                    double sumk = 0.0;
                    for (int kk = 0; kk < 1; kk++)
                        sumk += w2[kk][jj] * delta * (1 - (double) Math.pow((double) output[next].elementAt(kk), 2));
                    w1[jj][ii] -= learnrate * (1 - (double) Math.pow((double) hidden[next].elementAt(jj), 2)) * (int) input[next].elementAt(ii)*sumk;
                }
            learnrate=learnrate/(1+2*k/iteration);
            lanmdar=lanmdar/(1+k/lanmdar);
        }

        return trainError;
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
        feedForward(ntest,testdata);
        double testError=0.0;
        double[] sum = new double[ntest];
        for (int e = 0; e < ntest; e++)
            for (int c = 0; c < dim; c++)
                sum[e] += (double) Math.pow(((double) output[e].elementAt(c) - (double) testdata[e].elementAt(c)), 2);
        for (int ee = 0; ee < ntest; ee++)
            testError+= sum[ee];
        testError=testError/ntest;
        double wsum1 = 0;
        for (int y = 0; y < hid; y++)
            for (int z = 0; z < dim; z++)
                wsum1 += (double) Math.pow(w1[y][z], 2);
        double wsum2 = 0;
        for (int y1 = 0; y1 < dim; y1++)
            for (int z1 = 0; z1 < hid; z1++)
                wsum2 += (double) Math.pow(w2[y1][z1], 2);
        testError= (testError+ lanmdar * wsum1 + lanmdar * wsum2) / 2;
        return testError;


    }

    public  double testNeuralNetworkImage(){
        feedForwardImage(ntest,testdata);
        double testError=0.0;
        double[] sum = new double[ntest];
        for (int e = 0; e < ntest; e++)
            for (int c = 0; c < dim; c++)
                sum[e] += (double) Math.pow(((double) output[e].elementAt(c) - (int) testdata[e].elementAt(c)), 2);
        for (int ee = 0; ee < ntest; ee++)
            testError+= sum[ee];
        testError=testError/ntest;
        double wsum1 = 0;
        for (int y = 0; y < hid; y++)
            for (int z = 0; z < dim; z++)
                wsum1 += (double) Math.pow(w1[y][z], 2);
        double wsum2 = 0;
        for (int y1 = 0; y1 < dim; y1++)
            for (int z1 = 0; z1 < hid; z1++)
                wsum2 += (double) Math.pow(w2[y1][z1], 2);
        testError= (testError+ lanmdar * wsum1 + lanmdar * wsum2) / 2;
        return testError;


    }

    public  double testBinaryNeuralNetwork(){
        feedForwardBinary(ntest, testdata);
        double testError=0.0;
        double[] sum = new double[ntest];
        for (int e = 0; e < ntest; e++)
            for (int c = 0; c < 1; c++)
                sum[e] += (double) Math.pow(((double) output[e].elementAt(c) - taget[e]), 2);
        for (int ee = 0; ee < ntest; ee++)
            testError+= sum[ee];
        testError=testError/ntest;
        double wsum1 = 0;
        for (int y = 0; y < hid; y++)
            for (int z = 0; z < dim; z++)
                wsum1 += (double) Math.pow(w1[y][z], 2);
        double wsum2 = 0;
        for (int y1 = 0; y1 < 1; y1++)
            for (int z1 = 0; z1 < hid; z1++)
                wsum2 += (double) Math.pow(w2[y1][z1], 2);
        testError= (testError+ lanmdar * wsum1 + lanmdar * wsum2) / 2;
        return testError;
    }



    public void feedForward(int ntrain, Vector [] input){
             Vector [] aj=new Vector[ntrain];
             for(int q=0;q<ntrain;q++)
                 aj[q]=new Vector(hid);
            for(int s=0;s<ntrain;s++)
               for(int m=0;m<hid;m++)
                  aj[s].add(0.0);
            for(int n=0;n<ntrain;n++)
              for(int i=0;i<hid;i++)
                  for (int j = 0; j < dim; j++)
                      aj[n].set(i, (double) aj[n].elementAt(i) + w1[i][j] * (double) input[n].elementAt(j));

            hidden=new Vector[ntrain];
            for(int d=0;d<ntrain;d++)
                hidden[d]=new Vector(hid);
            for(int a=0;a<ntrain;a++)
                for(int b=0;b<hid;b++)
                    hidden[a].add(0.0);
            for(int count=0;count<ntrain;count++)
                for(int o=0;o<hid;o++)
                    hidden[count].set(o,Math.tanh((double)aj[count].elementAt(o)));

            Vector [] ak=new Vector[ntrain];
            for(int w=0;w<ntrain;w++)
                ak[w]=new Vector(dim);
            for(int s1=0;s1<ntrain;s1++)
                for(int m1=0;m1<dim;m1++)
                    ak[s1].add(0.0);
            for(int nn=0;nn<ntrain;nn++)
                for(int ii=0;ii<dim;ii++) {

                    for (int jj = 0; jj < hid; jj++)
                        ak[nn].set(ii, (double) ak[nn].elementAt(ii) + w2[ii][jj] * (double) hidden[nn].elementAt(jj));

                }

            output=new Vector[ntrain];
            for(int f=0;f<ntrain;f++)
                output[f]=new Vector(dim);
            for(int r=0;r<ntrain;r++)
                for(int t=0;t<dim;t++)
                    output[r].add(0.0);
            for(int p=0;p<ntrain;p++)
               for(int u=0;u<dim;u++)
                   output[p].set(u,Math.tanh((double)ak[p].elementAt(u)));
    }

    public void feedForwardImage(int ntrain, Vector [] input){
        Vector [] aj=new Vector[ntrain];
        for(int q=0;q<ntrain;q++)
            aj[q]=new Vector(hid);
        for(int s=0;s<ntrain;s++)
            for(int m=0;m<hid;m++)
                aj[s].add(0.0);
        for(int n=0;n<ntrain;n++)
            for(int i=0;i<hid;i++)
                for (int j = 0; j < dim; j++)
                    aj[n].set(i, (double) aj[n].elementAt(i) + w1[i][j] * (int) input[n].elementAt(j));



        hidden=new Vector[ntrain];
        for(int d=0;d<ntrain;d++)
            hidden[d]=new Vector(hid);
        for(int a=0;a<ntrain;a++)
            for(int b=0;b<hid;b++)
                hidden[a].add(0.0);
        for(int count=0;count<ntrain;count++)
            for(int o=0;o<hid;o++)
                hidden[count].set(o,Math.tanh((double)aj[count].elementAt(o)));

        Vector [] ak=new Vector[ntrain];
        for(int w=0;w<ntrain;w++)
            ak[w]=new Vector(dim);
        for(int s1=0;s1<ntrain;s1++)
            for(int m1=0;m1<dim;m1++)
                ak[s1].add(0.0);
        for(int nn=0;nn<ntrain;nn++)
            for(int ii=0;ii<dim;ii++) {

                for (int jj = 0; jj < hid; jj++)
                    ak[nn].set(ii, (double) ak[nn].elementAt(ii) + w2[ii][jj] * (double) hidden[nn].elementAt(jj));

            }

        output=new Vector[ntrain];
        for(int f=0;f<ntrain;f++)
            output[f]=new Vector(dim);
        for(int r=0;r<ntrain;r++)
            for(int t=0;t<dim;t++)
                output[r].add(0.0);
        for(int p=0;p<ntrain;p++)
            for(int u=0;u<dim;u++)
                output[p].set(u,Math.tanh((double)ak[p].elementAt(u)));
    }

    public void feedForwardBinary(int ntrain, Vector [] input){
        Vector [] aj=new Vector[ntrain];
        for(int q=0;q<ntrain;q++)
            aj[q]=new Vector(hid);
        for(int s=0;s<ntrain;s++)
            for(int m=0;m<hid;m++)
                aj[s].add(0.0);
        for(int n=0;n<ntrain;n++)
            for(int i=0;i<hid;i++)
                for (int j = 0; j < dim; j++)
                    aj[n].set(i, (double) aj[n].elementAt(i) + w1[i][j] * (int) input[n].elementAt(j));

        hidden=new Vector[ntrain];
        for(int d=0;d<ntrain;d++)
            hidden[d]=new Vector(hid);
        for(int a=0;a<ntrain;a++)
            for(int b=0;b<hid;b++)
                hidden[a].add(0.0);
        for(int count=0;count<ntrain;count++)
            for(int o=0;o<hid;o++)
                hidden[count].set(o,Math.tanh((double)aj[count].elementAt(o)));

        Vector [] ak=new Vector[ntrain];
        for(int w=0;w<ntrain;w++)
            ak[w]=new Vector(1);
        for(int s1=0;s1<ntrain;s1++)
            for(int m1=0;m1<1;m1++)
                ak[s1].add(0.0);
        for(int nn=0;nn<ntrain;nn++)
            for(int ii=0;ii<1;ii++) {
                for (int jj = 0; jj < hid; jj++)
                    ak[nn].set(ii, (double) ak[nn].elementAt(ii) + w2[ii][jj] * (double) hidden[nn].elementAt(jj));

            }

        output=new Vector[ntrain];
        for(int f=0;f<ntrain;f++)
            output[f]=new Vector(1);
        for(int r=0;r<ntrain;r++)
            for(int t=0;t<1;t++)
                output[r].add(0.0);
        for(int p=0;p<ntrain;p++)
            for(int u=0;u<1;u++)
                output[p].set(u,Math.tanh((double)ak[p].elementAt(u)));
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
