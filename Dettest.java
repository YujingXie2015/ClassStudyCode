/**
 * Created by yujingxie on 10/4/15.
 */
public class Dettest {
    public static void  main(String [] args){

        Detcompute det=new Detcompute();
        double [][] test=new double[4][4];
        for(int i=0;i<test.length;i++)
            for(int j=0;j<test.length;j++)
                test[i][j]=1;
        System.out.println(det.detmatrixcompute(test));
    }
}
