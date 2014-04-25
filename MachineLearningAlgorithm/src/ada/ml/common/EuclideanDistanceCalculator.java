/**
 * @author zhouxiaocao
 * 2014年4月17日 上午9:35:17 create
 */
package ada.ml.common;

/**
 * @author zhouxc
 *
 */
public class EuclideanDistanceCalculator implements DistanceCalculator{

	/* (non-Javadoc)
	 * @see ada.ml.common.DistanceCalculator#getDistance(ada.ml.common.Point, ada.ml.common.Point)
	 */
	@Override
	public double getDistance(Object p1, Object p2) {
		// TODO Auto-generated method stub
		double[] v1=((Point)p1).getVector();
		double[] v2=((Point)p2).getVector();
		double distance=0;
		for(int i=0;i<v1.length;i++){
			distance+=(v1[i]-v2[i])*(v1[i]-v2[i]);
		}
		return Math.sqrt(distance);
	}

}
