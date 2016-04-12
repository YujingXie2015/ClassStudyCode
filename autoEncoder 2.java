import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

/**
 * Created by yujingxie on 10/14/15.
 */
public class autoEncoder {
    public static void main(String[] args) throws IOException {
        double[] trainError = new double[10];
        double[] testError = new double[10];

        for (int i = 0; i < 10; i++) {
            NeutralNetwork neutral = new NeutralNetwork(100, 20, 100);
            neutral.generateInput();
            trainError[i] = neutral.neuralLearning(100, 0.001, 1);
            neutral.generateTestdata();
            testError[i] = neutral.testNeuralNetwork();
            /**if(i==5) {
                neutral.writeFile(neutral.w1, "w1");
                neutral.writeFile(neutral.w2,"w2");
            }*/
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

