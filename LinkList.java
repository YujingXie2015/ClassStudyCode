/**
 * Created by yujingxie on 1/29/16.
 */
public class LinkList {
    Nodes head;
    Nodes tail;

    public LinkList(){
        head=null;
        tail=null;
    }

    public Nodes buildLink(int [] element){
        for(int i=0;i<element.length;i++){
            Nodes newNode=new Nodes(element[i],null);
            if(i==0){
                head=tail=newNode;
            }
            else {
                insertAfter(newNode);
            }
        }

        return head;


    }

    public  void insertAfter(Nodes in){
        tail.setNext(in);
        tail=tail.getNext();
    }


    public Nodes oddEven(){
        Nodes oHead=this.head;
        Nodes eHead=this.head.getNext();
        Nodes p=oHead;
        Nodes q=eHead;

        while (!(p.getNext().getNext()==null || q.getNext().getNext()==null)){
            p.setNext(p.getNext().getNext());
            q.setNext(q.getNext().getNext());
            p=p.getNext();
            q=q.getNext();
        }
        if(p.getNext().getNext()==null){
            p.setNext(eHead);

        }
        else if (q.getNext().getNext()==null){
            p.setNext(q.getNext());
            p=p.getNext();
            q.setNext(null);
            p.setNext(eHead);


        }
        return oHead;
    }



}
