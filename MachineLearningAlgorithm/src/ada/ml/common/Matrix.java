/**
 * @author zhouxiaocao
 * 2014年4月25日 下午3:57:01 create
 */
package ada.ml.common;

/**
 * @author zhouxc
 *
 */
public class Matrix{
	private double[][] matrix;
	private Vector[] columnVector;
	//行数
	private int m;
	//列数
	private int n;
	public Matrix(double[][] matrix,int m,int n){
		this.matrix=matrix;
		columnVector=new Vector[n];
		for(int j=0;j<n;j++){
			double[] v=new double[m];
			for(int i=0;i<m;i++){
				v[i]=this.matrix[i][j];
			}
			Vector vec=new Vector(v);
			columnVector[j]=vec;
		}
	}
	/**
	 * 使用列向量初始化矩阵
	 * **/
	public Matrix(Vector[] col){
		this.matrix=new double[col[0].getDimension()][col.length];
		for(int j=0;j<col.length;j++){
			double[] vv=col[j].getVecValue();
			for(int i=0;i<vv.length;i++){
				this.matrix[i][j]=vv[i];
			}
		}
	}
	public Matrix product(Matrix rMatrix){
		double[][] retm=new double[m][rMatrix.n];
		Matrix result=new Matrix(retm,m,rMatrix.n);
		for(int i=0;i<this.m;i++){
			for(int j=0;j<rMatrix.n;j++){
				double sum=0;
				for(int k=0;k<this.n;k++){
					for(int l=0;l<rMatrix.m;l++){
						sum+=this.matrix[i][k]*rMatrix.matrix[j][l];
					}
				}
				result.matrix[i][j]=sum;
			}
		}
		return result;
	}
	public Matrix product(Vector vec){
		Matrix m=new Matrix(new Vector[]{vec});
		return this.product(m);
	}
	public Matrix transposition(){
		double[][] vv=new double[n][m];
		for(int j=0;j<n;j++){
			for(int i=0;i<m;i++){
				vv[j][i]=this.matrix[i][j];
			}
		}
		return new Matrix(vv,n,m);
	}
	public double getIJValue(int i,int j){
		return this.matrix[i][j];
	}
}
