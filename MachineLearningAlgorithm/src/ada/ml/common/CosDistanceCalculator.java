/**
 * @author zhouxiaocao
 * 2014年4月17日 上午9:35:53 create
 */
package ada.ml.common;

/**
 * @author zhouxc
 *
 */
public class CosDistanceCalculator implements DistanceCalculator{
	public double getDistance(Point p1, Point p2){
		double[] v=p1.getVector();
		double innerProduct=0d;
		for(int i=0;i<v.length;i++){
			innerProduct+=v[i]*p2.getVector()[i];
		}
		return (1d-innerProduct/(p1.getVecLen()*p2.getVecLen()));
	}
}
