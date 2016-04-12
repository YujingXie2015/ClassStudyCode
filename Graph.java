import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by yujingxie on 1/23/16.
 */
public class Graph {
    int [][] adjMatrix;

    Vertice [] ver;
    Edge [] fromEdge;
    Edge [] toEdge;

    Boolean [] mark;
    int [] in;
    int [] out;

    int [] output;
    int count;
    Vertice [] outV;





    public Graph(int [][] matrix){
        adjMatrix=matrix;
        output=new int[adjMatrix.length];
        count=0;
        for(int i=0;i<adjMatrix.length;i++)
            mark[i]=false;
        in=new int[adjMatrix.length];
        out=new  int[adjMatrix.length];
        for(int i=0;i<adjMatrix.length;i++)
            for (int j=0;j<adjMatrix[0].length;j++){
                if(adjMatrix[i][j]!=0)
                    out[i]++;
                if(adjMatrix[j][i]!=0)
                    in[i]++;
            }

    }

    public Graph(Vertice [] v,Edge [] e){
        ver=v;
        fromEdge=e;

        for(int i=0;i<ver.length;i++)
            mark[ver[i].getNum()]=false;
        in=new int[ver.length];
        out=new int[ver.length];

        for(int i=0;i<ver.length;i++) {
            for (Edge j = ver[i].getFirstEdge(); j != null; j = j.getNextEdge())
                out[i]++;
            for(Edge j=ver[i].getReverseFirst();j!=null;j=j.getNextReverse())
                in[i]++;
        }
    }

    public void  deepFirstSearchByMatrix(Graph g, int i){

        if(g.mark[i]==false){
            output[count++]=i;
            for(int j=0;j<g.adjMatrix[i].length;j++)
                if(g.adjMatrix[i][j]!=0 && g.mark[j]==false)
                    deepFirstSearchByMatrix(g,j);
        }

        for(int j=0;j<mark.length;j++)
            if(mark[j]==false)
                deepFirstSearchByMatrix(g,j);


    }

    public void  deepFirstSearchByTable(Graph g,Vertice v){

        if(g.mark[v.getNum()]==false) {
            outV[count++]=v;
            mark[v.getNum()]=true;
            for (Edge e = v.getFirstEdge(); e != null; e = e.getNextEdge()) {
                if(mark[e.getTo().getNum()]==false)
                    deepFirstSearchByTable(g,e.getTo());
            }
        }
        for(int i=0;i<ver.length;i++)
            if(mark[ver[i].getNum()]==false)
                deepFirstSearchByTable(g,ver[i]);

    }

    public void breadFirstSearchByMatrix(){}

    public  void  breadFirstSearchByTable(Graph g,Vertice v){
        Queue<Vertice> queue=new LinkedList<Vertice>();
        queue.add(v);
        while (!queue.isEmpty()){
            Vertice vertice= queue.remove();
            outV[count++]=vertice;
            mark[vertice.getNum()]=true;
            for(Edge e=vertice.getFirstEdge();e!=null;e=e.getNextEdge())
                if(mark[e.getTo().getNum()]==false)
                queue.add(e.getTo());

        }

        for(int i=0;i<ver.length;i++)
            if(mark[ver[i].getNum()]==false)
                breadFirstSearchByTable(g,ver[i]);

    }

    public  boolean testVerticeConnectivity(Vertice f, Vertice t, Graph g){
        boolean isConnective=false;
        Queue<Vertice> q=new LinkedList<Vertice>();
        q.add(f);
        while (!q.isEmpty()){
            Vertice vert=q.remove();
            for(Edge e=f.getFirstEdge();e!=null;e=e.getNextEdge()) {
                if (e.equals(t) == true)
                    isConnective=true;
                else if(mark[e.getTo().getNum()]==false)
                    q.add(e.getTo());
            }
        }
        return isConnective;
    }

    public  boolean testGraphConnectivity(Graph g){
        boolean isConnectivity=false;
        for(int i=0;i<ver.length;i++)
            for (int j=0;j<ver.length;j++){
                if(j==i) continue;
                else {
                   isConnectivity=testVerticeConnectivity(ver[i],ver[j],g);
                    if(isConnectivity==false) return false;
                }
            }
        isConnectivity=true;
        return  isConnectivity;


    }

    public int [] topologySortByMatrix(Graph g){
        int [] topology=new int[g.adjMatrix.length];
        Queue<Integer> queue=new LinkedList<Integer>();
        int count=0;
        for(int i=0;i<g.adjMatrix.length;i++)
            if(in[i]==0){
                queue.add(i);
            }
        while (!queue.isEmpty()) {
            topology[count++]= queue.remove();
            int element=topology[count-1];
            mark[element]=true;
            for(int j=0;j<in.length;j++)
                if(adjMatrix[element][j]!=0)
                    in[j]--;
            for(int i=0;i<g.adjMatrix.length;i++)
                if(in[i]==0 && mark[i]==false){
                    queue.add(i);
                }
        }

        for(int i=0;i<adjMatrix.length;i++)
            if(in[i]!=0){
                if(mark[i]==false){
                    System.out.println("There is a circle for this directed graph!");
                    return null;
                }
            }

        return topology;
    }

    public Vertice [] deepTopologySort(Graph g,Vertice v){
        Vertice [] newVertice=new Vertice[g.ver.length];
        int count=0;
        Stack<Vertice> s=new Stack<Vertice>();
        s.push(v);
        boolean isEnd=false;
        while (!s.isEmpty()){
            Vertice vertice=s.peek();
            mark[vertice.getNum()]=true;
            Edge e;
            for(e=vertice.getFirstEdge();e!=null;e=e.getNextEdge())
                if(mark[e.getTo().getNum()]==false) {
                    s.push(vertice.getFirstEdge().getTo());
                    break;
                }
            if(e==null) isEnd=true;
            if(isEnd) {
                newVertice[count++] = vertice;
                s.pop();
            }
        }
        Vertice [] reverse=new  Vertice[g.ver.length];
        for(int i=reverse.length-1;i>=0;i--)
            reverse[reverse.length-1-i]=newVertice[i];
        return reverse;


    }
}
