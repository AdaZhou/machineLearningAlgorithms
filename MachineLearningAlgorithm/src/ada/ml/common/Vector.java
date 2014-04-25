/**
 * @author zhouxiaocao
 * 2014-4-24 下午2:04:12 create
 */
package ada.ml.common;

import java.util.Random;

/**
 * @author zhouxc
 *
 */
public class Vector {
	private double[] vec;
	public Vector(double[] vec){
		this.vec=vec;
	}
	public double[] getVecValue(){
		return vec;
	}
	public int getDimension(){
		return vec.length;
	}
	public double getValueAtIndex(int index){
		return vec[index]; 
	}
	public double setValueAtIndex(int index,double v){
		return vec[index]=v; 
	}
	public double product(Vector v){
		double sum=0;
		for(int i=0;i<v.vec.length;i++){
			sum+=this.vec[i]*v.vec[i];
		}
		return sum;
	}
	public Matrix product(Matrix m){
		Matrix ret=new Matrix(new Vector[]{this});
		return ret.product(m);
	}
	public static Vector generateRandomVec(int highLimit,int dimension){
		Vector v=new Vector(new double[dimension]);
		Random r=new Random();
		for(int i=0;i<v.vec.length;i++){
			v.vec[i]=r.nextInt(highLimit);
		}
		return v;
	}
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("(");
		for(double d:vec){
			sb.append(d).append(",");
		}
		sb.append(")");
		return sb.toString();
	}
}
