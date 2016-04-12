package AISolver.Heuristic;

import java.util.*;
import java.io.*;
import game.PlayingField;

public class MLPSearchContext extends SearchContext {
    
    public MLP network;
    
    public MLPSearchContext() throws IOException, FileNotFoundException, ClassNotFoundException {
        File file = new File("neural");
        FileInputStream f = new FileInputStream(file);
        ObjectInputStream s = new ObjectInputStream(f);
        network = (MLP) s.readObject();
        s.close();
    }
    
    public double[] inputMapping(PlayingField pf) {
        double[] result = new double[257];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                switch(pf.cells[i][j]) {
                    case 0:
                        result[(i*4+j)+1+0] = 1;
                        break;
                    case 2:
                        result[(i*4+j)+1+1] = 1;
                        break;
                    case 4:
                        result[(i*4+j)+1+2] = 1;
                        break;
                    case 8:
                        result[(i*4+j)+1+3] = 1;
                        break;
                        
                    case 16:
                        result[(i*4+j)+1+4] = 1;
                        break;
                    case 32:
                        result[(i*4+j)+1+5] = 1;
                        break;
                    case 64:
                        result[(i*4+j)+1+6] = 1;
                        break;
                    case 128:
                        result[(i*4+j)+1+7] = 1;
                        break;
                        
                    case 256:
                        result[(i*4+j)+1+8] = 1;
                        break;
                    case 512:
                        result[(i*4+j)+1+9] = 1;
                        break;
                    case 1024:
                        result[(i*4+j)+1+10] = 1;
                        break;
                    case 2048:
                        result[(i*4+j)+1+11] = 1;
                        break;
                        
                    case 4096:
                        result[(i*4+j)+1+12] = 1;
                        break;
                    case 8192:
                        result[(i*4+j)+1+13] = 1;
                        break;
                    case 16384:
                        result[(i*4+j)+1+14] = 1;
                        break;
                    case 32768:
                        result[(i*4+j)+1+15] = 1;
                        break;
                }
        return result;
    }
    
    public double heuristicValueForPF(PlayingField pf) {
        double[] input = inputMapping(pf);
        double[] output = network.passNet(input);
        return output[1];
    }
    
}