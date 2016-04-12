package AISolver.Heuristic;

import game.PlayingField;

public class OwnDesign1 extends SearchContext {
    
    void transpose(int [][]orig) {
        int temp;
        for (int i = 0; i < 3; i++) {
            for (int j = i+1; j < 4; j++) {
                temp = orig[i][j];
                orig[i][j]= orig[j][i];
                orig[j][i] = temp;
            }
        }
    }
    
    int mylog2(int input) {
        switch(input) {
            case 0:
                return 0;
            case 2:
                return 1;
            case 4:
                return 2;
            case 8:
                return 3;
                
            case 16:
                return 4;
            case 32:
                return 5;
            case 64:
                return 6;
            case 128:
                return 7;
                
            case 256:
                return 8;
            case 512:
                return 9;
            case 1024:
                return 10;
            case 2048:
                return 11;
                
            case 4096:
                return 12;
            case 8192:
                return 13;
            case 16384:
                return 14;
            case 32768:
                return 15;
        }
        return 0;
    }
    
    float heurscore_part(int [][]tile, PlayingField pf) {
        final float MONOTONICITY_POWER = 4.0f;
        final float MONOTONICITY_WEIGHT = 47.0f;
        final float MERGES_WEIGHT = 700.0f;
        final float ONE_DIR_PENALTY = 500000.0f;
        boolean zero_row1 = false, zero_row2 = false;
    	int full_row = 0;
        float total = 0;
        for (int i = 0; i < 4; i++) {
            int merges = 0;
            int prev = 0;
            int counter = 0;
            float monotonic_left = 0, monotonic_right = 0;
            boolean all_zero = true, has_zero = false;
            for (int j = 0; j < 4; j++) {
                if (tile[i][j] != 0) {
                	all_zero = false;
                    if (prev == tile[i][j]) {
                        counter++;
                    } else if (counter > 0) {
                        merges += 1 + counter;
                        counter = 0;
                    }
                }
                else
                	has_zero = true;
                prev = tile[i][j];
                if (j > 0) {
                    if (tile[i][j-1] > tile[i][j]) {
                        monotonic_left += Math.pow(mylog2(tile[i][j-1]), MONOTONICITY_POWER) - Math.pow(mylog2(tile[i][j]), MONOTONICITY_POWER);
                    } else {
                        monotonic_right += Math.pow(mylog2(tile[i][j]), MONOTONICITY_POWER) - Math.pow(mylog2(tile[i][j-1]), MONOTONICITY_POWER);
                    }
                }
            }
            if (counter > 0) 
            {
                merges += 1 + counter;
            }
            if (all_zero) 
            {
    			if (i == 0)
    				zero_row1 = true;
    			else if (i == 4-1)
    				zero_row2 = true;
    		}
    		if (merges == 0 && !has_zero) 
    			full_row++;
            total += MERGES_WEIGHT * merges - MONOTONICITY_WEIGHT * Math.min(monotonic_left, monotonic_right);
        }
        if (full_row == 3 && (zero_row1 || zero_row2)) 
        {
        	
    		if (zero_row1 && pf.makeCopy().moveByInt(3) == 0)
    			total -= ONE_DIR_PENALTY;
    		else if (zero_row2 && pf.makeCopy().moveByInt(2) == 0)
    			total -= ONE_DIR_PENALTY;
    	}
        return total;
    }
    
