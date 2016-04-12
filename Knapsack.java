/**
 * Created by yujingxie on 11/12/15.
 */
public class Knapsack {
    int [] weight;
    int [] cost;
    int totalWeight;
    int  count;

    public Knapsack(int [] w, int [] c, int weightT){
        weight=w;
        cost=c;
        totalWeight=weightT;
    }


    public int ComputeKnapSack(int i,int v){
        if(v==0)
            return 0;
        if(i==0)
            return v/weight[0]*cost[0];

        else {
            int max = 0;
            for (int k = 0; k <= v/weight[i]; k++) {
                if(v-k*weight[i]<0) break;
                else if ((ComputeKnapSack(i - 1, v - k * weight[i]) + k * cost[i] )> max) {
                    max = ComputeKnapSack(i - 1, v - k * weight[i]) + k * cost[i];
                    count=k;
                }
            }
            System.out.println(count);
            return max;
        }
    }

}
