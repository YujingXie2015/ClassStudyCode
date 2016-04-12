import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by yujingxie on 9/22/15.
 */
public class NumberWordConvert  {
    public static void main (String [] args) throws IOException{
        Scanner scan=new Scanner(System.in);
        int rown1=scan.nextInt();
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        String[] s = new String[rown1];
        for (int k = 0; k < rown1; k++) {
            s[k] = buf.readLine();
        }
        for(int i=0;i<rown1;i++) {
            WordConvert w = new WordConvert();
            String [] splits=s[i].split(" ");
            if(splits[0].equals("negative")) {
                String [] splits1=new  String[splits.length-1];
                for (int m = 0; m < splits1.length; m++)
                    splits1[m]=splits[m+1];
                w.convertn(splits1);
                System.out.println(-w.result);
            }
            else{
           /** System.out.println(splits.length);
            for(int f=0;f<splits.length;f++)
                System.out.println(splits[f]);*/
            w.convertn(splits);
            System.out.println(w.result);
            }
        }
    }

}
