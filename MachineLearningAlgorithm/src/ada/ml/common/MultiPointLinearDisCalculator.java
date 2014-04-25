/**
 * @author zhouxiaocao
 * 2014年4月24日 下午5:51:04 create
 */
package ada.ml.common;

/**
 * @author zhouxc
 *
 */
public class MultiPointLinearDisCalculator implements DistanceCalculator{
	private double[] weight=null;
	public MultiPointLinearDisCalculator(double[] weight){
		this.weight=weight;
	}
	/* (non-Javadoc)
	 * @see ada.ml.common.DistanceCalculator#getDistance(java.lang.Object, java.lang.Object)
	 * 两个MultiPoint的距离为它们其中的point的距离的线性加权。具有更大weight的维度的point更能影响距离。
	 */
	@Override
	public double getDistance(Object p1, Object p2) {
		// TODO Auto-generated method stub
		MultiPoint mp1=(MultiPoint)p1;
		MultiPoint mp2=(MultiPoint)p2;
		Point[] ps1=mp1.getPoints();
		Point[] ps2=mp2.getPoints();
		double dis=0;
		for(int i=0;i<ps1.length;i++){
			Point po1=ps1[i];
			Point po2=ps2[i];
			dis+=po1.calculateDistance(po2)*weight[i];
		}
		return dis;
	}
	
}
