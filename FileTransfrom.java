import java.io.*;

/**
 * Created by yujingxie on 12/5/15.
 */
public class FileTransfrom {
    public static void main(String [] args) throws IOException{
        String [] target=new String[28170];
        int count=0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/yujingxie/Desktop/Target.csv"));
            reader.readLine();
            String line = "";
            while((line=reader.readLine())!=null){
                String item[] = line.split("，");

                target[count] = item[item.length-1];
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int [][] newtarget=new int[28170][6];

        for(int i=0;i<target.length;i++){
            if(target[i].equals("stand")){
                for(int j=0;j<5;j++)
                    newtarget[i][j]=0;
                newtarget[i][5]=1;
            }
            else if(target[i].equals("sit")){
                for(int j=0;j<4;j++)
                    newtarget[i][j]=0;
                newtarget[i][4]=1;
                newtarget[i][5]=0;
            }
            else if(target[i].equals("walk")){
                for(int j=0;j<6;j++){
                    if(j==3) newtarget[i][j]=1;
                    else newtarget[i][j]=0;
                }
            }
            else if(target[i].equals("bike")){
                for(int j=0;j<6;j++){
                    if(j==2) newtarget[i][j]=1;
                    else newtarget[i][j]=0;
                }
            }
            else if(target[i].equals("stairsup")){
                for(int j=0;j<6;j++){
                    if(j==1) newtarget[i][j]=1;
                    else newtarget[i][j]=0;
                }
            }
            else if(target[i].equals("stairsdown")){
                for(int j=0;j<6;j++){
                    if(j==0) newtarget[i][j]=1;
                    else newtarget[i][j]=0;
                }
            }
        }

        //write to CSV

        BufferedWriter bw= new BufferedWriter(new FileWriter("/Users/yujingxie/Desktop/T.txt"));

        for(int i=0;i<newtarget.length;i++) {
            for (int j = 0; j < newtarget[0].length; j++)
                bw.write(newtarget[i][j]+"\t");
            bw.write("\n");
        }
        bw.close();
    }
}

