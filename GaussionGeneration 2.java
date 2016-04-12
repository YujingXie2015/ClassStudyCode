import java.util.Random;
import java.util.Vector;

/**
 * Created by yujingxie on 9/30/15.
 */
public class GaussionGeneration {
    private int N;
    private int alpha;
    private Vector u1=new Vector(9);
    private Vector u2=new Vector(9);
    private double [][] sigma;
    private Vector w;
    private double b;


    public GaussionGeneration(double[][]sigmav){
       sigma=sigmav;
    }

    public void generationu(){
            Vector mean = new Vector(9);
            for (int i = 0; i < 9; i++)
                mean.addElement(0.0);
            GaussianRandom gaussian = new GaussianRandom();
            for (int k = 0; k < 9; k++)
                u1.add(k, getGaussian((double) mean.elementAt(k), sigma[k][k]));
            for (int a = 0; a < 9; a++)
                u2.add(a, getGaussian((double) mean.elementAt(a), sigma[a][a]));
    }
    public void randomu(){
        Vector mu1=new Vector(9);
        Vector mu2=new Vector(9);
        for(int i=0;i<9;i++) {
            mu1.add(0);
            mu2.add(0);
        }
        int k1=(int)(2+Math.random()*6);
        int k2=(int)(2+Math.random()*6);
        int [] index1=new int[k1+1];
        index1[0]=0;
        int index;
        for(int count=0;count<k1;count++){
            index=(int)(Math.random()*9);
            if(index!=index1[count])
                mu1.set(index,1);
            else while (true){
                index=(int)(Math.random()*9);
                if(index!=index1[count]) break;
            }
            index1[count+1]=index;
        }
        int [] index2=new int[k2+1];
        index2[0]=0;
        int index21=0;
        for(int count1=0;count1<k2;count1++){
            index21=(int)(Math.random()*9);
            if(index21!=index2[count1])
                mu2.set(index21,1);
            else while (true){
                index21=(int)(Math.random()*9);
                if(index21!=index2[count1]) break;
            }
            index2[count1+1]=index21;
        }
        u1=mu1;
        u2=mu2;
    }


    public  Vector getu1(){
        return  u1;
    }

    public  Vector getu2(){
        return  u2;
    }

    public  Vector getW(){
        return  w;
    }

    public  double getb(){
        return  b;
    }

    private Random gRandom = new Random();
    private double getGaussian(double aMean, double aVariance){
        return aMean + gRandom.nextGaussian() * aVariance;
    }
    private double getGaussianr(int aMean, double aVariance){
        return (double)(aMean + gRandom.nextGaussian() * aVariance);
    }

    public Vector [] generateClass(double alpha,int Num, Vector u){
        double [][]newsigma=new double[9][9];
        for(int j=0;j<9;j++)
        newsigma[j][j]=alpha*sigma[j][j];
        Vector [] clustor1=new Vector[Num];
       for(int num=0;num<clustor1.length;num++){
            clustor1[num]=new Vector(9);
            for(int s=0;s<9;s++)
                clustor1[num].add(s,getGaussian((double)u.elementAt(s),newsigma[s][s]));
        }
        return  clustor1;
    }

    public int classify(double result){
        int flag;
        if(result>0) flag=1;
        else  flag=-1;
        return  flag;
    }

    public void perceptronlearn(Vector [] trainset, Vector yestimate, int Niterations){
        int executetime=0;
        Vector wp=new Vector(9);
        for(int m=0;m<9;m++)
            wp.add(0.0);
        double bp=0.0;

        while (executetime<=Niterations){

            Random prandom=new Random();
            int index=prandom.nextInt(trainset.length-1);
            double result=0.0;

            for(int h=0;h<trainset[index].size();h++)
                result+=(double)wp.elementAt(h)*(double)trainset[index].elementAt(h);
            result+=bp;
            int yreal=classify(result);
            if(yreal!=(int)yestimate.elementAt(index)) {
                if((int)yestimate.elementAt(index)==1)
                   for (int g = 0; g < wp.size(); g++)
                    wp.set(g, (double) wp.elementAt(g) + (double) trainset[index].elementAt(g));
                else
                    for (int g = 0; g < wp.size(); g++)
                        wp.set(g, (double) wp.elementAt(g) -(double) trainset[index].elementAt(g));
                if((int)yestimate.elementAt(index)==1)
                    bp=bp+1;
                else  bp=bp-1;
            }
            executetime++;
        }
        w=wp;
        b=bp;
    }

}
