/**
 * Created by yujingxie on 9/19/15.
 */
public class ExpressionCompute {
    String expression;
    char[] vare;
    int varsize;
    int[] coefficient;
    int cosize;

    public ExpressionCompute(String arge) {
        expression = arge;
        vare = new char[expression.length()];
        varsize = 0;
        coefficient = new int[expression.length()];
        cosize = varsize + 1;
    }

    public void compute() {

        int len = expression.length();
        char[] c = new char[len];
        for (int i = 0; i < len; i++) {
            c[i] = expression.charAt(i);
        }
        int constante = 0;
        for (int k = 0; k < len; k++) {
            if (c[k] > '0' && c[k] <='9') {
                if (k == len - 1) {
                    if (c[k - 1] == '-')
                        constante -= (int)c[k]-48;
                    else constante += (int)c[k]-48;
                }
                else if (c[k + 1] == '+' || c[k + 1] == '-') {
                    if (k == 0)
                        constante +=(int)c[k]-48;
                    else if (c[k - 1] == '-')
                        constante -= (int)c[k]-48;
                    else constante += (int)c[k]-48;
                }
                else if (c[k + 1] == '*') {
                    if (c[k + 2] > '0' && c[k + 2] <= '9') {
                        if (k == 0)
                            constante += ((int)c[k]-48) * ((int)c[k + 2]-48);
                        else if (c[k - 1] == '-')
                            constante -= ((int)c[k]-48) * ((int)c[k + 2]-48);
                        else constante += ((int)c[k]-48) *((int)c[k + 2]-48);
                        k = k + 2;
                    } else {
                        int j;
                        for (j = 0; j < varsize; j++) {
                            if (vare[j] == c[k + 2]) break;
                        }
                        if (j == varsize) {
                            ++varsize;
                            ++cosize;
                            vare[varsize - 1] = c[k + 2];
                            if (k == 0)
                                coefficient[varsize - 1] = (int)c[k]-48;
                            else if (c[k - 1] == '-')
                                coefficient[varsize - 1] -= (int)c[k]-48;
                            else coefficient[varsize - 1] = (int)c[k]-48;
                            k = k + 2;
                        } else {
                            if (k == 0)
                                coefficient[j] +=(int)c[k]-48;
                            else if (c[k - 1] == '-')
                                coefficient[j] -= (int)c[k]-48;
                            else coefficient[j] += (int)c[k]-48;
                            k = k + 2;
                        }
                    }

                }
            }
            else if (((c[k] >= 'a' && c[k] <= 'z') || (c[k]) >= 'A' && c[k] <= 'Z')) {
                if (k == len - 1) {
                    int l;
                    for (l = 0; l < varsize; l++) {
                        if (vare[l] == c[k]) break;
                    }
                    if (l == varsize) {
                        ++varsize;
                        cosize++;
                        vare[varsize - 1] = c[k];
                        if (c[k - 1] == '-')
                            coefficient[l] -= 1;
                        else coefficient[l] = 1;
                    } else {
                        if (c[k - 1] == '-')
                            coefficient[l] -=1;
                        else coefficient[l] +=1;

                    }
                }

                else if (c[k + 1] == '*') {
                        int m;
                        for (m = 0; m < varsize; m++) {
                            if (vare[m] == c[k]) break;
                        }
                        if (m == varsize) {
                            ++varsize;
                            cosize++;
                            vare[varsize - 1] = c[k];
                            if (k == 0)
                                coefficient[m] = (int)c[k + 2]-48;
                            else if (c[k - 1] == '-')
                                coefficient[m] -= (int)c[k + 2]-48;
                            else coefficient[m] = (int)c[k + 2]-48;
                            k = k + 2;
                        }
                        else {
                            if (k == 0)
                                coefficient[m] += (int)c[k + 2]-48;
                            else if (c[k - 1] == '-')
                                coefficient[m] -= (int)c[k + 2]-48;
                            else coefficient[m] += (int)c[k + 2]-48;
                            k = k + 2;
                        }
                    }
                else {
                        int d;
                        for (d= 0; d < varsize; d++) {
                            if (vare[d] == c[k]) break;
                        }
                        if (d== varsize) {
                            ++varsize;
                            cosize++;
                            vare[varsize - 1] = c[k];
                            if (k == 0)
                                coefficient[d] = 1;
                            else if (c[k - 1] == '-')
                                coefficient[d] -= 1;
                            else coefficient[d] = 1;
                            /**System.out.println(vare[d]+"test"+coefficient[d]);*/
                        }
                        else {
                            if (k == 0)
                                coefficient[d] += 1;
                            else if (c[k - 1] == '-')
                                coefficient[d] -= 1;
                            else coefficient[d] += 1;
                        }
                    }
                }
                else continue;
            }
        /**System.out.println(constante);*/
            coefficient[cosize-1]=constante;
           /** for(int a=0;a<cosize-1;a++)
                coefficient[a]-=48; */
        }
}


