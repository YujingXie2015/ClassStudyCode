import java.util.Random;
import java.util.Vector;

/**
 * Created by yujingxie on 10/3/15.
 */
public class Randommean {
    public static void  main(String [] args){
    Vector mu1=new Vector(9);
    Vector mu2=new Vector(9);
    for(int i=0;i<9;i++) {
        mu1.add(0);
        mu2.add(0);
    }
        int k1=(int)(2+Math.random()*6);
        int k2=(int)(2+Math.random()*6);
        System.out.println(k1+""+k2);
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
        for(int i=0;i<mu1.size();i++)
            System.out.print(mu1.elementAt(i));
        System.out.println("break");
        for(int j=0;j<mu2.size();j++)
            System.out.print(mu2.elementAt(j));


    }
}
