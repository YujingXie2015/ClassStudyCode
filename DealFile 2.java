import java.io.*;
import java.util.Random;

/**
 * Created by yujingxie on 12/7/15.
 */
public class DealFile {

    public static void main(String [] args) throws IOException{
        /*double num=28170*0.7;
        int numtrain=(int)num;
        double num1=28170*0.3;
        int numtest=(int)num1;*/

        // read the train file

        String [] data=new String[3540962];
        int countrow=0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/yujingxie/Desktop/Watch_Accelerometer.csv"));
            reader.readLine();
            String line = "";
            while((line=reader.readLine())!=null){
                String item[] = line.split("，");
                data[countrow] = item[item.length - 1];
                countrow++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String [] splitstring;
        double [][] traindata=new double[118235][3];
        String [] target=new String[118235];
        int count=0;
        int counttarget=0;
        for(int i=0;i<data.length;i++) {
            splitstring=data[i].split(",");
            if(!splitstring[10].equals("stand") && !splitstring[10].equals("sit") && !splitstring[10].equals("walk") && !splitstring[10].equals("stairsup") && !splitstring[10].equals("stairsdown") && !splitstring[10].equals("bike")) continue;
            else {
                for (int j = 0; j < splitstring.length; j++) {
                    if (j == 4)
                        traindata[count][0] = Double.parseDouble(splitstring[j]);
                    else if (j == 5)
                        traindata[count][1] = Double.parseDouble(splitstring[j]);
                    else if (j == 6)
                        traindata[count][2] = Double.parseDouble(splitstring[j]);
                    else if (j == 10)
                        target[count] = splitstring[j];
                }
                count++;
            }
        }


        // transform the target
        int [][] newtarget=new int[118235][5];

        for(int i=0;i<target.length;i++){
            if(target[i].equals("stand")){
                for(int j=0;j<5;j++)
                    newtarget[i][j]=0;
                newtarget[i][4]=1;
            }
            else if(target[i].equals("sit")){
                for(int j=0;j<3;j++)
                    newtarget[i][j]=0;
                newtarget[i][3]=1;
                newtarget[i][4]=0;
            }
            else if(target[i].equals("walk")){
                for(int j=0;j<5;j++){
                    if(j==2) newtarget[i][j]=1;
                    else newtarget[i][j]=0;
                }
            }
            else if(target[i].equals("bike")){
                for(int j=0;j<5;j++){
                    if(j==1) newtarget[i][j]=1;
                    else newtarget[i][j]=0;
                }
            }
            else if(target[i].equals("stairsup")|| target[i].equals("stairsdown")){
                for(int j=0;j<5;j++){
                    if(j==0) newtarget[i][j]=1;
                    else newtarget[i][j]=0;
                }
            }
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/yujingxie/Desktop/Ttrain1s.txt"));
        BufferedWriter bw1 = new BufferedWriter(new FileWriter("/Users/yujingxie/Desktop/TrainTarget1s.txt"));

        for (int i = 0; i < traindata.length; i++) {
            for (int j = 0; j < traindata[0].length; j++)
                bw.write(traindata[i][j] + "\t");
            bw.write("\n");
        }
        bw.close();

        for (int i = 0; i < traindata.length; i++) {
            for (int j = 0; j <newtarget[0].length; j++)
                bw1.write(newtarget[i][j] + "\t");
            bw1.write("\n");
        }
        bw1.close();


        // distribution the train data and test data

        double num = 118235 * 0.7;
        int numtrain = (int) num;
        int numtest = 118235-numtrain;
       // System.out.print("execution here4");

        int[] trainarry = new int[numtrain];
        int[] testarray = new int[numtest];

        Random random = new Random();
        for (int i = 0; i < trainarry.length; i++) {
            while (true) {
                int next = random.nextInt();
                if (next >= 118235 || next < 0) continue;
                else {
                    int j;
                    for (j = 0; j < i; j++)
                        if (trainarry[j] == next)
                            break;
                    if (j == i) {
                        trainarry[i] = next;
                        break;
                    } else continue;
                }
            }
        }

        //System.out.print("execution here1");

        int countnum = 0;
        for (int i = 0; i < traindata.length; i++) {
            int j;
            for (j = 0; j < trainarry.length; j++)
                if (i == trainarry[j]) break;
            if (j == testarray.length) {
                testarray[countnum] = i;
                countnum++;
            } else continue;
        }
       // System.out.print("execution here2");

        double[][] train = new double[numtrain][3];
        int[][] traintarget = new int[numtrain][5];

        for (int i = 0; i < trainarry.length; i++) {
            for (int j = 0; j < 3; j++) {
                train[i][j] = traindata[trainarry[i]][j];
            }
            for (int j = 0; j < 5; j++) {
                traintarget[i][j] = newtarget[trainarry[i]][j];
            }

        }
       // System.out.print("execution here3");

        double[][] testd = new double[numtest][3];
        int[][] testtarget = new int[numtest][5];

        for (int i = 0; i < testarray.length; i++) {
            for (int j = 0; j < 3; j++) {
                testd[i][j] = traindata[testarray[i]][j];
            }
            for (int j = 0; j < 5; j++) {
                testtarget[i][j] = newtarget[testarray[i]][j];
            }
        }
       // System.out.print("execution here");

        //write to files
        BufferedWriter bw4 = new BufferedWriter(new FileWriter("/Users/yujingxie/Desktop/TtrainWs.txt"));
        BufferedWriter bw5 = new BufferedWriter(new FileWriter("/Users/yujingxie/Desktop/TrainTargetWs.txt"));

        for (int i = 0; i < train.length; i++) {
            for (int j = 0; j < train[0].length; j++)
                bw4.write(train[i][j] + "\t");
            bw4.write("\n");
        }
        bw4.close();

        for (int i = 0; i < train.length; i++) {
            for (int j = 0; j < traintarget[0].length; j++)
                bw5.write(traintarget[i][j] + "\t");
            bw5.write("\n");
        }
        bw5.close();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter("/Users/yujingxie/Desktop/TestWs.txt"));
        BufferedWriter bw3 = new BufferedWriter(new FileWriter("/Users/yujingxie/Desktop/TestTargetWs.txt"));

        for (int i = 0; i < testd.length; i++) {
            for (int j = 0; j < testd[0].length; j++)
                bw2.write(testd[i][j] + "\t");
            bw2.write("\n");
        }
        bw2.close();

        for (int i = 0; i < testtarget.length; i++) {
            for (int j = 0; j < testtarget[0].length; j++)
                bw3.write(testtarget[i][j] + "\t");
            bw3.write("\n");
        }
        bw3.close();
    }
}
