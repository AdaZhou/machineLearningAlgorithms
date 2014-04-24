package ada.ml.common;

public class Point {
	private int dimension;
	private double[] vector=null;
	private double vecLen=0d;
	private static DistanceCalculator ds;
	private String id;
	private String value;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
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
	public Point(double[] vector,String id){
		double sum=0;
		for(double i:vector){
			sum+=i;
		}
		this.vector = new double[vector.length];
		for(int i=0;i<vector.length;i++){
			this.vector[i]=vector[i]/sum;
		}
		
		this.id=id;
		for(double v : vector){
			vecLen+=v*v;
		}
		vecLen=Math.sqrt(vecLen);
		this.dimension=vector.length;
	}
	public int getDimension(){
		return this.dimension;
	}
	public String getId(){
		return id;
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
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Point)){
			return false;
		}
		Point p=(Point)obj;
		if(p==obj){
			return true;
		}
		double[] vec=p.vector;
		for(int i=0;i<vec.length;i++){
			if(vec[i]!=this.vector[i]){
				return false;
			}
		}
		return true;
	}
}
