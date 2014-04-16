package ada.ml.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import ada.ml.common.DistanceCalculator;
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
 * **/
public class Agenes {
	private Point[] p=null;
	private int finalClusterNum=20;
	private int currentClusterNum=20;
	//记录了某个节点所在的cluster被合并而成的所有历史记录，历史记录之间用“，”隔开。每个历史记录Cluster中
	//又包含了两个被合并的cluster,这两个cluster用 “｜” 隔开
	private Map<Integer,Set<Integer>> pointIdTopointSet=new ConcurrentHashMap<Integer,Set<Integer>>();
	public Agenes(Point[] p,int fcnum){
		this.p=p;
		this.finalClusterNum=fcnum;
		this.currentClusterNum=p.length;
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
				return -1;
			}else if(this.distance<o.distance){
				return 1;
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
	public void doCluster(){
		List<PointPair> l=getPointPairAndSort();
		
		for(int i=0;i<p.length;i++){
			Set<Integer> s=new ConcurrentSkipListSet<Integer>();
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
			//每个历史记录cluster用 "#" 隔开
			mergeCluster(clusterFirst,clusterSecond);
			this.currentClusterNum--;
			if(this.currentClusterNum==this.finalClusterNum){
				break;
			}
		}
	}
	public List<Point[]> getResult(){
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
	//两个新合并的cluster用 “｜” 隔开,每个cluster中原有的元素用 “,”隔开
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
		Point.setDistanceCalculator(new  DistanceCalculator());
		Point[] pa=new Point[num];
/*		pa[0]=new Point(0,new double[]{0,1});
		pa[1]=new Point(0,new double[]{1,0});
		pa[2]=new Point(0,new double[]{7,2});
		pa[3]=new Point(0,new double[]{2,7});*/
		for(int i=0;i<num;i++){
			Random r=new Random(); 
			Random l=new Random(); 
			Point p=new Point(i,new double[]{r.nextInt(100),l.nextInt(300)});
			//System.out.println(p.toString());
			 pa[i]=p;
		}
		Agenes a=new Agenes(pa,k);
		a.doCluster();
		List<Point[]> ret=a.getResult();
		for(Point[] rep:ret){
			for(Point p:rep){
				System.out.print(","+p.toString());
			}
			System.out.println("\n");
		}
	}
}
