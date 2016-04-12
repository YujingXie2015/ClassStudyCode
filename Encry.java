/**
 * Created by yujingxie on 9/15/15.
 */
public class Encry {
    char[] upper;
    char[] lower;

    public Encry() {
        upper = new char[26];
        for (int i = 0; i < 26; i++) {
            upper[i] = (char) ('A' + i);
        }
        lower = new char[26];
        for (int j = 0; j < 26; j++) {
            lower[j] = (char) ('a' + j);
        }
    }

    public char substitute(char cs) {
        if (cs >= 'A' && cs <= 'Z') {
            if (cs == 'Z') return 'A';
            else {
                int j;
                for (j = 0; j < upper.length - 2; j++)
                    if (upper[j] == cs)
                        break;
                return upper[j + 1];
            }
        }
        else if (cs >= 'a' && cs <= 'z') {
            if (cs == 'z') return 'a';
            else {
                int k;
                for (k = 0; k < lower.length - 2; k++)
                    if (lower[k] == cs)
                        break;
                return lower[k + 1];
            }
        }
        return cs;
    }
}
