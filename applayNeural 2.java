import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

/**
 * Created by yujingxie on 10/15/15.
 */
public class applayNeural {

    public static void main(String [] args) throws IOException{
        double[] trainError = new double[10];
        double[] testError = new double[10];

        for (int i = 0; i < 10; i++) {
            NeutralNetwork neutral = new NeutralNetwork(100, 35, 100);
            neutral.generateTrainImage();
            trainError[i] = neutral.neuralLearningImage(100, 0.001, 0.001);
            neutral.generateTestImage();
            testError[i] = neutral.testNeuralNetworkImage();
           if(i==5) {
             neutral.writeFile(neutral.w1, "wimage");
             neutral.writeFile(neutral.w2,"w2image");
             }
        }
        double[] train = new double[2];
        train = NeutralNetwork.meanDeviation(trainError);
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(df.format(train[0]) + "±" + df.format(train[1]));
        double[] test = new double[2];
        test = NeutralNetwork.meanDeviation(testError);
        System.out.println(df.format(test[0]) + "±" + df.format(test[1]));
    }

}

