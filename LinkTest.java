/**
 * Created by yujingxie on 1/29/16.
 */
public class LinkTest {
    public static void main(String [] args){

        LinkList list=new LinkList();
        int [] listnum=new int[9];
        for(int i=0;i<listnum.length;i++){
            listnum[i]=i+1;
        }
        list.buildLink(listnum);
        list.oddEven();
        Nodes temp=list.head;
        while (temp!=null){
            System.out.print(temp.getNum()+" ");
            temp=temp.getNext();
        }


    }
}
