/**
 * Created by yujingxie on 1/16/16.
 */
public class SelectionSort {
    int [] sortIn;

    public SelectionSort(int [] testarray){
        sortIn=testarray;

    }

    public int [] selSort(){
        int len=sortIn.length;
        for(int i=0;i<len;i++) {
            int min=sortIn[i];
            int temp=sortIn[i];
            int index=i;
            for (int j = i+1; j <= len - 1; j++) {
                if (sortIn[j]<min){
                    min=sortIn[j];
                    index=j;
                }
            }
            sortIn[i]=min;
            sortIn[index]=temp;
        }
        return sortIn;

    }
}
