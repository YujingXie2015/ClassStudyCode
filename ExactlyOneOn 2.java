import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by yujingxie on 10/15/15.
 */
public class ExactlyOneOn {

    public static void main(String [] args) throws IOException{
        double [] trainError=new double[10];
        double [] testError=new double[10];
        for(int i=0;i<10;i++) {
            NeutralNetwork neutral = new NeutralNetwork(100, 20, 100,0.5);
            neutral.generateTrainBinary();
            neutral.binaryTagert(neutral.input);
            trainError[i]=neutral.neuralLearningBinary(100, 0.001, 0.001);
            neutral.generateTestBinary();
            neutral.binaryTagert(neutral.testdata);
            testError[i]=neutral.testBinaryNeuralNetwork();
            if(i==5) {
                neutral.writeFile(neutral.w1, "w11ex");
                neutral.writeFile(neutral.w2, "w22ex");
            }
        }
        double [] train=new double[2];
        train=NeutralNetwork.meanDeviation(trainError);
        DecimalFormat df = new DecimalFormat("0.000");
        System.out.println(df.format(train[0]*100000) + "±" + df.format(train[1]*100000));
        double [] test=new double[2];
        test=NeutralNetwork.meanDeviation(testError);
        System.out.println(df.format(test[0]*100000) + "±" + df.format(test[1]*100000));


    }
}
