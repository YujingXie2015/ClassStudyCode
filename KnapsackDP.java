/**
 * Created by yujingxie on 11/13/15.
 */
public class KnapsackDP {

    int [][] f;
    int [] weight;
    int [] cost;
    int totalWeight;
    int [][] count;
    int [] optimal;
    /**int [][] tempweight;*/

    public KnapsackDP(int [] w, int [] c, int weightT){
        weight=w;
        cost=c;
        totalWeight=weightT;
        f=new int[w.length][totalWeight+1];
        count=new int[weight.length][totalWeight+1];
        /**tempweight=new int[weight.length][totalWeight+1];*/

        for(int i=0;i<=totalWeight/weight[0];i++) {
            if(i<totalWeight/weight[0]) {
                for (int j = 0; j < weight[0]; j++) {

                    f[0][j + i * weight[0]] = i * cost[i];
                    count[0][j+i*weight[0]]=i;
                }
            }
            else{
                for(int j=i*weight[0];j<=totalWeight;j++) {
                    f[0][j] = i * cost[0];
                    count[0][j]=i;
                }
            }
        }


        for(int i=1;i<weight.length;i++)
            for(int j=0;j<=totalWeight;j++)
                for(int k=0;k<=j/weight[i];k++){
                    if(f[i-1][j-k*weight[i]]+k*cost[i]>f[i][j]) {
                        f[i][j] = f[i - 1][j - k * weight[i]] + k * cost[i];
                        count[i][j]=k;
                    }
                }
        optimal=new int[weight.length];
        optimal[weight.length-1]=count[weight.length-1][totalWeight];
        for(int i=optimal.length-2;i>=0;i--) {
            int tempweight=0;
            for(int j=i+1;j<optimal.length;j++)
                tempweight+=optimal[j]*weight[j];
            tempweight=totalWeight-tempweight;
            optimal[i] = count[i][tempweight];
        }
    }


}
