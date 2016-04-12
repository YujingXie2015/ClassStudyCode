/**
 * Created by yujingxie on 8/17/15.
 */
public class Node {
    int data;
    Node next,pre;
    public Node(){
        data=0;
        next=null;
        pre=null;
    }
    public Node(int d, Node next,Node pre){
        this.data=d;
        this.next=next;
        this.pre=pre;
    }
    public int getData(){
        return this.data;
    }
    public Node getNext(){
        return this.next;
    }

    public Node getPre(){
        return this.pre;
    }

    public void setData(int d1){
      this.data=d1;
    }
    public void setNext(Node next1){
        this.next=next1;
    }
    public void setPre (Node pre1){
        this.pre=pre1;
    }

}
