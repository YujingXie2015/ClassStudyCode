/**
 * Created by yujingxie on 1/17/16.
 */
public class HeapSort {
    int min;
    int [] heapArray;

    public HeapSort(int [] array){
        heapArray=array;
    }

    public void sort(int [] array){
        int mark=1;
        while (mark<=array.length){
            deleteMin(array,mark);
            System.out.print(min+" ");
            mark++;
        }

    }

    public void deleteMin(int [] array,int count){
        min=array[0];
        int temp=array[0];
        array[0]=array[array.length-count];
        array[array.length-count]=1000000;
        int index=0;
        siftdown(0, array);
    }

    public void siftup(int index,int [] array){

        while (array[index]<array[index/2] && index/2>=0){
            int temp=array[index];
            array[index]=array[index/2];
            array[index/2]=temp;
            index=index/2;
        }


    }
    public void siftdown(int index,int [] array) {
        if (2 * index + 1 < array.length && 2 * index + 2 >= array.length) {
            int temp = array[index];
            if (array[2 * index + 1] < array[index]) {
                array[index] = array[2 * index + 1];
                array[2 * index + 1] = temp;
            }

        } else if (2 * index + 2 < array.length) {
             int temp=array[index];
            while (2 * index + 2 < array.length) {
                int minorindex;
                if(array[2*index+1]<array[2*index+2])minorindex=2*index+1;
                else minorindex=2*index+2;
                if(temp>array[minorindex]) {
                    array[index] = array[minorindex];
                    index = minorindex;
                }
                else break;
            }
            if(2*index+1>=array.length || (2*index+2<array.length)){
                array[index]=temp;
            }
            else if(2*index+1<array.length && 2*index+2>=array.length){
                if (array[2 * index + 1] < temp) {
                    array[index] = array[2 * index + 1];
                    array[2 * index + 1] = temp;
                }

            }

        }
    }



    public void createHeap(int [] array){
        int len=array.length;
        for(int i=(len-1)/2;i>=0;i-- )
            siftdown(i,array);
        for(int i=0;i<array.length;i++)
            System.out.println(array[i]+" ");

    }


}
