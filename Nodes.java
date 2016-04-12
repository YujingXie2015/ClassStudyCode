/**
 * Created by yujingxie on 1/29/16.
 */
public class Nodes {

    private int num;
    private Nodes next;

    public Nodes(int numNode, Nodes nextNode){
        num=numNode;
        next=nextNode;
    }
    public int getNum(){
        return num;
    }

    public Nodes getNext(){
        return next;
    }

    public void setNum(int number){
        this.num=number;
    }
    public void setNext(Nodes nextN){
        this.next=nextN;
    }
}
