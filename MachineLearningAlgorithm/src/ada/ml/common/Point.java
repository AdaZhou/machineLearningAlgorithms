package ada.ml.common;

public class Point {
	private double[] vector=null;
	private double vecLen=0d;
	private int id;
	private static DistanceCalculator ds;
	public static void setDistanceCalculator(DistanceCalculator ds){
		Point.ds=ds;
	}
	public Point(int id,double[] vector){
		this.vector = vector;
		this.id=id;
		for(double v : vector){
			vecLen+=v*v;
		}
		vecLen=Math.sqrt(vecLen);
	}
	public int getId(){
		return this.id;
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
		return ds.calculate(this, p);
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
