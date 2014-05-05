/**
 * @author zhouxiaocao
 * 2014年4月25日 下午3:44:09 create
 */
package ada.ml.util;

import ada.ml.common.Matrix;
import ada.ml.common.Vector;

/**
 * @author zhouxc
 *
 */
public class SampleGenerator {
	/**
	 * 3个线性模型共同生成一组离散点
	 * x是3维
	 * y是1维
	 * theta是3维
	 * **/
	public static void generateLinearSample(Vector[] xs,Vector[] ys,int dataNumber){
		xs=new Vector[dataNumber];
		ys=new Vector[dataNumber];
		generateLinearSample(xs,ys,new Vector(new double[]{3,4,7}),0,20);
		generateLinearSample(xs,ys,new Vector(new double[]{5,4,1}),20,40);
		generateLinearSample(xs,ys,new Vector(new double[]{6,2,8}),40,60);
	}
	private static void generateLinearSample(Vector[] xs,Vector[] ys,Vector theta,int start,int end){
		int index=start;
		for(int i=start;i<end;i++){
			double[] x=new double[]{i,i+i*2,1};
			Vector xv=new Vector(x);
			double y=theta.product(xv);
			xs[index]=xv;
			ys[index]=new Vector(new double[]{y});
			index++;
		}
	}
/**
 * 
 * 两个最高次数为二次的多项式模型共同生成一组离散点
 * x是4维
 * y是1维
 * theta是4维
 * **/
	private static void generateQuadricSample(Vector[] xs,Vector[] ys){
		xs=new Vector[80];
		ys=new Vector[80];
		double[][] mA1=new double[][]{{3,2,0,1},{2,5,12,11},{0,12,7,9},{1,11,9,0}};
		Matrix A1=new Matrix(mA1,4,4);
		Vector theta1=new Vector(new double[]{6,2,8,30});
		generateQuadricSample( xs,ys,A1, theta1,0,40);
		double[][] mA2=new double[][]{{3,7,9,12},{7,24,2,0},{9,2,4,0},{12,0,0,1}};
		Matrix A2=new Matrix(mA2,4,4);
		Vector theta2=new Vector(new double[]{3,0,9,7});
		generateQuadricSample( xs,ys,A2, theta2,40,80);
	}
	/**
	 * 
	 * X'AX+theta'X=y
	 * **/
	private static void generateQuadricSample(Vector[] xs,Vector[] ys,Matrix A, Vector theta,int start,int end){
		for(int i=start;i<end;i++){
			double[] xv=new double[]{i,i+i*2,i+5,1};
			Vector x=new Vector(xv);
			xs[i]=x;
			Matrix xm=new Matrix(new Vector[]{x});
			Matrix xmT=xm.transposition();
			double retQuadric=xmT.product(A).product(xm).getIJValue(0, 0);
			double retLinear=theta.product(x);
			ys[i]=new Vector(new double[]{retQuadric+retLinear});
		}
	}
}
