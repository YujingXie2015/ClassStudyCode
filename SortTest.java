/**
 * Created by yujingxie on 1/16/16.
 */
public class SortTest {
    public static void main(String [] args){
        int [] testarray={10,2,4,6,1,30,20,15,4,2,24};
//        BubbleSort bsort=new BubbleSort(testarray);
//        int [] outarray=bsort.sort();
   /*     QuickSort qsort=new QuickSort(testarray);
        qsort.qSort(0,testarray.length-1);*/
//        InsertSort isort=new InsertSort(testarray);
//        int [] outarray=isort.iSort();
//        SelectionSort ssort=new SelectionSort(testarray);
//        int [] outarray=ssort.selSort();
        /*ShellSort shell=new ShellSort(testarray);
        int [] outarray=shell.sSort();*/
       /* MergeSort msort=new MergeSort(testarray);
        msort.mSort(msort.sortIn,0,testarray.length-1);
        for(int i=0;i<testarray.length;i++)
            System.out.print(msort.sortIn[i]+" ");*/

        HeapSort hsort=new HeapSort(testarray);
        hsort.createHeap(hsort.heapArray);
        hsort.sort(hsort.heapArray);
    }
}
