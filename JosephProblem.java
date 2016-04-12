import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by yujingxie on 8/17/15.
 */
public class JosephProblem {
    public static void main(String[] args) {
       Scanner in=new Scanner(System.in);
        System.out.printf("Enter value n: ");
        int n=in.nextInt();
        System.out.printf("Enter value m: ");
        int m=in.nextInt();


        CircleList list=new CircleList(n);
        Node last=list.filterm(n,m);
        System.out.println(list.num);
        /**System.out.println(list.delement.length);*/
        System.out.println(last.data);
        for(int k=0;k<=list.num;k++) System.out.println(list.delement[k]);

        int i=0;
        Node cur=list.head;
        while(i<5) {

            System.out.println(cur.data);
            cur=cur.next;
            i++;
        }
        System.out.println(list.tail.data);
        System.out.println(list.tail.next.data);
    }


    public JosephProblem() {
        System.out.println("This is Joseph Problem!");
    }
}




