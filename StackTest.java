import java.util.Stack;

/**
 * Created by yujingxie on 8/19/15.
 * This program is to test the Stack study.
 */
public class StackTest {

    public static void main(String [] args){
       /**tack s=new Stack();
        s.push(1);
        s.push(2);
        s.push(4);
        s.push(5);
        System.out.println(s.pop());
        s.push(7);
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.empty());*/

     ExpressionC c=new ExpressionC("1+2+3*4*(2+3)");
        c.pushe(c.expression);
        while(!c.s.empty())
            System.out.println(c.s.pop());
    }

    public StackTest(int n){

    }
    public  StackTest(){}
}
