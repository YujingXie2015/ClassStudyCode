import java.util.Vector;

/**
 * Created by yujingxie on 10/1/15.
 */
public class Generatemean {
    public static void main(String [] args){

        double [][] sigmav=new double[9][9];
        for(int i=0;i<9;i++){
            sigmav[i][i]=0.6;
        }
        GaussionGeneration gaussion=new GaussionGeneration(sigmav);
        gaussion.generationu();
        Vector mean1=gaussion.getu1();
        Vector mean2=gaussion.getu2();
        for(int index=0;index<mean1.size();index++)
            System.out.println(mean1.elementAt(index)+" ");
        for(int index1=0;index1<mean2.size();index1++)
            System.out.println(mean2.elementAt(index1) + " ");
    }
}
