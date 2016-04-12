package AISolver.Heuristic;

import game.PlayingField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yujingxie on 11/30/15.
 */
public class OwnDesign2 extends SearchContext {
    public double heuristicValueForPF(PlayingField pf) {
        int heuristicValue=1000;
        for(int i=0;i<4;i++)
            for(int j=0;j<3;j++) {
                if (pf.valueAtPoint(i, j) >= pf.valueAtPoint(i, j + 1))
                    heuristicValue -= 100;
                else heuristicValue-=10;
            }
        for(int i=0;i<3;i++)
            for (int j=0;j<4;j++) {
                if (pf.valueAtPoint(i, j) >= pf.valueAtPoint(i + 1, j))
                    heuristicValue -= 100;
                else  heuristicValue-=10;
            }
        for(int i=0;i<4;i++) {
            if (pf.valueAtPoint(i, 0) >= pf.valueAtPoint(i, 1) && pf.valueAtPoint(i, 1) >= pf.valueAtPoint(i, 2) && pf.valueAtPoint(i, 2) >= pf.valueAtPoint(i, 3))
                heuristicValue -= 50;
           // else heuristicValue-=10;
        }
        for(int j=0;j<4;j++) {
            if (pf.valueAtPoint(0, j) >= pf.valueAtPoint(1, j) && pf.valueAtPoint(1, j) >= pf.valueAtPoint(2, j) && pf.valueAtPoint(2, j) >= pf.valueAtPoint(3, j))
                heuristicValue -= 50;
          //  else  heuristicValue-=10;
        }
        int max=0;
        int r=0;
        int c=0;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                if(pf.valueAtPoint(i,j)>max) {
                    max = pf.valueAtPoint(i, j);
                    r=i;
                    c=j;
                }
        heuristicValue-=max*0.1;
        if(r<1 && c<1)
            heuristicValue -= 100;
        else heuristicValue-=10;

        int secondMax=0;
        int sr=0;
        int sc=0;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                if(pf.valueAtPoint(i,j)>secondMax && pf.valueAtPoint(i,j)<max) {
                    secondMax = pf.valueAtPoint(i, j);
                    sr=i;
                    sc=j;
                }
        if(sr==0 && sc==1)
            heuristicValue-=50;
        else heuristicValue-=10;

        int thirdMax=0;
        int tr=0;
        int tc=0;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                if(pf.valueAtPoint(i,j)>thirdMax && pf.valueAtPoint(i,j)<secondMax) {
                    thirdMax = pf.valueAtPoint(i, j);
                    tr=i;
                    tc=j;
                }
        if(tr==0 && tc==2)
            heuristicValue-=50;
        else heuristicValue-=10;


        for(int i=1;i<3;i++)
            for(int j=1;j<3;j++) {
                if (pf.valueAtPoint(i, j) == pf.valueAtPoint(i, j - 1) || pf.valueAtPoint(i, j) == pf.valueAtPoint(i, j + 1) || pf.valueAtPoint(i, j) == pf.valueAtPoint(i - 1, j) || pf.valueAtPoint(i, j) == pf.valueAtPoint(i + 1, j))
                    heuristicValue -= 30;
                else heuristicValue-=10;
            }
        if(pf.movesAvailable())
            heuristicValue-=100;
        else heuristicValue-=20;

<<<<<<< HEAD:2048Solver/src/AISolver/OwnDesign2.java
       heuristicValue-=(20*pf.getNumberOfFreeCells()/**+40*pf.getNumberOccupiedTiles()/**+20*(pf.movesAvailable()?1:0)*/);
=======
        heuristicValue-=(20*pf.getNumberOfFreeCells()/*+40*pf.getNumberOccupiedTiles()/**+20*(pf.movesAvailable()?1:0)*/);
>>>>>>> 3e00448886634762195876e2c5b9f3d88d5d2506:2048Solver/src/AISolver/Heuristic/OwnDesign2.java
        return heuristicValue;
    }

    ConcurrentHashMap<PlayingField, Double> scores = new ConcurrentHashMap<PlayingField, Double>();


    public void save() throws IOException {
        {
            File file = new File("temp");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.flush();
            s.writeObject(scores);
            s.close();
        }
    }

}
