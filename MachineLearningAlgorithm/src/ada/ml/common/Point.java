package ada.ml.common;

public class Point {
	private int dimension;
	private double[] vector=null;
	private double vecLen=0d;
	private static DistanceCalculator ds;
	public static void setDistanceCalculator(DistanceCalculator ds){
		Point.ds=ds;
	}
	public Point(double[] vector){
		this.vector = vector;
		for(double v : vector){
			vecLen+=v*v;
		}
		vecLen=Math.sqrt(vecLen);
		this.dimension=vector.length;
	}
	public int getDimension(){
		return this.dimension;
	}
	
	
	public double[] getVector() {
		return vector;
	}
	public double getVecLen() {
		return vecLen;
	}
	public static DistanceCalculator getDs() {
		return ds;
	}
	public double calculateDistance(Point p){
		return ds.getDistance(this, p);
	}
	public String toString(){
		String s="( ";
		for(double d:vector){
			s=s.concat(String.valueOf(d)).concat(",");
		}
		s=s.concat(" )");
		return s;
	}
}
