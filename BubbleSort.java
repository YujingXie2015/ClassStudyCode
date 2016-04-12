/**
 * Created by yujingxie on 1/16/16.
 */
public class BubbleSort {
    int [] sortIn;

    public BubbleSort(int [] arrary){
        sortIn=arrary;
    }

    public int [] sort(){
        int len=sortIn.length;
        for(int j=1;j<=len-1;j++) {
            int count=0;
            for (int i = len - 1; i >= j; i--) {
                int temp = sortIn[i];
                if (sortIn[i] < sortIn[i - 1]) {
                    sortIn[i] = sortIn[i - 1];
                    sortIn[i - 1] = temp;
                    count++;
                }
            }
            if(count==0) break;
        }
        return sortIn;

    }
}
