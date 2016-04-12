package AISolver.Algorithm;

import game.PlayingField;
import game.Round;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import AISolver.Heuristic.SearchContext;

/**
 * Created by yujingxie on 11/19/15.
 */
public class AstarSearch  extends SearchTree {

        public AstarSearch(PlayingField pf, int level, SearchContext ctx){
            super(pf, level, ctx);
        }

        public class AstarTreeNode {
            public AstarTreeNode parent;
            //public double probability;
            public double score;
            public ArrayList<Round> cases;
            //The depth of the node in the tree
            public int depth = 0;

            public SearchContext context;

            public ArrayList<AstarTreeNode> child;

            public AstarTreeNode(AstarTreeNode _parent, PlayingField movedField, SearchContext _context, int _depth) {
                depth = _depth;
                parent = _parent;
                context = _context;
                cases = movedField.getAllNextPossiblePlayingFields();
                score = 0;
                for (Round r : cases) {
                    score += _context.getHeuristicValueUnderCache(r.getPf())*r.getProbability();
                }
                if (cases.size() == 0) {
                    score = Double.MAX_VALUE;
                }
            }

            public double pathCost(PlayingField pf){
                double cost=0;
               // cost+=10*pf.getNumberOccupiedTiles();
                int max=0;
                for(int i=0;i<4;i++)
                    for(int j=0;j<4;j++)
                        if(pf.valueAtPoint(i,j)>max) {
                            max = pf.valueAtPoint(i, j);
                        }
                cost+=0.01*max;
               int secondMax=0;
               for(int i=0;i<4;i++)
                    for(int j=0;j<4;j++)
                        if(pf.valueAtPoint(i,j)>secondMax && pf.valueAtPoint(i,j)<max) {
                            secondMax = pf.valueAtPoint(i, j);
                        }
                cost+=0.05*secondMax;

                int thirdMax=0;
                for(int i=0;i<4;i++)
                    for(int j=0;j<4;j++)
                        if(pf.valueAtPoint(i,j)>thirdMax && pf.valueAtPoint(i,j)<secondMax) {
                            thirdMax = pf.valueAtPoint(i, j);
                        }
                cost+=0.05*thirdMax;
                cost+=0.1*pf.sumPow()/((double)pf.getNumberOccupiedTiles());
              //  cost+=0.01*pf.costFunctionAvgScorePow()+pf.getNumberOccupiedTiles();
                //cost+=50*Math.abs(0);

                return cost;

            }

            public AstarTreeNode(AstarTreeNode _parent, ArrayList<Round> movedFields, SearchContext _context, int _depth) {
                depth = _depth;
                parent = _parent;
                context = _context;
                cases = new ArrayList<Round>();
                score = 0;
                if (movedFields.size() == 0) {
                    //Not moveable
                    score = Double.MAX_VALUE;
                }
                else for (Round rootRound: movedFields) {
                    ArrayList<Round> newCases = rootRound.getPf().getAllNextPossiblePlayingFields();
                    for (Round newRound : newCases) {
                        newRound.probability *= rootRound.probability;
                        score += (context.getHeuristicValueUnderCache(newRound.pf)+pathCost(newRound.pf))*newRound.probability;
                       // score += (context.getHeuristicValueUnderCache(newRound.pf)+depth)*newRound.probability;
                        cases.add(newRound);
                    }
                }

            }

            public void expand() {
                child = new ArrayList<AstarTreeNode>();
                for (int i = 0; i < 4; i++) {
                    ArrayList<Round> copyOfCases = new ArrayList<Round>();
                    int numberMovable = 0;
                    for (Round r : cases) {
                        if (r.pf.moveByInt(i) > 0) {
                            numberMovable++;
                            copyOfCases.add(r);
                        }
                    }
                    //Recabiliate the probability
                    if (numberMovable > 0) {
                        double rate = cases.size()/((double)numberMovable);
                        for (Round r: copyOfCases) {
                            r.setProbability(r.getProbability()*rate);
                        }
                    }

                    AstarTreeNode node = new AstarTreeNode(this, copyOfCases, context, depth+1);

                    child.add(node);

                }

                //After expansion, do a upward travel to update the score of all parents
                if (parent != null)
                    parent.refreshScore();
            }

            public void refreshScore() {
                score = Double.MAX_VALUE;
                for(int i = 0; i < 4; i++) {
                    AstarTreeNode node = child.get(i);
                    if (node.score < score)
                        score = node.score;
                }
                if (parent != null)
                    parent.refreshScore();
            }
        }

        SortedMap<Double, ArrayList<AstarTreeNode>> openingList = new TreeMap<Double, ArrayList<AstarTreeNode>>();
        Boolean readerLock = false;

        void pushOpeningList(AstarTreeNode currentRound, double score) {
            ArrayList<AstarTreeNode> list = openingList.get(new Double(score));
            if (list == null) {
                list = new ArrayList<AstarTreeNode>();
                list.add(currentRound);
            } else {
                list.add(currentRound);
            }
            openingList.put(new Double(score), list);
        }

        AstarTreeNode popOpeningList() {
            Double key = openingList.firstKey();
            ArrayList<AstarTreeNode> list = openingList.get(key);

            AstarTreeNode r = list.get(0);
            list.remove(0);

            if (list.isEmpty()) {
                openingList.remove(key);
            } else {
                openingList.put(key, list);
            }
            return r;
        }

        Boolean openingListIsEmpty() {
            return openingList.isEmpty();
        }

        public double calculateScore() {
            if (depth == 1) {
                //Calculate expected value of all insertion state
                double score = 0.0;
                ArrayList<Round> newFields = pf.getAllNextPossiblePlayingFields();
                for (Round r: newFields) {
                    score += r.getProbability() * context.getHeuristicValueUnderCache(r.getPf());
                }
                return score/newFields.size();
            } else {
                //Build a tree
                AstarTreeNode rootNode = new AstarTreeNode(null, pf, context, 0);
                pushOpeningList(rootNode, -1);
                AstarTreeNode workingNode;
                Boolean terminatedSearch = false;
                do {
                    workingNode = popOpeningList();
                    workingNode.expand();
                    ArrayList<AstarTreeNode> nodes = workingNode.child;
                    for (AstarTreeNode node : nodes) {
                        //The
                        if (node.depth >= depth) {
                            terminatedSearch = true;
                        }
                        if (node.score != Double.MAX_VALUE) {
                            pushOpeningList(node, node.score);
                        }
                    }
                } while(!openingListIsEmpty() && !terminatedSearch);
                return rootNode.score;
            }

        }
    }



