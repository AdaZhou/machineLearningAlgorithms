/**
 * @author zhouxiaocao
 * 2014-4-24 上午11:28:10 create
 */
package ada.ml.regression;



import ada.ml.common.Vector;

/**
 * @author zhouxc
 *
 */
public class LinearRegression {
	public static void main(String[] args){
		int dataNumber=1000;
		System.out.println("=======BGD批量梯度下降=====");
		LinearRegression lr=new LinearRegression();
		Vector[] xs=new Vector[dataNumber];
		Vector[] ys=new Vector[dataNumber];
		lr.generateSample(xs, ys,dataNumber);
		Vector thetaB=new Vector(new double[]{-10,12});
		lr.batchGD(ys, thetaB, xs, new double[]{1E-9,1E-5});
		Vector thetaS=new Vector(new double[]{-10,1000});
		lr.SGD(ys,thetaS,xs,new double[]{1E-7,1E-8});
		System.out.println("=====SGD随机梯度下降========\n finished!");
		System.out.println("BGD result: "+thetaB);
		System.out.println("SGD result: "+thetaS);
	}
	public void generateSample(Vector[] xs,Vector[] ys,int number){
		model(xs,ys,number);
	}
	public void model(Vector[] xs,Vector[] ys,int dataNumber){
		model(xs,ys,new Vector(new double[]{12,4}),0,dataNumber);
	}
	public void model(Vector[] xs,Vector[] ys,Vector theta,int start,int end){
		int index=start;
		for(int i=start;i<end;i++){
			double[] x=new double[]{i*2,1};
			Vector xv=new Vector(x);
			double y=theta.product(xv);
			xs[index]=xv;
			ys[index]=new Vector(new double[]{y});
			index++;
		}
	}
	/**
	 * 方括号表示下标 ， 圆括号上标
	 * until converge：
	 * 	for j: 0->theta.getDimension()
	 * 		theta[j]=theta[j]+alpha*SUM((y(i)-theta*x(i))*x(i)[j])
	 * **/
	public void batchGD(Vector[] y,Vector theta,Vector[] x,double[] alpha){
		double convergeThreshold=10E-13;
		int iteration=0;
		double deltaTheta=100;
		while(Math.abs(deltaTheta)>convergeThreshold){
			deltaTheta=0;
			for(int i=0;i<theta.getDimension();i++){
				double delta=bgdIteration( y,theta, x, alpha[i],i);
				if(Math.abs(deltaTheta)<Math.abs(delta)){
					deltaTheta=delta;
				}
			}
			System.out.println("iteration: "+ iteration+" , delta: "+deltaTheta+", theta: "+theta);
			iteration++;
		}
	}
	public double bgdIteration(Vector[] y,Vector theta,Vector[] x,double alpha,int thetaupdatedIndex){
		double sum=0;
		for(int i=0;i<x.length;i++){
			sum+=(y[i].getValueAtIndex(0)-theta.product(x[i]))*x[i].getValueAtIndex(thetaupdatedIndex);
		}
		double temp=theta.getValueAtIndex(thetaupdatedIndex)+alpha*sum;
		double deltaTheta=theta.getValueAtIndex(thetaupdatedIndex)-temp;
		theta.setValueAtIndex(thetaupdatedIndex, temp);
		return deltaTheta;
	}
	/**
	 * for i: 0->m
	 * 		for j: 0->theta.getDimension()
	 * 			theta[j]=theta[j]+alpha*(y(i)[0]-theta*x)*x(i)[j]
	 * **/
	public void SGD(Vector[] y,Vector theta,Vector[] x,double[] alpha){
		for(int i=0;i<x.length;i++){
			for(int j=0;j<theta.getDimension();j++){
				double h=theta.product(x[i]);
				double residual=y[i].getValueAtIndex(0)-h;
				double temp=theta.getValueAtIndex(j)+residual*x[i].getValueAtIndex(j)*alpha[j];
				System.out.println("y[i]="+y[i].getValueAtIndex(0)+",h="+h+", residual="+residual+",theta="+theta+",x[i]="+x[i]);
				theta.setValueAtIndex(j, temp);
				
			}
			System.out.println("iteration: "+i+", theta:"+theta);
		}
	}
}
