import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by yujingxie on 12/6/15.
 */
public class ApplyMobileNeural implements Runnable {


    MobileNeuralNetwork neural;



    public ApplyMobileNeural(double [][]train, int [][] target,double[][] testd,int[][]ttarget, int hid){
        neural=new MobileNeuralNetwork(train,testd,target,ttarget,hid);
    }

    public static void main(String [] args) throws IOException{
        double num=118235*0.7;
        int numtrain=(int)num;
        int numtest=118235-numtrain;

        // read the train file
        String [] data=new String[numtrain];
        int countrow=0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/yujingxie/Desktop/TtrainW.csv"));
           // reader.readLine();
            String line = "";
            while((line=reader.readLine())!=null){
                String item[] = line.split("，");

                data[countrow] = item[item.length-1];
                countrow++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String [] splitstring;
        double [][] traindata=new double[numtrain][3];
        for(int i=0;i<data.length;i++) {
            int count = 0;
                splitstring=data[i].split(",");
                for(int j=0;j<splitstring.length;j++)
                    traindata[i][j]= Double.parseDouble(splitstring[j]);
        }

        //read the  train target file
        String [] target=new String[numtrain];
        int [][] targetdata=new int[numtrain][6];
        int count=0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/yujingxie/Desktop/TrainTargetW.csv"));
            String line = "";
            while((line=reader.readLine())!=null){
                String item[] = line.split("，");

                target[count] = item[item.length-1];
                count++;
            }
             String [] split;
             for(int i=0;i<target.length;i++){
                 split=target[i].split(",");
                 for(int j=0;j<split.length;j++)
                     targetdata[i][j]=Integer.parseInt(split[j]);
             }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //read test data
        String [] data1=new String[numtest];
        int countrow1=0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/yujingxie/Desktop/TestW.csv"));
            // reader.readLine();
            String line = "";
            while((line=reader.readLine())!=null){
                String item[] = line.split("，");

                data1[countrow1] = item[item.length-1];
                countrow1++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String [] splitstring1;
        double [][] testdata=new double[numtest][3];
        for(int i=0;i<data1.length;i++) {
            int count1 = 0;
            splitstring1=data1[i].split(",");
            for(int j=0;j<splitstring1.length;j++)
                testdata[i][j]= Double.parseDouble(splitstring1[j]);
        }

        //read the  test target file
        String [] target1=new String[numtest];
        int [][] testtarget=new int[numtest][6];
        int count2=0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/yujingxie/Desktop/TestTargetW.csv"));
            String line = "";
            while((line=reader.readLine())!=null){
                String item[] = line.split("，");

                target1[count2] = item[item.length-1];
                count2++;
            }
            String [] split;
            for(int i=0;i<target1.length;i++){
                split=target1[i].split(",");
                for(int j=0;j<split.length;j++)
                    testtarget[i][j]=Integer.parseInt(split[j]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ApplyMobileNeural apply=new ApplyMobileNeural(traindata,targetdata,testdata,testtarget,3);
        Thread t=new Thread(apply);
        t.start();
    }

    public void run(){
       double [] trainError=new double[10];
        double [] testError=new double[10];

       for (int i=0;i<10;i++){
            int iteration = 1000;
            double lanmdar = 0.01;
            double learnrate = 0.001;
            trainError[i] = neural.neuralLearning(iteration, lanmdar, learnrate);
            testError[i] = neural.testNeuralNetwork();
        }
        double[] train = new double[2];
        train = neural.meanDeviation(trainError);
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(df.format(train[0]) + "±" + df.format(train[1]));
        double[] test = new double[2];
        test = neural.meanDeviation(testError);
        System.out.println(df.format(test[0]) + "±" + df.format(test[1]));

        /*int count=0;
        for(int i=0;i<neural.output.length;i++){
            double max=neural.output[i][0];
            int mark=0;
            for(int j=1;j<neural.output[0].length;j++){
                if(neural.output[i][j]>max) {
                    max=neural.output[i][j];
                    mark = j;
                }
            }
            if(neural.target[i][mark]==1) count++;
        }
        double trainaccuracy=count/neural.ntrain;

        int testcount=0;
        for(int i=0;i<neural.testouput.length;i++){
            double max=neural.testouput[i][0];
            int mark=0;
            for(int j=1;j<neural.testouput[0].length;j++){
                if(neural.testouput[i][j]>max) {
                    max=neural.testouput[i][j];
                    mark = j;
                }
            }
            System.out.println(mark+"mark"+neural.testtarget[i][mark]);
            if(neural.testtarget[i][mark]==1) testcount++;*/
        }
        //System.out.println(testcount+"testcount");
       /* DecimalFormat df = new DecimalFormat("0.00");
        double testaccuracy=testcount/neural.ntest;

        System.out.println(trainaccuracy+" "+testaccuracy);*/


    }



