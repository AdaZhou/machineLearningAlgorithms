package ada.ml.common;

public class Point {
	private double[] tfIdfVec=null;
	private double vecLen=1d;
	private int id;
	private static DistanceCalculator ds;
	public static void setDistanceCalculator(DistanceCalculator ds){
		Point.ds=ds;
	}
	public Point(int id,double[] tfIdf){
		this.tfIdfVec = tfIdf;
		this.id=id;
		for(double v : tfIdf){
			vecLen+=v*v;
		}
	}
	public int getId(){
		return this.id;
	}
	
	public double[] getTfIdfVec() {
		return tfIdfVec;
	}
	public double getVecLen() {
		return vecLen;
	}
	public static DistanceCalculator getDs() {
		return ds;
	}
	public double calculateDistance(Point p){
		return ds.calculate(this, p);
	}
}
