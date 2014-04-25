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
	public double getDistance(Object p1, Object p2){
		double[] v=((Point)p1).getVector();
		double innerProduct=0d;
		for(int i=0;i<v.length;i++){
			innerProduct+=v[i]*((Point)p2).getVector()[i];
		}
		return (1d-innerProduct/((Point) p1).getVecLen()*((Point)p2).getVecLen());
	}
}
