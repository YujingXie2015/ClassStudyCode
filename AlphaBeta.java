package AISolver.Algorithm;

import game.PlayingField;
import game.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import AISolver.Heuristic.SearchContext;


public class AlphaBeta extends SearchTree 
{

	public AlphaBeta(PlayingField pf, int depth, SearchContext context) 
	{
		super(pf, depth, context);
	}
	
	/* Enumerator helper for alpha-beta pruning algorithm
	 * Player vs. Computer
	 */
	public enum Player 
	{
        COMPUTER, // Computer
        PLAYER // Player
    }

	private Map<String, Object> alphabeta(PlayingField pf, int depth, double alpha, double beta, Player player)
	{
        Map<String, Object> result = new HashMap<String, Object>();
        
        String bestDirection = new String();
        double bestScore;
        

        if(pf.isGameWon()||pf.isGameLost())
        {
            bestScore = context.getHeuristicValueUnderCache(pf); //highest possible score
        }
        else if(this.depth==0 || (this.depth)*2+1 == depth)
        {
            bestScore = context.getHeuristicValueUnderCache(pf);
        }
        else 
        {
            if(player == Player.PLAYER) 
            {
            	String[] directions = {"up", "down", "left", "right"};
            	ArrayList<String> legalDirection = new ArrayList<String>();
            	ArrayList<PlayingField> legalPF = new ArrayList<PlayingField>();
            	
                for(String direction : directions) 
                {
                    PlayingField newPF = pf.makeCopy();

                    int points = newPF.moveByString(direction);
                    
//                    System.out.println("moves " + points);
                    
                    if(points > 0)// || !isEqualPlayingField(pf.getCopyOfCells(), newPF.getCopyOfCells()))
                    {	
                    	legalDirection.add(direction);
                    	legalPF.add(newPF);
                    }
                    else
                    {
                    	continue;
                    }
                }
                
//                System.out.println("In max # legal moves" + numLegalMove);
                
                int index = 0;
                for(String direction : legalDirection)
                {
                	PlayingField newPF = legalPF.get(index);
                    
                    context.pushNewScore(newPF.getSumOfMerged());
                    
                	index++;
                	
                    Map<String, Object> currentResult = alphabeta(newPF, depth + 1, alpha, beta, Player.COMPUTER);
                    double currentScore = ((Double)currentResult.get("Score")).doubleValue();
                    
                    //                    System.out.println("Current score: " + currentScore + " in Max");
                    context.pushNewScore(newPF.getSumOfMerged());
                                        
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
            	
            	ArrayList<PlayingField> allPFs = pf.getAllNextPossiblePlayingFieldsPF();
                
                for(PlayingField newPF : allPFs)
                {
                    Map<String, Object> currentResult = alphabeta(newPF, depth + 1, alpha, beta, Player.PLAYER);
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
    	Map<String, Object> result = alphabeta(pf, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.COMPUTER);
        return (Double)result.get("Score");
    }
	
}
