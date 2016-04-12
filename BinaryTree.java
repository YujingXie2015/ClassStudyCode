import java.util.Arrays;

/**
 * Created by yujingxie on 9/28/15.
 */
public class BinaryTree {
     TreeNode root;
     int TreeSize;
     int [] midscan;
     int [] postscan;

    public BinaryTree(int size,int [] midorder,int [] postorder){
        TreeSize=size;
        midscan=midorder;
        postscan=postorder;
    }
    private int index=0;
    private int [] preorder=new int[100];
    public void prescan(TreeNode treeroot) {
        if (treeroot != null) {
            /**int count=0;
            System.out.println("This is the " + ++count + "time to execute precan function");
            System.out.println(index);
            preorder[index]=treeroot.info;*/
            System.out.print(treeroot.info + " ");
            /**if(treeroot.leftchild!=null && treeroot.rightchild!=null)
             index++;*/
            prescan(treeroot.leftchild);
            prescan(treeroot.rightchild);
        }
    }

    int executime=0;
    public TreeNode rebuildTree(int [] midorder, int [] postorder ) {
        TreeNode returnr;
        if (midorder.length >= 1) {
            executime++;
            TreeNode troot = new TreeNode();
            troot.setInfo(postorder[postorder.length - 1]);
            if (executime == 1)
                root = troot;
            if (midorder.length == 1) {
                troot.setLeftchild(null);
                troot.setRightchild(null);
            } else if (midorder.length > 1) {
                if (midorder[midorder.length - 1] == postorder[postorder.length - 1]) {
                    troot.setRightchild(null);
                    int[] newpost = new int[postorder.length - 1];
                    int[] newmid = new int[midorder.length - 1];
                    newpost = Arrays.copyOfRange(postorder, 0, postorder.length - 1);
                    newmid = Arrays.copyOfRange(midorder, 0, midorder.length - 1);
                    troot.setLeftchild(rebuildTree(newmid, newpost));
                } else if (midorder[0] == postorder[postorder.length - 1]) {
                    troot.setLeftchild(null);
                    int[] newpostr = new int[postorder.length - 1];
                    int[] newmidr = new int[midorder.length - 1];
                    newpostr = Arrays.copyOfRange(postorder, 0, postorder.length - 1);
                    newmidr = Arrays.copyOfRange(midorder, 1, midorder.length);
                    troot.setLeftchild(rebuildTree(newmidr, newpostr));
                } else {
                    int rindex=0;
                    /**for(int k=0;k<midorder.length;k++)
                        System.out.println(midorder[k]);*/
                    for( int s=0;s<midorder.length;s++){
                        if(midorder[s]==postorder[postorder.length-1]) {
                            rindex = s;
                            break;
                        }
                    }
                    int count=0;
                   /** System.out.println("This is the ++count time execute"+"rindex+"+ rindex);*/
                    int[] newpostl = new int[rindex];
                    int[] newmidl = new int[rindex];
                    int[] newpostr = new int[postorder.length - 1 - rindex];
                    int[] newmidr = new int[midorder.length - 1 - rindex];
                    newpostl = Arrays.copyOfRange(postorder, 0, rindex);
                    newmidl = Arrays.copyOfRange(midorder, 0, rindex);
                    newpostr = Arrays.copyOfRange(postorder, rindex, postorder.length - 1);
                    newmidr = Arrays.copyOfRange(midorder, rindex + 1, midorder.length);
                    troot.setLeftchild(rebuildTree(newmidl, newpostl));
                    troot.setRightchild(rebuildTree(newmidr, newpostr));
                }

            }
            returnr=troot;

        }
        else returnr=null;

        return  returnr;
    }
}
