package ada.ml.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import java.util.concurrent.ConcurrentSkipListSet;

import ada.ml.common.EuclideanDistanceCalculator;
import ada.ml.common.Point;

/**
 * @author zhouxc
 * 2014-04-13 11:49:30 create
 * 自下而上的层次聚类，Agenes算法
 * 这里两个簇距离的定义有三种：
 * (1)单链(MIN):定义簇的邻近度为不同两个簇的两个最近的点之间的距离。

   (2)全链(MAX):定义簇的邻近度为不同两个簇的两个最远的点之间的距离。

   (3)组平均：定义簇的邻近度为取自两个不同簇的所有点对邻近度的平均值。
   目前本算法实现的是单链
   算法描述：算法时间复杂度为 n*n
   1. 将Point[] p两两配对并计算两点间的距离，生成Pair对象, n*n
   2. 将Pair按照从小到大的顺序排序。n*logn
   3.repeat:
   	    遍历Pair 如果Pair中的两个点属于不同类别，则合并这两个点所在的类。
   	 until: 类别数=finalClusterNum 或者 类间距>diff
 * **/
public class Agnes {
	private Point[] p=null;
	private int finalClusterNum=20;
	private int currentClusterNum=20;
	double diffLine=10;
	private Map<Integer,Set<Integer>> pointIdTopointSet=new HashMap<Integer,Set<Integer>>();
	/**
	 * @param fcnum 聚集成的类数
	 * @param diff 类间的最小距离
	 * 停止条件为满足 聚类后的类别数量达到fcnum或者是类间最小距离为diff以上。
	 * **/
	public Agnes(Point[] p,int fcnum,double diff){
		this.p=p;
		this.finalClusterNum=fcnum;
		this.currentClusterNum=p.length;
		this.diffLine=diff;
	}
	private static class PointPair implements Comparable<PointPair>{
		private int first;
		private int second;
		private double distance;
		public PointPair(int f,int s,double distance){
			this.first=f;
			this.second=s;
			this.distance=distance;
		}
		@Override
		public int compareTo(PointPair o) {
			// TODO Auto-generated method stub
			if(this.distance>o.distance){
				return 1;
			}else if(this.distance<o.distance){
				return -1;
			}else{
				return 0;
			}
		}
		public int getFirst() {
			return first;
		}
		public int getSecond() {
			return second;
		}
	}
	private List<PointPair> getPointPairAndSort(){
		List<PointPair> l=new ArrayList<PointPair>();
		for(int i=0;i<p.length;i++){
			for(int j=i+1;j<p.length;j++){
				PointPair pair=new PointPair(i,j,p[i].calculateDistance(p[j]));
				l.add(pair);
			}
		}
		Collections.sort(l);
		return l;
	}
	public List<Point[]> doCluster(){
		List<PointPair> l=getPointPairAndSort();
		
		for(int i=0;i<p.length;i++){
			Set<Integer> s=new ConcurrentSkipListSet<Integer>();
			//Set<Integer> s=new HashSet<Integer>();
			this.pointIdTopointSet.put(i, s);
			s.add(i);
		}
		for(PointPair pa : l){
			Set<Integer> clusterFirst=this.pointIdTopointSet.get(pa.getFirst());
			Set<Integer> clusterSecond=this.pointIdTopointSet.get(pa.getSecond());
			//这两个点已经在一个cluster中 则移动到下一个pair
			if(clusterFirst.contains(pa.getSecond())){
				continue;
			}
			mergeCluster(clusterFirst,clusterSecond);
			this.currentClusterNum--;
			double dis=p[pa.getFirst()].calculateDistance(p[pa.getSecond()]);
			//System.out.println(dis);
			if(this.currentClusterNum==this.finalClusterNum||dis>this.diffLine){
				break;
			}
		}
		return getResult();
	}
	private List<Point[]> getResult(){
		List<Point[]> ret=new ArrayList<Point[]>();
		Collection<Set<Integer>> c=this.pointIdTopointSet.values();
		Set<Set<Integer>> s=new HashSet<Set<Integer>>();
		for(Set<Integer> st:c){
			s.add(st);
		}
		for(Set<Integer> each:s){
			Point[] pa=new Point[each.size()];
			int index=0;
			for(Integer pid:each){
				pa[index]=p[pid];
				index++;
			}
			ret.add(pa);
		}
		return ret;
	}
	private void mergeCluster(Set<Integer> f, Set<Integer> secnd){
		for(int id :f){
			Set<Integer> s=this.pointIdTopointSet.get(id);
			s.addAll(secnd);
		}
		for(int id :secnd){
			Set<Integer> s=this.pointIdTopointSet.get(id);
			s.addAll(f);
		}
	}
	public static void main(String[] args){
		int num=50;
		int k=10;
		Point.setDistanceCalculator(new EuclideanDistanceCalculator());
		Point[] pa=new Point[num];
/*		pa[0]=new Point(0,new double[]{0,1});
		pa[1]=new Point(0,new double[]{1,0});
		pa[2]=new Point(0,new double[]{0.8,0.2});
		pa[3]=new Point(0,new double[]{0.2,0.8});*/
		Random r=new Random();
		for(int i=0;i<num;i++){
			Point p=new Point(new double[]{r.nextInt(200),r.nextInt(300)});
			 pa[i]=p;
		}
		Agnes a=new Agnes(pa,k,1);
		List<Point[]> ret=a.doCluster();
		for(Point[] rep:ret){
			for(Point p:rep){
				System.out.print(","+p.toString());
			}
			System.out.println("\n");
		}
	}
}
