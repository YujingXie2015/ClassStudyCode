import java.util.Vector;

/**
 * Created by yujingxie on 10/16/15.
 */
public class PerceptronLearning_qustion1 {
    public static void main(String [] args){
        double[][] sigmap = new double[9][9];
        for (int i = 0; i < 9; i++) {
            sigmap[i][i] = 10;
        }
        GaussionGeneration perceptron=new GaussionGeneration(sigmap);
        Vector u1 = new Vector(9);
        Vector u2 = new Vector(9);
        perceptron.generationu();
        u1=perceptron.getu1();
        u2=perceptron.getu2();
        int N = 20000;
        double alpha = 0.1;
        Vector [] train1=new Vector[N];
        Vector [] train2=new Vector[N];
        Vector [] train=new  Vector[2*N];
        for(int m=0;m<train.length;m++)
            train[m]=new Vector(9);
        train1=perceptron.generateClass(alpha,N,u1);
        train2=perceptron.generateClass(alpha,N,u2);
        System.arraycopy(train1,0,train,0,N);
        System.arraycopy(train2,0,train,N,N);
        Vector y1estimate=new Vector(N);
        Vector y2estimate=new Vector(N);
        Vector yestimate=new Vector(2*N);
        for(int j=0;j<N;j++)
            y1estimate.add(1);
        for(int j=0;j<N;j++)
            y2estimate.add(-1);
        for(int count=0;count<N;count++)
            yestimate.add(count, y1estimate.elementAt(count));
        for(int count1=N;count1<2*N;count1++)
            yestimate.add(count1, y2estimate.elementAt(count1 - N));
        int Niteration=20000;
        perceptron.perceptronlearn(train,yestimate,Niteration);
        Vector wlearn=perceptron.getW();
        double blearn=perceptron.getb();
        int Ntest=10000;
        double [] errorratep=new double[500];
        for(int num=0;num<500;num++) {
            Vector[] test1 = new Vector[Ntest];
            Vector[] test2 = new Vector[Ntest];
            test1 = perceptron.generateClass(alpha, Ntest, u1);
            test2 = perceptron.generateClass(alpha, Ntest, u2);
            Vector ty1est=new Vector(Ntest);
            Vector ty1real=new Vector(Ntest);
            Vector ty2est=new Vector(Ntest);
            Vector ty2real=new Vector(Ntest);
            for(int index2=0;index2<Ntest;index2++)
                ty1est.add(1);
            for(int index2=0;index2<Ntest;index2++)
                ty2est.add(-1);

            int countp = 0;
            double result = 0.0f;
            for (int j = 0; j < test1.length; j++) {
                for (int index1 = 0; index1 < wlearn.size(); index1++)
                    result += (double) wlearn.elementAt(index1) * (double) test1[j].elementAt(index1);
                result += blearn;
                ty1real.add(j, perceptron.classify(result));
                result = 0;
            }
            double result2 = 0.0f;
            for (int j = 0; j < test2.length; j++) {
                for (int index1 = 0; index1 < wlearn.size(); index1++)
                    result2 += (double) wlearn.elementAt(index1) * (double) test2[j].elementAt(index1);
                result2 += blearn;
                ty2real.add(j,perceptron.classify(result2));
                result2 = 0;
            }
            for (int k = 0; k < ty1est.size(); k++)
                if (ty1est.elementAt(k) != ty1real.elementAt(k)) countp++;
            for (int k = 0; k < ty2est.size(); k++)
                if (ty2est.elementAt(k) != ty2real.elementAt(k)) countp++;
            errorratep[num] = (double) countp / (2 * N);
        }

        double mean=0.0;
        double standeviation=0.0;
        double sum=0.0;
        for(int count=0;count<errorratep.length;count++){
            sum+=errorratep[count];
        }
        mean=(double)sum/errorratep.length;

        double sume=0.0f;
        for(int s=0;s<errorratep.length;s++)
            sume+=(double)((double)errorratep[s]-mean)*((double)errorratep[s]-mean);
        double stdev=0.0;
        stdev=Math.sqrt(sume/errorratep.length);
        System.out.println(mean+" "+stdev);



    }
}
