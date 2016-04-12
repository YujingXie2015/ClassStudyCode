/**
 * Created by yujingxie on 9/23/15.
 */
public class SpellCheckClass {
    String[] dictionary;
    String[] similarw;
    int count;
    public SpellCheckClass(String[] input) {
        dictionary = new String[input.length];
        dictionary = input;
        similarw=new  String[input.length];
        count=0;
    }

    public void checkspell(String checkword) {
        int i;
        for ( i = 0; i < dictionary.length; i++) {
            if (checkword.equals(dictionary[i])) {
                    break;
                }
            else if ((checkword.length()==dictionary[i].length()+1 )|| (checkword.length()==dictionary[i].length()) || (checkword.length()==dictionary[i].length()-1))
            {
              if(checkword.length()==dictionary[i].length()+1){
                  if(checkword.contains(dictionary[i])){
                      count++;
                      similarw[count-1]=dictionary[i];
                  }
                  else {
                      int s;
                      for (s = 1; s < checkword.length()-1; s++) {
                          if ((checkword.substring(0, s).equals(dictionary[i].substring(0, s))) && (checkword.substring(s + 1, checkword.length()).equals(dictionary[i].substring(s, dictionary[i].length())))) {
                              count++;
                              similarw[count - 1] = dictionary[i];
                           /** System.out.println(s + "this is s " + i + " " + dictionary[i] + " " + "this is for situation 1");*/
                              break;
                          }
                      }
                  }
              }
              else if(checkword.length()==dictionary[i].length()) {
                  int d;
                  if (!(checkword.substring(0, 1).equals(dictionary[i].substring(0, 1))) && (checkword.substring(1, checkword.length()).equals(dictionary[i].substring(1, dictionary[i].length())))) {
                      count++;
                      similarw[count - 1] = dictionary[i];

                  } else if ((checkword.substring(0, checkword.length() - 1).equals(dictionary[i].substring(0, dictionary[i].length() - 1))) && !(checkword.substring(checkword.length() - 1, checkword.length()).equals(dictionary[i].substring(dictionary[i].length() - 1, dictionary[i].length())))) {
                      count++;
                      similarw[count - 1] = dictionary[i];
                  } else {
                      for (d = 1; d < checkword.length(); d++) {
                          if ((checkword.substring(0, d).equals(dictionary[i].substring(0, d))) && (checkword.substring(d + 1, checkword.length()).equals(dictionary[i].substring(d + 1, dictionary[i].length()))))
                          {
                              count++;
                              similarw[count - 1] = dictionary[i];
                              /**System.out.println(d+"this is d "+i+" "+dictionary[i]+" "+"this is for situation 2");*/
                              break;
                          }
                      }

                  }
              }
              else if(checkword.length()==dictionary[i].length()-1){
                  if(dictionary[i].contains(checkword)){
                      count++;
                      similarw[count-1]=dictionary[i];
                  }
                  else {
                      int b;
                      for (b = 1; b < checkword.length(); b++) {
                          if ((checkword.substring(0, b).equals(dictionary[i].substring(0, b))) && (checkword.substring(b, checkword.length()).equals(dictionary[i].substring(b + 1, dictionary[i].length())))) {
                              count++;
                              similarw[count - 1] = dictionary[i];
                              /** System.out.println(b+"this is b "+i+" "+dictionary[i]+" "+"this is for situation 3");*/
                              break;
                          }
                      }
                  }

              }
            }
            else continue;
        }
        if(i<dictionary.length)
            System.out.println(checkword + ":" +""+"is correct");
        else if(i==dictionary.length && count!=0){
            System.out.print(checkword+":");
            for(int j=0;j<count;j++)
                System.out.print(similarw[j]+" ");
                System.out.print("\n");
        }

        else
             System.out.println(checkword+":");

    }

}

