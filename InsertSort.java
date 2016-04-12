/**
 * Created by yujingxie on 1/16/16.
 */
public class InsertSort {

    private int [] sortIn;

    public  InsertSort(int [] sort){
        sortIn=sort;
    }

    public int [] iSort(){
        for(int i=1;i<sortIn.length;i++) {
            int temp=sortIn[i];
            int j;
            for (j = i-1; j >=0; j--) {
                if(temp<sortIn[j])
                    sortIn[j+1]=sortIn[j];
                if(temp>=sortIn[j]) {
                    break;
                }
            }

            sortIn[j+1]=temp;

        }
        return sortIn;
    }
}
