import java.util.Stack;

/**
 * Created by yujingxie on 8/22/15.
 */
  public class ExpressionC {
    String expression;
    int result;
    Stack s;
    public ExpressionC(String expressionp) {
        expression = expressionp;
        s=new Stack();
    }

    public void pushe(String expression) {
        int len = expression.length();
        char[] c = new char[len];
        for (int i = 0; i < len; i++) {
            c[i] = expression.charAt(i);
        }
       for (int k = 0; k < c.length; k++) {
            if (c[k] != '+' && c[k] != '-' && c[k] != '*' && c[k] != '/' && c[k] != '(' && c[k] != ')') {
                String num="";
                int count=k;
                while (c[count]!='+' && c[count]!='-' && c[count]!='/' && c[count]!='*'&& count<c.length-1)
                    count++;
                /**System.out.println(count);*/
                if(count==c.length-1) {
                    for (int s = k; s <= count; s++)
                        num += c[s];
                    s.push(num);
                    k = count;
                }
                else {
                    for (int s = k; s < count; s++)
                        num += c[s];
                    s.push(num);
                    k = count - 1;
                }
            }
           else if (c[k] == '(') {
                int tem2 = k;
                int st = ++tem2;
                while (c[tem2] != ')') {
                        ++tem2;
                    }
                String tems1 = "";
                for (int j = st; j < tem2; j++)
                        tems1 += c[j];
                pushe(tems1);
                k=tem2;

            }
            else if (c[k] == '*' || c[k] == '/') {
                    int tem = k + 1;
                    if (c[tem] == '(') {
                        int st = ++tem;
                        while (c[tem] != ')') {
                            ++tem;
                        }
                        String tems2 = "";
                        for (int j = st; j < tem; j++) {
                            tems2 += c[j];
                        }
                        pushe(tems2);
                        s.push(c[k]);
                        k=tem;
                    }
                    else {
                        int count1=k;
                        String tems7="";
                        while(c[count1]!='+' && c[count1]!='-' && c[count1]!='*' && c[count1]!='/' && count1<c.length-1)
                            count1++;
                        if(count1==c.length-1) {
                            for (int s = k+1; s <= count1; s++)
                                tems7 += c[s];
                            pushe(tems7);
                            s.push(c[k]);
                            k=count1;
                        }
                        else {
                            for (int s = k+1; s < count1; s++)
                                tems7 += c[s];
                            pushe(tems7);
                            s.push(c[k]);
                            k = count1-1;
                        }

                    }
                }
            else if (c[k] == '+' || c[k] == '-') {
                    int tem1 = k + 1;
                    if (c[tem1] == '(') {
                        int st = ++tem1;
                        while (c[tem1] != ')') {
                            ++tem1;
                        }
                        String tems3 = "";
                        for (int j = st; j < tem1; j++) {
                            tems3 += c[j];
                        }
                        pushe(tems3);
                        if (c[tem1 + 1] == '+' || c[tem1 + 1] == '-' || tem1 == c.length - 1) {
                            s.push(c[k]);
                            k=tem1;
                        }
                        else {
                            int st2 = tem1 + 1;
                            tem1 = tem1 + 2;
                            if (c[tem1] != '(') {
                                while (c[tem1] != '+' && c[tem1] != '-' && tem1 < c.length - 1) {
                                    ++tem1;
                                }
                                String tems4 = "";
                                if(tem1!=c.length-1) {
                                    for (int i = st2; i < tem1; i++)
                                        tems4 += c[i];
                                    pushe(tems4);
                                    s.push(c[k]);
                                    k=tem1-1;

                                }
                                else {
                                    for (int i = st2; i <= tem1; i++)
                                        tems4 += c[i];
                                    pushe(tems4);
                                    s.push(c[k]);
                                    k=tem1;
                                }
                            }
                            else {
                                while (!(c[tem1] == ')' && (c[tem1 + 1] == '+' || c[tem1 + 1] == '-' || tem1 == c.length - 1)))
                                    tem1++;

                                String tems5 = "";
                                if(tem1==c.length-1)
                                    for (int d = st2; d<=tem1; d++)
                                     tems5 += c[d];
                                else
                                    for (int d = st2; d<tem1; d++)
                                        tems5 += c[d];

                                pushe(tems5);
                                s.push(c[k]);
                                k=tem1;
                            }

                        }
                    }
                    else {
                        while (c[tem1] != '+' && c[tem1] != '-' && tem1<c.length-1) {
                            ++tem1;
                        }
                        String tems6 = "";
                        if(tem1==c.length-1) {
                            for (int a = k + 1; a <= tem1; a++)
                                tems6 += c[a];
                            pushe(tems6);
                            s.push(c[k]);
                            k = tem1;
                        }
                        else {
                            for (int a = k + 1; a < tem1; a++)
                                tems6 += c[a];
                            pushe(tems6);
                            s.push(c[k]);
                            k=tem1-1;
                        }
                    }
                }


            }
        }

        public void compute(Stack s1){
         if(s1.empty()==true)
         System.out.println(s1.pop());
         /**while (!s1.empty()){

         }

         }*/

    }
}

