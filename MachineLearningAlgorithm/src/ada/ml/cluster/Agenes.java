package ada.ml.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	private Map<Integer,String> pointIdToClusterLevel=new HashMap<Integer,String>();
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
	private void getPointPairAndSort(){
		List<PointPair> l=new ArrayList<PointPair>();
		for(int i=0;i<p.length;i++){
			for(int j=i+1;j<p.length;j++){
				PointPair pair=new PointPair(i,j,p[i].calculateDistance(p[j]));
				l.add(pair);
			}
		}
		Collections.sort(l);
	}
	public void doCluster(List<PointPair> l){
		getPointPairAndSort();
		for(PointPair pa : l){
			String clusterFirst=this.pointIdToClusterLevel.get(pa.getFirst());
			String clusterSecond=this.pointIdToClusterLevel.get(pa.getSecond());
			if(clusterFirst==null){
				clusterFirst=String.valueOf(pa.getFirst());
				this.pointIdToClusterLevel.put(pa.getFirst(), clusterFirst);
			}
			if(clusterSecond==null){
				clusterSecond=String.valueOf(pa.getSecond());
				this.pointIdToClusterLevel.put(pa.getSecond(), clusterSecond);
			}
			//这两个点已经在一个cluster中 则移动到下一个pair
			if(clusterFirst.contains(String.valueOf(pa.getSecond()))){
				continue;
			}
			//每个历史记录cluster用 "#" 隔开
			String s=mergeCluster(clusterFirst.split("#"),clusterSecond.split("#") );
			this.pointIdToClusterLevel.put(pa.getFirst(), clusterFirst.concat("#").concat(s));
			this.pointIdToClusterLevel.put(pa.getSecond(), clusterSecond.concat("#").concat(s));
			this.currentClusterNum--;
			if(this.currentClusterNum==this.finalClusterNum){
				break;
			}
		}
	}
	public Set<String> getResult(){
		Set<String> ret=new HashSet<String>();
		Set<Entry<Integer,String>> ens=this.pointIdToClusterLevel.entrySet();
		for(Entry<Integer,String> en:ens){
			String[] c=en.getValue().split(",");
			ret.add(c[c.length-1].replace("|", ""));
		}
		return ret;
	}
	//两个新合并的cluster用 “｜” 隔开,每个cluster中原有的元素用 “,”隔开
	private String mergeCluster(String[] f, String[] secnd){
		String lastF=f[f.length-1].replace("|", ",");
		String lasdS=secnd[secnd.length-1].replace("|", ",");
		String newCluster=lastF.concat("|").concat(lasdS);
		return newCluster;
	}
}
