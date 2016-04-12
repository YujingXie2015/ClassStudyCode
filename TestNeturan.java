/**
 * Created by yujingxie on 10/14/15.
 */
public class TestNeturan {

    public static void main(String [] args){
          NeutralNetwork neutral=new NeutralNetwork(1000,100,1000);
          neutral.generateInput();
          neutral.neuralLearning(100,0.1,1);
    }
}
