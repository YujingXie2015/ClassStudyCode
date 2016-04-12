/**
 * Created by yujingxie on 1/16/16.
 */
public class QuickSort {
    int [] sortIn;

    public QuickSort(int [] arrayIn){
        sortIn=arrayIn;
    }

    public  void qSort(int left, int right){
        int pivot=sortIn[left];
        sortIn[left]=sortIn[right];
        int sRight=right;

        while (true){
            for(;left<right;left++) {
                if (sortIn[left] > pivot) {
                    sortIn[right] = sortIn[left];
                    right--;
                    break;
                }
            }
            for(;right>left;right--)
            if(sortIn[right]<pivot){
                sortIn[left]=sortIn[right];
                left++;
                break;
            }
            if(left==right) {
               sortIn[left]=pivot;
                break;
            }
        }
        if(left>=1)
        qSort(0,left-1);
        if (right<sRight)
        qSort(right+1,sRight);

    }

}
