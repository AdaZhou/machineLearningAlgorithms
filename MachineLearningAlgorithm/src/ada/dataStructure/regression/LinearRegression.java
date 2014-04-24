/**
 * @author zhouxiaocao
 * 2014-4-24 上午11:28:10 create
 */
package ada.dataStructure.regression;



import ada.ml.common.Vector;

/**
 * @author zhouxc
 *
 */
public class LinearRegression {
	public static void main(String[] args){
		System.out.println("=======BGD批量梯度下降=====");
		int xDimension=2;
		LinearRegression lr=new LinearRegression();
		Vector[] xs=new Vector[60];
		Vector[] ys=new Vector[60];
		lr.generateSample(xs, ys);
		Vector theta=new Vector(new double[]{9,0,7});
		for(int i=0;i<xDimension;i++){
			//注意这里的 alpha一定要取的合适，太大的话得不到收敛结果
			lr.batchGD(ys,theta,xs,i,2E-7);
		}
		System.out.println("BGD: "+theta);
		for(int i=0;i<xDimension;i++){
			lr.SGD(ys,theta,xs,i,2E-7);
		}
		System.out.println("=====SGD随机梯度下降========");
		System.out.println("SGD: "+theta );
	}
	public void generateSample(Vector[] xs,Vector[] ys){
		model(xs,ys);
	}
	public void model(Vector[] xs,Vector[] ys){
		model(xs,ys,new Vector(new double[]{3,4,7}),0,20);
		model(xs,ys,new Vector(new double[]{5,4,1}),20,40);
		model(xs,ys,new Vector(new double[]{6,2,8}),40,60);
	}
	public void model(Vector[] xs,Vector[] ys,Vector theta,int start,int end){
		int index=start;
		for(int i=start;i<end;i++){
			double[] x=new double[]{i,i+i*2};
			Vector xv=new Vector(x);
			double y=theta.product(xv);
			xs[index]=xv;
			ys[index]=new Vector(new double[]{y});
			index++;
		}
	}
	/**
	 * 方括号表示下标 ， 圆括号上标
	 * theta[j]=theta[j]-alpha*SUM((y(i)-theta*x(i))*x(i)[j])
	 * **/
	public void batchGD(Vector[] y,Vector theta,Vector[] x,int thetaupdatedIndex,double alpha){
		double convergeThreshold=10E-2;
		double deltaTheta=100;
		int iteration=0;
		while(Math.abs(deltaTheta)>convergeThreshold){
			double sum=0;
			for(int i=0;i<x.length;i++){
				sum+=(y[i].getValueAtIndex(0)-theta.product(x[i]))*x[i].getValueAtIndex(thetaupdatedIndex);
			}
			double temp=theta.getValueAtIndex(thetaupdatedIndex)+alpha*sum;
			deltaTheta=theta.getValueAtIndex(thetaupdatedIndex)-temp;
			theta.setValueAtIndex(thetaupdatedIndex, temp);
			System.out.println("iteration: "+ iteration+" , delta: "+deltaTheta+", theta["+thetaupdatedIndex+"]: "+theta.getValueAtIndex(thetaupdatedIndex));
			iteration++;
		}
	}
	/**
	 * for i: 0->m
	 * 		theta[j]=theta[j]-alpha*(y(i)[0]-theta*x)*x(i)[j]
	 * **/
	public void SGD(Vector[] y,Vector theta,Vector[] x,int thetaupdatedIndex,double alpha){
		for(int i=0;i<x.length;i++){
			double temp=theta.getValueAtIndex(thetaupdatedIndex)+(y[i].getValueAtIndex(0)-theta.product(x[i]))*x[i].getValueAtIndex(thetaupdatedIndex)*alpha;
			theta.setValueAtIndex(thetaupdatedIndex, temp);
			//System.out.println("iteration: "+i);
		}
	}
}
