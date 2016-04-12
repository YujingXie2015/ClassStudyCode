/**
 * Created by yujingxie on 1/16/16.
 */
public class ShellSort {
    int [] sortIn;

    public ShellSort(int [] testarray){
        sortIn=testarray;
    }

    public int [] sSort(){
        int slot=sortIn.length/2;
        int len=sortIn.length;

        int s;
        for( s=slot;s>=1;s/=2) {
            for (int i = 0; i < slot; i++)
                for (int j = i + s; j < len; j += s) {
                    int temp = sortIn[j];
                    int k;
                    for (k = j - s; k >= i; k -= s) {
                        if (sortIn[k] > temp) continue;
                        if (sortIn[k] <= temp) break;
                    }
                    if (k < j - s) {
                        for (int t = j; t > k + s; t -= s)
                            sortIn[t] = sortIn[t - s];
                        sortIn[k + s] = temp;
                    }
                }
            System.out.println(s);
            for(int i=0;i<len;i++)
                System.out.print(sortIn[i]+" ");
        }

   /*         for(int i=1;i<len;i++) {
                int temp=sortIn[i];
                int j;
                for ( j = i-1; j >= 0; j--) {
                    if(sortIn[j]>temp)continue;
                    else if(sortIn[j]<=temp) break;
                }
                if(j<i-1) {
                    for(int k=i;k>j+1;k--)
                        sortIn[k] = sortIn[k-1];
                    sortIn[j+1] = temp;
                }

            }*/
        return sortIn;
        }

    }

