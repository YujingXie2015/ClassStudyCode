import java.util.Random;

/**
 * Created by yujingxie on 10/26/15.
 */
public class Kmeans {
    int k, dimension,n;
    double [][] datapoint;
    int [] rnk;
    int [] rnkold;
    int [] realrnk;
    double [][] muk;
    public Kmeans(int knum,int d, int npoint){
        k=knum;
        dimension=d;
        n=npoint;
        datapoint=new double[n][dimension];
        muk=new double[k][dimension];
        rnk=new int[n];
        rnkold=new int[n];
        realrnk=new int [n];
    }

    public void generateTraindata(){
        double [][] mut=new double[k][dimension];
        double [] sigmat=new double[k];
        for(int i=0;i<k;i++)
            for(int j=0;j<dimension;j++)
                mut[i][j]=3*i+Math.random();
        for(int i=0;i<k;i++)
            sigmat[i]=Math.random();
        for(int i=0;i<k;i++)
            for(int j=i*n/k;j<(i+1)*n/k;j++)
                for(int m=0;m<dimension;m++) {
                    datapoint[j][m] = getGaussian(mut[i][m], sigmat[i]);
                    realrnk[j]=i;
                }
    }

    public void initializeMu(){
        /**Initialize muk*/
        int [] knum=new int[k];
        Random random=new Random();
        for(int i=0;i<k;i++){
            int randomnew=random.nextInt(n);
            if(i==0)
                knum[i]=randomnew;
            else {
                while (true) {
                    int j;
                    for (j = 0; j < i; j++)
                        if (randomnew == knum[j])
                            break;
                    if (j == i) {
                        knum[i] = randomnew;
                        break;
                    }
                    else randomnew=random.nextInt(n);
                }
            }

        }
        for(int i=0;i<k;i++)
            for(int d=0;d<dimension;d++)
                muk[i][d]=datapoint[knum[i]][d];

    }




    public  void KmeansAlgorithm(){
        initializeMu();
        /**E step*/
        for(int i=0;i<n;i++)
             rnkold[i]=rnk[i];

        int tempclas=0;
        for(int i=0;i<n;i++) {
            double min=10000000;
            for (int j = 0; j < k; j++) {
                double distance=0.0;
                for (int m = 0; m < dimension; m++)
                    distance += Math.pow((datapoint[i][m] - muk[j][m]), 2);
                if (distance < min) {
                    min = distance;
                    tempclas=j;
                }
            }
            rnk[i]=tempclas;
        }



        /**M step*/
        double [][] muknew=new double[k][dimension];
        for(int i=0;i<k;i++) {
            int [] sum=new int[k];
            for (int j = 0; j < n; j++) {
                if (rnk[j] == i) {
                    sum[i]++;
                    for(int m=0;m<dimension;m++)
                        muknew[i][m]+=datapoint[j][m];
                }
            }
            for(int m=0;m<dimension;m++)
                muknew[i][m]=muknew[i][m]/sum[i];
        }
        muk=muknew;
    }

    public boolean testConvergence(){
        int i;
        for(i=0;i<n;i++) {
            if (rnk[i]!=rnkold[i])
                break;
        }
        if(i<n) return false;
        else return true;
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

    private Random gRandom = new Random();
    private double getGaussian(double aMean, double aVariance){
        return aMean + gRandom.nextGaussian() * aVariance;
    }

}
