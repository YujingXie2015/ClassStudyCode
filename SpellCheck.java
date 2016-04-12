import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by yujingxie on 9/23/15.
 */
public class SpellCheck {

    public static void main(String [] args) throws IOException{
       /**String [] dic=new String[5];
        dic[0]="test";
        dic[1]="apple";
        dic[2]="testa";
        dic[3]="est";
        dic[4]="tast";
        SpellCheckClass spell=new SpellCheckClass(dic);
        spell.checkspell("tst");
        String s="test";
        System.out.println(s.substring(1,4));
        /**for(int t=0;t<spell.count;t++)
            System.out.println(spell.similarw[t]);
        System.out.println(spell.similarw[0]);
        System.out.println(spell.count);*/
        Scanner scan=new Scanner(System.in);
        String [] sinput=new String[10000];
        int countw=0;
        while(true){
            String word=scan.next();
            if(word.equals("#")) {
                break;
            }
            else {
                countw++;
                sinput[countw - 1] = word;
            }
        }
        String [] dictionarys=new String[countw];
        for(int i=0;i<countw;i++)
            dictionarys[i]=sinput[i];
        SpellCheckClass spellc=new SpellCheckClass(dictionarys);
        /**for(int j=0;j<spellc.dictionary.length;j++)
            System.out.println(spellc.dictionary[j]);
        Scanner scanner=new Scanner(System.in);
       while(true){
            String wordt=scan.next();
            if(wordt.equals("#")) {
                break;
            }
            else spellc.checkspell(wordt);
        }
        scan.close();*/

       String [] wordt=new String[50];
        int wordtsize=0;
        while(true){
            String word=scan.next();
            if(word.equals("#")) {
                break;
            }
            else {
                wordtsize++;
                wordt[wordtsize - 1] = word;
            }
        }
        scan.close();
        for(int s=0;s<wordtsize;s++)
            spellc.checkspell(wordt[s]);
    }
}
