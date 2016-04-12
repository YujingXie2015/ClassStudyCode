package AISolver.Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import AISolver.Heuristic.SearchContext;
import game.*;

public class ExpectMax extends SearchTree {

	public ExpectMax(PlayingField pf, int depth, SearchContext ctx) {
		super(pf, depth, ctx);
		// TODO Auto-generated constructor stub
	}
	
	/* Enumerator helper for alpha-beta pruning algorithm
	 * Player vs. Computer
	 */
	public enum Player 
	{
        COMPUTER, // Computer
        PLAYER // Player
    }
	
	private Map<String, Object> expectMax(Round r, int depth, Player player)
	{
        PlayingField pf = r.getPf();
		Map<String, Object> result = new HashMap<String, Object>();
        
        String bestDirection = new String();
        double bestScore;
        
        if(pf.isGameWon() || pf.isGameLost()) 
        {
            bestScore = context.getHeuristicValueUnderCache(pf); //highest possible score
        }
        else if((this.depth)*2 == depth)// || r.getProbability() < 0.0001)
        {
            bestScore = context.getHeuristicValueUnderCache(pf);
        }
        else 
        {
            //Max
            if(player == Player.PLAYER) 
            {
            	bestScore = Integer.MIN_VALUE;
            	String[] directions = {"up", "down", "left", "right"};
            	ArrayList<String> legalDirection = new ArrayList<String>();
            	ArrayList<PlayingField> legalPF = new ArrayList<PlayingField>();
            	
                for(String direction : directions) 
                {
                    PlayingField newPF = pf.makeCopy();

                    int points = newPF.moveByString(direction);
                    
//                    System.out.println("moves " + points);
                    
                    if(points > 0)
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
                	
                    Round newRound = new Round(newPF, r.probability, " ");
                    
                    Map<String, Object> currentResult = expectMax(newRound, depth + 1, Player.COMPUTER);
                    double currentScore = ((Double)currentResult.get("Score")).doubleValue();
                   
//                    System.out.println("Current score: " + currentScore + " in Max");
                    context.pushNewScore(newPF.getSumOfMerged());
                                        
                    if(currentScore > bestScore) 
                    { 
                    	//maximize score
                    	bestScore = currentScore;
                        bestDirection = direction;
                    }
                }
//                System.out.println("Max");
            }
            else
                //Expect
            {
            	bestScore = 0;
            	ArrayList<Round> allPFs = pf.getAllNextPossiblePlayingFields();
                
                for(Round newRound : allPFs)
                {
                    newRound.probability *= r.probability;
                    Map<String, Object> currentResult = expectMax(newRound, depth + 1, Player.PLAYER);
                    bestScore += ((Double)currentResult.get("Score")).doubleValue()*newRound.getProbability();
                    
                }
//                System.out.println("Min");
            }
        }
        
        result.put("Score", bestScore);
        result.put("Direction", bestDirection);
        
        return result;
    }
	
    public double calculateScore()
    {
        Round r = new Round(pf, 1, " ");
    	Map<String, Object> result = expectMax(r, 1, Player.COMPUTER);
        return (Double)result.get("Score");
    }
}
