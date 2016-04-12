/**
 * Created by yujingxie on 11/12/15.
 */
public class KnapsackTest {
    public static void main(String [] args){
        int [] w={6,3,4,2};
        int [] c={30,14,16,9};
        int total=10;

        KnapsackDP knapsack=new KnapsackDP(w,c,total);
        System.out.println(knapsack.f[w.length-1][total]);
        for(int i=0;i<knapsack.optimal.length;i++)
            System.out.print(knapsack.optimal[i]+" ");

    }
}
