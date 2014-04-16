package ada.ml.common;
/**
 * @author zhouxc
 * 2014-04-13 01:29:30 create
 * **/
public class DistanceCalculator {
	//by default, calculate cos<p1,p2>
	public double calculate(Point p1, Point p2){
		double[] v=p1.getVector();
		double innerProduct=0d;
		for(int i=0;i<v.length;i++){
			innerProduct+=v[i]*p2.getVector()[i];
		}
		return innerProduct/(p1.getVecLen()*p2.getVecLen());
	}
}
