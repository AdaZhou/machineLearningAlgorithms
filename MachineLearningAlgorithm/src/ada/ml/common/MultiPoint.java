/**
 * @author zhouxiaocao
 * 2014年4月24日 下午5:38:50 create
 */
package ada.ml.common;



/**
 * @author zhouxc
 *这个复合point包含了多个Point
 */
public class MultiPoint extends Point{
	private Point[] ps=null;
	private static DistanceCalculator ds=null;
	public static void setDistanceCalculator(DistanceCalculator ds){
		MultiPoint.ds=ds;
	}
	public MultiPoint(Point[] p,String id){
		this.ps=p;
		this.id=id;
	}
	public MultiPoint(Point[] p){
		this.ps=p;
	}
	public Point[] getPoints(){
		return this.ps;
	}
	public double calculateDistance(Point p){
		return ds.getDistance(this, p);
	}
}
