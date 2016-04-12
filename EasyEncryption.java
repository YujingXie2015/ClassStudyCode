import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by yujingxie on 9/15/15.
 */
public class EasyEncryption {

    public static void main(String[] args) throws IOException {
        Scanner scan=new Scanner(System.in);
        int rown1=scan.nextInt();
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(rown1);
        String[] s = new String[rown1];
        for (int k = 0; k < rown1; k++) {
            s[k] = buf.readLine();
        }
        Encry e = new Encry();
        String[] output = new String[rown1];
        for (int i = 0; i < rown1; i++) {
            int len = s[i].length();
            char[] enc = new char[len];
            for (int m = 0; m < len; m++) {
                enc[m] = s[i].charAt(m);
            }
            char[] postenc = new char[len];
            for (int d = 0; d < enc.length; d++)
                postenc[d] = e.substitute(enc[d]);
            output[i] = new String(postenc);
        }
        for(int b=0;b<rown1;b++)
            System.out.println(output[b]);
        buf.close();

    }
}
