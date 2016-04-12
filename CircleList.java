/**
 * Created by yujingxie on 8/17/15.
 * This class implements the function that create a cycle-linkedlist. And solve the Josph problem.
 */
public class CircleList {

    Node head, tail;
    int [] delement;
    int num=0;

    public CircleList(int total) {
        for (int i = 1; i <= total; i++) {
            addNode(i, null, null);
        }
    }


    public void deleteNode(Node cur) {
        if(cur==head){
            head=cur.getNext();
        }
        if(cur==tail){
            tail = cur.getPre();
        }
        cur.getPre().setNext(cur.getNext());
        cur.getNext().setPre(cur.getPre());
    }

    public void addNode(int d, Node next, Node pre) {
        Node newnode = new Node(d, next, pre);
        if (head == null) {
            head = newnode;
            tail = newnode;
            newnode.setNext(newnode);
            newnode.setPre(newnode);
        } else {

            tail.setNext(newnode);
            newnode.setPre(tail);
            newnode.setNext(head);
            tail = newnode;
            head.setPre(tail);
        }
    }

    public Node filterm(int n,int m){
        Node curNode=head;
        delement=new int[n-1];
        for(int count=1;count<=m;count++){
            if(head==tail) {
                break;
            }
            else if(count==m && head!=tail) {
                delement[num]=curNode.data;
                if(num<n-2) num+=1;
                deleteNode(curNode);
                count = 0;
                curNode=head;
            }
            else curNode=curNode.next;
        }
    return head;
    }


}
