/**
 * @author zhouxiaocao
 * 2014年4月25日 下午3:06:13 create
 */
package ada.ml.regression;

import ada.ml.common.Vector;

/**
 * @author zhouxc
 *非参数算法
 */
public class LocallyWeightedLinearRegression {
	/**
	 * for i: 0->m
	 * 		for j: 0->theta.getdimension
	 * 			theta[j]=theta[j]+alpha*SUM(W(i)*(y(i)-theta*x(i))*x(i)[j])
	 * 
	 * **/
	public void predict(Vector x,double bandW){
		
	}
	
}
