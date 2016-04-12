/**
 * Created by yujingxie on 10/4/15.
 */
public class Detcompute {
    double [][]detmatrix;

    public double detmatrixcompute(double [][] matrix ){
        double det=0.0;
        if(matrix.length==1 && matrix[0].length==1)
            det=matrix[0][0];
        else
            for(int j=0;j<matrix.length;j++){
                int mcoefficient=0;
                if(j%2==0) mcoefficient=1;
                else mcoefficient=-1;

                double [][] adj=new double[matrix.length-1][matrix.length-1];
                if(j==0)
                  for(int k=0;k<adj.length;k++)
                      for(int i=0;i<adj.length;i++)
                          adj[k][i]=matrix[k+1][i+1];
                else if(j==matrix.length-1)
                    for(int m=0;m<adj.length;m++)
                        for(int n=0;n<adj.length;n++)
                            adj[m][n]=matrix[m+1][n];
                else{
                    for(int f=0;f<matrix.length-1;f++)
                        for(int l=0;l<j;l++)
                            adj[f][l]=matrix[f+1][l];
                    for(int p=0;p<adj.length;p++)
                        for(int d=j;d<adj.length;d++)
                            adj[p][d]=matrix[p+1][d+1];
                }
                det+= mcoefficient * matrix[0][j]* detmatrixcompute(adj);
            }
        return det;
    }

    public double [][] computem(double [][] matrix){
        double [][] matrtixm=new double[matrix.length][matrix.length];

        for(int i=0;i<matrix.length;i++)
            for(int j=0;j<matrix.length;j++){
                int mcoefficient=0;
                if((i+j)%2==0) mcoefficient=1;
                else  mcoefficient=-1;

                double [][] matrixc=new double[matrix[0].length-1][matrix[0].length-1];
                if(j==0){
                    if(i==0)
                       for(int k=0;k<matrixc.length;k++)
                           for(int l=0;l<matrixc.length;l++)
                          matrixc[k][l]=matrix[k+1][l+1];
                    else if(i==matrix[0].length-1)
                        for(int g=0;g<matrixc.length;g++)
                            for(int h=0;h<matrixc.length;h++)
                                matrixc[g][h]=matrix[g][h+1];
                    else {
                        for(int a=0;a<i;a++)
                            for(int s=0;s<matrixc.length;s++)
                            matrixc[a][s]=matrix[a][s+1];
                        for(int d=i;d<matrixc.length;d++)
                            for(int f=0;f<matrixc.length;f++)
                                matrixc[d][f]=matrix[d+1][f+1];
                    }

                }
                else if(j==matrix[0].length-1){
                    if(i==0)
                        for(int e=0;e<matrixc.length;e++)
                            for(int r=0;r<matrixc.length;r++)
                                matrixc[e][r]=matrix[e+1][r];
                    else  if(i==matrix.length-1)
                        for(int q=0;q<matrixc.length;q++)
                            for(int w=0;w<matrixc.length;w++)
                                matrixc[q][w]=matrix[q][w];
                    else{
                        for(int t=0;t<i;t++)
                            for(int y=0;y<matrixc.length;y++)
                                matrixc[t][y]=matrix[t][y];
                        for(int u=i;u<matrixc.length;u++)
                            for(int o=0;o<matrixc.length;o++)
                                matrixc[u][o]=matrix[u+1][o];
                    }
                }
                else {
                    if(i==0){
                        for(int p=0;p<matrixc.length;p++)
                            for(int z=0;z<j;z++)
                                matrixc[p][z]=matrix[p+1][z];
                        for(int x=0;x<matrixc.length;x++)
                            for(int c=j;c<matrixc.length;c++)
                                matrixc[x][c]=matrix[x+1][c+1];
                    }
                    else if(i==matrix.length-1){
                        for(int v=0;v<matrixc.length;v++)
                            for(int b=0;b<j;b++)
                                matrixc[v][b]=matrix[v][b];
                        for(int n=0;n<matrixc.length;n++)
                            for(int m=j;m<matrixc.length;m++)
                                matrixc[n][m]=matrix[n][m+1];
                    }
                    else {
                        for(int aa=0;aa<i;aa++)
                            for(int ss=0;ss<j;ss++)
                                matrixc[aa][ss]=matrix[aa][ss];
                        for(int dd=0;dd<i;dd++)
                            for(int ff=j;ff<matrixc.length;ff++)
                                matrixc[dd][ff]=matrix[dd][ff+1];
                        for(int qq=i;qq<matrixc.length;qq++)
                            for(int ww=0;ww<j;ww++)
                                matrixc[qq][ww]=matrix[qq+1][ww];
                        for(int ee=i;ee<matrixc.length;ee++)
                            for(int rr=j;rr<matrixc.length;rr++)
                                matrixc[ee][rr]=matrix[ee+1][rr+1];
                    }
                }

                matrtixm[j][i]=(double)mcoefficient*detmatrixcompute(matrixc);

            }
        return matrtixm;
    }
}
