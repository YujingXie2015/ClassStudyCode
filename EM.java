import java.util.Random;
/**
 * Created by yujingxie on 10/26/15.
 */
public class EM {
    int dimension,k,n;
    double [][] datapoint;
    double [][] emmuk;
    double [][][] sigmak;
    double [] pik;
    double [][] gaussianvalue;
    double [][] rnk;

    double [] nk;
    double [][] emmukold;
    double [] pikold;
    double [][][] sigmakold;
    double probabilitybefore;
    double probabilityafter;
    int [] realrnk;

    public EM(int d,int knum,int num){
        dimension=d;
        k=knum;
        n=num;
        emmuk=new double[k][dimension];
        sigmak=new double[k][dimension][dimension];
        sigmakold=new double[k][dimension][dimension];
        pik=new double[k];
        gaussianvalue=new double[n][k];
        rnk=new double[n][k];
        nk=new double[k];
        emmukold=new double[k][dimension];
        pikold=new double[k];
        realrnk=new int[n];
        datapoint=new double[n][dimension];

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
            for (int j = i; j < n; j += k){
                for (int m = 0; m < dimension; m++)
                    datapoint[j][m] = getGaussian(mut[i][m], sigmat[i]);
            realrnk[j]=i;
        }

    }

    public void initialize(){
        Kmeans kmeanapply=new Kmeans(k,dimension,n);
        kmeanapply.generateTraindata();
        kmeanapply.initializeMu();
        kmeanapply.KmeansAlgorithm();
        for(int i=0;i<k;i++)
            for(int j=0;j<dimension;j++)
            emmuk[i][j]=kmeanapply.muk[i][j];
        for(int i=0;i<k;i++)
            for(int j=0;j<dimension;j++)
              for(int d=0;d<dimension;d++){
                if(d==j)
                    sigmak[i][j][d]=1;
                else sigmak[i][j][d]=0;
            }
        for(int i=0;i<k;i++) {
            int count=0;
            for (int j = 0; j < n; j++)
                if(kmeanapply.rnk[j]==i)
                    count++;
            pik[i]=count/n;
        }
    }

    /** Caulate the probability*/
    public  double  computlogprobability(){
        double probability=0.0;

        /**Calculate N(mu, sigma)*/
        for(int i=0;i<n;i++)
            for(int j=0;j<k;j++)
                 {
                     double [] differenceu=new double[dimension];
                     for(int m=0;m<dimension;m++)
                         differenceu[m]=datapoint[i][m]-emmuk[j][m];
                     double [][] sigmareverse=new double[dimension][dimension];
                     for(int t=0;t<dimension;t++)
                         for(int s=0;s<dimension;s++) {
                             if (t == s)
                                 sigmareverse[t][s] = 1 / sigmak[j][t][s];
                             else sigmareverse[t][s]=0;
                         }
                     double [] temresult=new double[dimension];
                     for(int d=0;d<dimension;d++) {
                         temresult[d] = differenceu[d] * sigmareverse[d][d];
                     }
                    double temp=0.0;
                     for(int t=0;t<dimension;t++)
                         temp+=temresult[t]*differenceu[t];
                     temp=Math.exp(-temp/2);
                     double temp1=Math.pow(Math.PI*2,dimension/2);
                     Detcompute detcompute=new Detcompute();
                     double det=detcompute.detmatrixcompute(sigmak[j]);
                     double temp2=Math.pow(det,1/2);
                     double temp3=temp1*temp2;
                     gaussianvalue[i][j]=temp/temp3;
                }
        for(int i=0;i<n;i++)
            for(int j=0;j<k;j++)
                probability+=pik[j]*gaussianvalue[i][j];
        return probability;
    }

    public void emAlgorithm(){
        /**evaluate the probability*/
        probabilitybefore=computlogprobability();

        /**store the  old pik,emmuk, and sigmak*/
        for(int i=0;i<k;i++)
            for(int j=0;j<dimension;j++)
                emmukold[i][j]=emmuk[i][j];
        for(int i=0;i<k;i++)
            pikold[i]=pik[i];
        for(int i=0;i<k;i++)
            for(int j=0;j<dimension;j++)
                for(int d=0;d<dimension;d++)
                    sigmakold[i][j][d]=sigmak[i][j][d];

        /** Calculate rnk value for every n, k*/
        double [] probabilityn=new double[n];
        for(int i=0;i<n;i++)
            for(int j=0;j<k;j++)
                probabilityn[i]+=pik[j]*gaussianvalue[i][j];
        for(int i=0;i<n;i++)
            for(int j=0;j<k;j++)
                rnk[i][j]=pik[j]*gaussianvalue[i][j]/probabilityn[i];

        /**calculate NK value*/
        for(int i=0;i<k;i++)
            for(int j=0;j<n;j++)
                nk[i]+=rnk[j][i];

        /**calculate the new emmuk*/

        for(int i=0;i<k;i++){
            double [] tempmu=new double[dimension];
            for(int j=0;j<n;j++)
                for (int d=0;d<dimension;d++)
                    tempmu[d]+= datapoint[j][d]*rnk[j][i];
            for(int m=0;m<dimension;m++)
                emmuk[i][m]=tempmu[m]/nk[i];
        }

        /**Calculate the new sigmak*/
        for(int j=0;j<k;j++) {
            double[][] tempu = new double[dimension][dimension];
            for (int i = 0; i < n; i++) {
                double[] differenceu = new double[dimension];
                for (int m = 0; m < dimension; m++)
                    differenceu[m] = datapoint[i][m] - emmuk[j][m];
                for (int d= 0; d < dimension; d++)
                    for (int t = 0; t < dimension; t++)
                        tempu[d][t] += rnk[i][j]*differenceu[d] * differenceu[t];
            }
            for(int r=0;r<dimension;r++)
                for(int y=0;y<dimension;y++)
                    sigmak[j][r][y]=tempu[r][y]/nk[j];
        }


        /**Calculte the new pik*/

        for(int i=0;i<k;i++)
            pik[i]=nk[i]/n;

        /** evaluate the after possibility*/
        probabilityafter=computlogprobability();

    }

    public boolean testConvergency(){
        if(Math.abs(probabilityafter-probabilitybefore)<=0.01)
            return true;
        else  return false;
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
