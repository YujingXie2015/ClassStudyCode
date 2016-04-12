package AISolver;

import game.PlayingField;
import game.Round;
import game.Tile;

import AISolver.SearchContext;
import AISolver.SearchTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MinMax extends SearchTree
{

	public MinMax(PlayingField pf, int depth, SearchContext context)
	{
		super(pf, depth, context);
	}
	
	/* Enumerator helper for alpha-beta pruning algorithm
	 * Player vs. Computer
	 */
	
    float minimax_min(PlayingField pf, int score, int dir, int depth) {
        float minheur = Integer.MAX_VALUE;
        int[][] movedtile = duplicateTile(tile);
        if (move(movedtile, dir, score, false)) {
            for (int i = 0; i < L; i++) {
                for (int j = 0; j < L; j++) {
                    if (movedtile[i][j] == 0) {
                        int** add2tile = duplicateTile(movedtile);
                        add2tile[i][j] = 2;
                        int heur = minimax_max(add2tile, score, dir, depth-1);
                        if (heur < minheur) minheur = heur;
                        int** add4tile = duplicateTile(movedtile);
                        add4tile[i][j] = 4;
                        heur = minimax_max(add4tile, score, dir, depth-1);
                        if (heur < minheur) minheur = heur;
                        deleteTile(add2tile);
                        deleteTile(add4tile);
                    }
                }
            }
        } else {
            minheur = INT_MIN;
            if (DEBUG) {
                displaySimple(movedtile);
                cout << "dir " << dir << " has no move\n";
            }
        }
        deleteTile(movedtile);
        return minheur;
    }
    
    float minimax_max(int** &tile, int score, int &dir, int depth) {
        float heurmax = Integer.MIN_VALUE;
        float heur = Integer.MIN_VALUE;
        dir = 0;
        if (depth == 0) return heurscore(tile, score);
        heur = minimax_min(tile, score, 72, depth);
        if (heur > heurmax) {
            heurmax = heur;
            dir = 72;
        }
        heur = minimax_min(tile, score, 75, depth);
        if (heur > heurmax) {
            heurmax = heur;
            dir = 75;
        }
        heur = minimax_min(tile, score, 77, depth);
        if (heur > heurmax) {
            heurmax = heur;
            dir = 77;
        }
        heur = minimax_min(tile, score, 80, depth);
        if (heur > heurmax) {
            heurmax = heur;
            dir = 80;
        }
        if (dir == 0) {
            //		DEBUG = true;
            return heurscore(tile, score);
        } //else DEBUG = false;
        return heurmax;
    }
    
	private Map<String, Object> alphabeta(Round round, int depth, double alpha, double beta, Player player)
	{
        Map<String, Object> result = new HashMap<String, Object>();
        
        String bestDirection = new String();
        double bestScore;
        PlayingField pf = round.getPf();
        

        if(pf.isGameWon()) 
        {
            bestScore = Integer.MAX_VALUE; //highest possible score
        }
        else if(pf.isGameLost()) 
        {
            bestScore = context.getHeuristicValueUnderCache(pf); //lowest possible score
        }
        else if ((this.depth==0 || (this.depth) == depth/2)&&(player == Player.PLAYER))
        {
            bestScore = context.getHeuristicValueUnderCache(pf);
        }
        else 
        {
            if(player == Player.PLAYER) 
            {
            	String[] directions = {"up", "down", "left", "right"};
            	ArrayList<String> legalDirection = new ArrayList<String>();
            	int numLegalMove = 0;
            	ArrayList<Round> legalRound = new ArrayList<Round>();
                for(String direction : directions) 
                {
                	Round newRound = new Round(pf, 1, direction);
                	PlayingField newPF = pf.makeCopy();

                    int points = newPF.moveByString(direction);
                    
//                    System.out.println("moves " + points);
                    
                    if(points > 0 || !isEqualPlayingField(pf.getCopyOfCells(), newPF.getCopyOfCells()))
                    {	
                    	numLegalMove++;
                    	legalDirection.add(direction);
                    	legalRound.add(newRound);
                    }
                    else
                    {
                    	continue;
                    }
                }
                
//                System.out.println("In max # legal moves" + numLegalMove);
                
                double rate = directions.length/(double) numLegalMove;
                
                int index = 0;
                for(String direction : legalDirection)
                {
                	Round newRound = legalRound.get(index);
                	newRound.setProbability(newRound.getProbability()*rate);
                	index++;
                	
                    Map<String, Object> currentResult = alphabeta(newRound, depth + 1, alpha, beta, Player.COMPUTER);
                    double currentScore = ((Double)currentResult.get("Score")).doubleValue();
                    
//                    System.out.println("Current score: " + currentScore + " in Max");
                                        
                    if(currentScore > alpha) 
                    { 
                    	//maximize score
                        alpha = currentScore;
                        bestDirection = direction;
                    }
                    
                    if(beta <= alpha) 
                    {
                        break; //beta cutoff
                    }
                }
                
                bestScore = alpha;
//                System.out.println("Max");
            }
            else 
            {
            	ArrayList<Tile> freeTiles = pf.getFreeCells();
            	int numTreeTiles = freeTiles.size();
            	
                int[] possibleValues = {2, 4};
                ArrayList<Round> allRounds = new ArrayList<Round>(14*2);
                double probability = 0;
                
                for(Tile tile : freeTiles) 
                {
                    for(int value : possibleValues) 
                    {
                        PlayingField newPF = pf.makeCopy();
                        newPF.setValue(tile.getX(), tile.getY(), value);
                        if(value == 2)
                        	probability = 0.9;
                        else if(value == 4)
                        	probability = 0.1;
                        allRounds.add(new Round(newPF, 1, "" + value + "("+tile.getX()+","+tile.getY()+")")); //set 2 with probability of 0.9
                    }
                }
                
                for(Round newRound : allRounds) 
                {
                    Map<String, Object> currentResult = alphabeta(newRound, depth + 1, alpha, beta, Player.PLAYER);
                    double currentScore = ((Double)currentResult.get("Score")).doubleValue();// * newRound.getProbability();
                    
                    if(currentScore < beta) 
                    { //minimize best score
                        beta = currentScore;
                    }
                    
                    if(beta <= alpha) {
                        break; //alpha cutoff
                    }
                }
                
                bestScore = beta;
                
                if(freeTiles.isEmpty()) 
                {
                    bestScore = 0;
                }
//                System.out.println("Min");
            }
        }
        
        result.put("Score", bestScore);
        result.put("Direction", bestDirection);
        
        return result;
    }
	
    private boolean isEqualPlayingField(int[][] currentPF, int[][] newPF) 
    {

    	boolean equal = true;
        
        for(int i=0;i<currentPF.length;i++) 
        {
            for(int j=0;j<currentPF.length;j++) 
            {
                if(currentPF[i][j]!= newPF[i][j]) 
                {
                    equal = false; //The two boards are not same.
                    return equal;
                }
            }
        }
        
        return equal;
    }
    
    public double calculateScore()
    {
    	Round currentRound = new Round(pf, 1, "*");
    	Map<String, Object> result = alphabeta(currentRound, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.COMPUTER);
        return (Double)result.get("Score");
    }
	
}
