/**
 * Created by yujingxie on 9/22/15.
 */
public class WordConvert {
    String[] words;
    int[] numbers;
    int result;

    public WordConvert() {
        words = new String[32];
        words[0] = "negative";
        words[1] = "zero";
        words[2] = "one";
        words[3] = "two";
        words[4] = "three";
        words[5] = "four";
        words[6] = "five";
        words[7] = "six";
        words[8] = "seven";
        words[9] = "eight";
        words[10] = "nine";
        words[11] = "ten";
        words[12] = "eleven";
        words[13] = "twelve";
        words[14] = "thirteen";
        words[15] = "fourteen";
        words[16] = "fifteen";
        words[17] = "sixteen";
        words[18] = "seventeen";
        words[19] = "eighteen";
        words[20] = "nineteen";
        words[21] = "twenty";
        words[22] = "thirty";
        words[23] = "forty";
        words[24] = "fifty";
        words[25] = "sixty";
        words[26] = "seventy";
        words[27] = "eighty";
        words[28] = "ninety";
        words[29] = "hundred";
        words[30] = "thousand";
        words[31] = "million";

        numbers = new int[31];
        numbers[0] = 0;
        numbers[1] = 1;
        numbers[2] = 2;
        numbers[3] = 3;
        numbers[4] = 4;
        numbers[5] = 5;
        numbers[6] = 6;
        numbers[7] = 7;
        numbers[8] = 8;
        numbers[9] = 9;
        numbers[10] = 10;
        numbers[11] = 11;
        numbers[12] = 12;
        numbers[13] = 13;
        numbers[14] = 14;
        numbers[15] = 15;
        numbers[16] = 16;
        numbers[17] = 17;
        numbers[18] = 18;
        numbers[19] = 19;
        numbers[20] = 20;
        numbers[21] = 30;
        numbers[22] = 40;
        numbers[23] = 50;
        numbers[24] = 60;
        numbers[25] = 70;
        numbers[26] = 80;
        numbers[27] = 90;
        numbers[28] = 100;
        numbers[29] = 1000;
        numbers[30] = 1000000;
        result = 0;
    }

    public void convertn(String[] inputword) {
        int mcount;
        for (mcount = 0; mcount < inputword.length; mcount++)
            if (inputword[mcount].equals("million"))
                break;

        if (mcount < inputword.length) {
            int k;
            for (k = 0; k < mcount; k++)
                if (inputword[k].equals("hundred"))
                    break;
            if (k == mcount) {
                for (int j = 0; j < mcount; j++)
                    for (int m = 1; m < words.length; m++)
                        if (inputword[j].equals(words[m])) {
                            result += numbers[m - 1];
                            break;
                        }
                result = result * 1000000;
            } else {
                for (int p = 0; p < k; p++)
                    for (int t = 0; t < words.length; t++) {
                        if (inputword[p].equals(words[t])) {
                            result += numbers[t - 1] * 100;
                            break;
                        }
                    }
                if(k<mcount-1) {
                    for (int r = k + 1; r < mcount; r++)
                        for (int a = 0; a < words.length; a++) {
                            if (inputword[r].equals(words[a])) {
                                result += numbers[a - 1];
                                break;
                            }
                        }
                }
                result = result * 1000000;
            }

        }
        int scount;
        if (mcount == inputword.length)
            mcount = 0;
        else mcount = mcount + 1;
        for (scount = mcount; scount < inputword.length; scount++)
            if (inputword[scount].equals("thousand")) break;
        if (scount < inputword.length) {
            int u;
            for (u = mcount; u < scount; u++)
                if (inputword[u].equals("hundred"))
                    break;
            /**System.out.println(u+"this is u");*/
            if (u == scount) {
                for (int e = mcount; e < scount; e++)
                    for (int q = 0; q < words.length; q++) {
                        if (inputword[e].equals(words[q])) {
                            result += numbers[q - 1] * 1000;
                            break;
                        }
                    }
            } else {
                for (int f = mcount; f < u; f++)
                    for (int h = 0; h < words.length; h++) {
                        if (inputword[f].equals(words[h])) {
                            result += numbers[h - 1] * 100 * 1000;
                            break;
                        }
                    }
               /** System.out.println("u:"+u+""+"scount"+scount);*/
                if(u<scount-1) {
                    /**System.out.println("execute this paragram");*/
                    for (int c = u + 1; c < scount; c++)
                        for (int g = 0; g < words.length; g++) {
                            if (inputword[c].equals(words[g])) {
                                result += numbers[g - 1] * 1000;
                                break;
                            }
                        }
                }
            }
        }
        int hcount;
        if (scount == inputword.length)
            scount = mcount;
        else scount = scount + 1;
        for (hcount = scount; hcount < inputword.length; hcount++)
            if (inputword[hcount].equals("hundred"))
                break;
       /** System.out.println(hcount+"hundred index");
        System.out.println(scount+"scount");*/
        if (hcount < inputword.length) {
            for (int v = scount; v < hcount; v++)
                for (int n = 0; n < words.length; n++) {
                    if (inputword[v].equals(words[n])) {
                        result += numbers[n - 1] * 100;
                        break;
                    }

                }
            if(hcount<inputword.length-1) {
                for (int b = hcount + 1; b < inputword.length; b++)
                    for (int x = 0; x < words.length; x++) {
                        if (inputword[b].equals(words[x])) {
                            result += numbers[x - 1];
                            break;
                        }
                    }
            }
        } else {
            for (int l = scount; l < inputword.length; l++)
                for (int z = 0; z < words.length; z++) {
                    if (inputword[l].equals(words[z])) {
                        result += numbers[z - 1];
                        break;
                    }
                }

        }
    }
}
