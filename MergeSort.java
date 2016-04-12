/**
 * Created by yujingxie on 1/17/16.
 */
public class MergeSort {
    int [] sortIn;

    public  MergeSort(int [] array){
        sortIn=array;
    }

    public void mSort(int [] array,int left, int right){
        if(right>left) {
            int middle = (right+left) / 2;
            mSort(array, left, middle);
            mSort(array, middle + 1, right);
            mergeS(array, left, right,middle);
        }
    }

    public int [] mergeS(int[] array,int l, int r,int middle) {
        int index1, index2;
        int[] temparray = new int[r - l + 1];
        for (int i = l; i <= r; i++)
            temparray[i - l] = array[i];

        index1 = l;
        index2 = middle + 1;
        int count=l;
        while (index1 <= middle && index2 <= r) {
            if (temparray[index1-l] > temparray[index2-l]) {
                array[count++] = temparray[index2-l];
                index2++;
            } else {
                array[count++] = temparray[index1-l];
                index1++;
            }
        }

        if(index1>middle && index2<=r){
            for(int i=index2;i<=r;i++)
                array[count++]=temparray[i-l];
        }
        else if(index1<=middle && index2>r){
            for(int i=index1;i<=middle;i++)
                array[count++]=temparray[i-l];
        }
        return  array;

    }


}
