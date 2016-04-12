import java.util.Scanner;

/**
 * Created by yujingxie on 9/19/15.
 */
public class ExpressionEqual {
    public static void main(String [] args){
        Scanner scan=new Scanner(System.in);
        int group=scan.nextInt();
        String [] s1=new String[group];
        String [] s2=new String[group];
        for(int s=0;s<group;s++) {
            s1[s] = scan.next();
            s2[s] = scan.next();
        }
        for(int t=0;t<group;t++){
        ExpressionCompute e1=new ExpressionCompute(s1[t]);
        ExpressionCompute e2=new ExpressionCompute(s2[t]);
        e1.compute();
        e2.compute();
        /**System.out.println(e1.varsize);
        System.out.println(e2.varsize);
        for(int h=0;h<e1.varsize;h++)
            System.out.println(e1.vare[h]);
        for(int i=0;i<e1.cosize;i++)
            System.out.println(e1.coefficient[i]);
        for(int h=0;h<e1.varsize;h++)
            System.out.println(e2.vare[h]);
        for(int i=0;i<e1.cosize;i++)
            System.out.println(e2.coefficient[i]);*/
        if(e1.varsize!=e2.varsize)
            System.out.println("NO");
        else if(e1.coefficient[e1.cosize-1]!=e2.coefficient[e2.cosize-1])
            System.out.println("NO");
        else{
            int a;
            for(a=0;a<e1.varsize;a++){
                int b;
                for(b=0;b<e2.varsize;b++)
                    if(e1.vare[a]==e2.vare[b]) break;
                if(b==e2.varsize){
                    System.out.println("NO");
                    break;
                }
                else if(e1.coefficient[a]!=e2.coefficient[b]){
                    System.out.println("NO");
                    break;
               }
            }
            if(a==e1.varsize)
                System.out.println("YES");
        }
    }
  }
}


