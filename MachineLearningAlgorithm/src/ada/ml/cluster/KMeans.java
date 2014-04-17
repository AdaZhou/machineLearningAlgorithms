/**
 * @author zhouxiaocao
 * 2014年4月16日 下午5:50:40 create
 */
package ada.ml.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ada.ml.common.Point;

/**
Input: Dataset D, number clusters k
Output: Set of cluster representatives C, cluster membership vector m
1.choose k start point radomly.
2.classify the other points to the cluster that start point representative
repeat:
3.calculate the center
4. relocate point
until center point is stable or max iteration has been reached;
 */
public class KMeans {
	private Point[] p;
	private int k;
	private double precision;
	private int maxIteration;
	private Map<Point,List<Point>> centerToMember=new HashMap<Point,List<Point>>();
	public KMeans(Point[] p,int k,double moveDistance,int maxIteration){
		this.p=p;
		this.k=k;
		this.precision=moveDistance;
		this.maxIteration=maxIteration;
	}
	public Map<Point,List<Point>> doCluster(){
		Random r=new Random();
		int count=0;
		//initial k center
		while(count<k){
			int pi=r.nextInt(k);
			if(!centerToMember.containsKey(pi)){
				centerToMember.put(p[pi], new ArrayList<Point>());
				count++;
			}
		}
		boolean converge=false;
		int iteration=0;
		while(!converge&&iteration<maxIteration+1){
			iteration++;
			reloacate();
			converge=calculateNewCenter();
		}
		return this.centerToMember;
	}
	private boolean calculateNewCenter(){
		Map<Point,List<Point>> nc=new HashMap<Point,List<Point>>();
		Set<Point> centers=this.centerToMember.keySet();
		double[] move=new double[this.k];
		int index=0;
		for(Point center:centers){
			List<Point> l=this.centerToMember.get(center);
			Point centerNew=getCenter(l);
			double distance=center.calculateDistance(centerNew);
			move[index]=distance;
			index++;
			nc.put(center, new ArrayList<Point>());
		}
		boolean converge=true;
		for(double movedis: move){
			if(movedis>this.precision){
				converge=false;
			}
		}
		if(!converge){
			this.centerToMember=nc;
		}
		return converge;
	}
	private void reloacate(){
		Set<Point> centerSet=this.centerToMember.keySet();
		for(Point pt:p){
			double dis=0;
			Point nearest=null;
			for(Point center:centerSet){
				if(nearest==null){
					nearest=center;
					dis=pt.calculateDistance(center);
				}else{
					double dis2=pt.calculateDistance(center);
					if(dis2<dis){
						dis=dis2;
						nearest=center;
					}
				}
			}
			this.centerToMember.get(nearest).add(pt);
		}
	}
	private Point getCenter(List<Point> pl){
		int dimension=pl.get(0).getDimension();
		double[][] ar=new double[dimension][1];
		for(Point pt:pl){
			for(int i=0;i<pt.getVector().length;i++){
				ar[i][1]+=pt.getVector()[i];
			}
		}
		double[] vec=new double[dimension];
		for(int i=0;i<dimension;i++){
			vec[i]=ar[i][1]/pl.size();
		}
		Point cen=new Point(vec);
		return cen;
	}
	public static void main(String[] args){
		Random r=new Random();
		for(int i=0;i<10;i++){
			System.out.println(r.nextInt(300));
		}
	}
}