    public double heuristicValueForLowValPF(PlayingField pf) {
        
        int [][]tile = pf.getCopyOfCells();
        
        final float SUM_POWER = 3.5f;
        float SUM_WEIGHT = 11.0f;
        float EMPTY_WEIGHT = pf.getScore()/5;
        float SCORE_WEIGHT = 10.0f;
        
        float totalheur = 0;
        
        float corner_weight = 100000f;
        
        int corner = 0;
        
        int sum = 0;
        int empty = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sum += Math.pow(mylog2(tile[i][j]), SUM_POWER);
                if (tile[i][j] == 0) empty++;
                if (i == 0 || i == 3)
                    if (j == 0 || j == 3)
                        if (tile[i][j] == pf.MaxTile())
                            corner = 1;
            }
        }
        
        float monotonicWeight = 1;
        
        if (empty < 6) {
            int logMaxTile = mylog2(pf.MaxTile());
            int blockSupposeEmpty = (16-logMaxTile);
            if (empty < blockSupposeEmpty)
                EMPTY_WEIGHT = 10000000;
        }
        boolean heurPart = true;
        if (empty <= 3) {
            SCORE_WEIGHT = 2.5f;
            SUM_WEIGHT = 4.125f;
            monotonicWeight = 0.5f;
            corner_weight = 0;
        }
        
        totalheur += EMPTY_WEIGHT * empty + SUM_WEIGHT * sum// + corner_weight * corner
        //+ SCORE_WEIGHT * pf.getSumOfMerged()
        + SCORE_WEIGHT * pf.getScore()
        ;
        transpose(tile);
        if (heurPart) totalheur += monotonicWeight*heurscore_part(tile, pf);
        transpose(tile);
        if (heurPart) totalheur += monotonicWeight*heurscore_part(tile, pf);
        return totalheur;
        
    }
    
    public float decayWeightSummation_emptyCell(int emptyCellNum, float score_weight) {
        return (emptyCellNum*score_weight-40*score_weight*(float)Math.pow(2.718281828459045, emptyCellNum/-4))+40*score_weight;
        /*if (emptyCellNum <= 3)
            return score_weight*40*emptyCellNum;
        if (emptyCellNum <= 6)
            return score_weight*40*3+(score_weight-3)*20*emptyCellNum;
        else return score_weight*40*3+score_weight*20*3+score_weight*(emptyCellNum-6);*/
    }
    
    public float decayWeightSummation_score(int emptyCellNum, float score_weight) {
        return (emptyCellNum*score_weight-80*score_weight*(float)Math.pow(2.718281828459045, emptyCellNum/-10000))+40*score_weight;
        /*if (emptyCellNum <= 3)
         return score_weight*40*emptyCellNum;
         if (emptyCellNum <= 6)
         return score_weight*40*3+(score_weight-3)*20*emptyCellNum;
         else return score_weight*40*3+score_weight*20*3+score_weight*(emptyCellNum-6);*/
    }
    
    public float decayWeightSummation_sum(int emptyCellNum, float score_weight) {
        return (emptyCellNum*score_weight-80*score_weight*(float)Math.pow(2.718281828459045, emptyCellNum/-1000))+40*score_weight;
        /*if (emptyCellNum <= 3)
         return score_weight*40*emptyCellNum;
         if (emptyCellNum <= 6)
         return score_weight*40*3+(score_weight-3)*20*emptyCellNum;
         else return score_weight*40*3+score_weight*20*3+score_weight*(emptyCellNum-6);*/
    }
    
    public double heuristicValueForPF(PlayingField pf) {
        
        if (pf.MaxTile() <= 512) {
            //return heuristicValueForLowValPF(pf);
        }
        
        int [][]tile = pf.getCopyOfCells();
        
        float SUM_POWER = 3.5f;
        float SUM_WEIGHT = 11.0f;
        float EMPTY_WEIGHT = Math.max(270f, pf.getScore()/3);
        float SCORE_WEIGHT = 10.0f;
        
        float totalheur = 0;
        int sum = 0;
        int empty = 0;
        int islandMax = 0;
        int max = pf.MaxTile();
        boolean maxAtCorner = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sum += Math.pow(mylog2(tile[i][j]), SUM_POWER);
                if (tile[i][j] == 0) empty++;
                if (tile[i][j] == max) {
                    maxAtCorner |= ((i==0)||(i==3))&&((j==0)||(j==3));
                    islandMax += 2;
                    if (i < 3 && tile[i+1][j]==max) islandMax--;
                    if (i > 0 && tile[i-1][j]==max) islandMax--;
                    if (j < 3 && tile[i][j+1]==max) islandMax--;
                    if (j > 0 && tile[i][j-1]==max) islandMax--;
                }
            }
        }
        islandMax /= 2;
        
        
        totalheur += decayWeightSummation_emptyCell(empty, EMPTY_WEIGHT);//EMPTY_WEIGHT * empty
        totalheur += decayWeightSummation_score(sum, SUM_WEIGHT);
        totalheur += decayWeightSummation_score(pf.getScore(), SCORE_WEIGHT);
        if (islandMax < 2) totalheur += pf.MaxTile()*20;
        transpose(tile);
        totalheur += heurscore_part(tile, pf);
        transpose(tile);
        totalheur += heurscore_part(tile, pf);
        return totalheur;
        
	}
    
}