/**
 * @author zhouxiaocao
 * 2014年4月21日 上午9:44:14 create
 */
package ada.ml.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ada.ml.common.Point;


/**
 * @author zhouxc
 *保存了层次聚类每一层的聚类状态，聚类时不用输入聚类个数k，在获取时可以根据k值获取到相应的聚类状态。
 */
public class AgnesWithAnyK {
	private Point[] p=null;
	//层次对应于该层次聚类的状态
	private Map<Integer,Set<TreeNode>> kToCluster=new HashMap<Integer,Set<TreeNode>>();
	//由Point生成的 TreeNode
	private List<TreeNode> tns;
	public AgnesWithAnyK(Point[] p){
		this.p=p;
		tns=new ArrayList<TreeNode>();
		for(Point po:p){
			tns.add(new TreeNode(null,null,po));
		}
	}
	private static class NodePair implements Comparable<NodePair>{
		private TreeNode first;
		private TreeNode second;
		private double distance;
		public NodePair(TreeNode f,TreeNode s,double distance){
			this.first=f;
			this.second=s;
			this.distance=distance;
		}
		@Override
		public int compareTo(NodePair o) {
			// TODO Auto-generated method stub
			if(this.distance>o.distance){
				return 1;
			}else if(this.distance<o.distance){
				return -1;
			}else{
				return 0;
			}
		}
		public TreeNode getFirst() {
			return first;
		}
		public TreeNode getSecond() {
			return second;
		}
	}
	public static class TreeNode{
		private TreeNode lchild;
		private TreeNode rchild;
		private TreeNode parent;
		private Object value;
		private int len=1;
		public TreeNode(TreeNode l,TreeNode r, Object value){
			this.lchild=l;
			this.rchild=r;
			this.value=value;
		}
		public TreeNode getLchild() {
			return lchild;
		}
		public void setLchild(TreeNode lchild) {
			this.lchild = lchild;
		}
		public TreeNode getRchild() {
			return rchild;
		}
		public void setRchild(TreeNode rchild) {
			this.rchild = rchild;
		}
		public TreeNode getParent() {
			return parent;
		}
		public void setParent(TreeNode parent) {
			this.parent = parent;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public void getAllLeafNode(TreeNode node,List<TreeNode> result){
			TreeNode lc=node.getLchild();
			while(lc!=null){
				getAllLeafNode(lc,result);
			}
			TreeNode rc=node.getRchild();
			while(rc!=null){
				getAllLeafNode(rc,result);
			}
			result.add(node);
		}
		public TreeNode getTailNode(){
			TreeNode temp=this;
			while(temp.getParent()!=null){
				temp=temp.getParent();
			}
			return temp;
		}
		public static void addParentNode(TreeNode lchild,TreeNode rchild){
			//更新链的长度
			lchild.len=lchild.len+1;
			rchild.len=rchild.len+1;
			TreeNode parent=new TreeNode(lchild.getTailNode(),rchild.getTailNode(),null);
			lchild.getTailNode().setParent(parent);
			rchild.getTailNode().setParent(parent);
		}
		/**
		 * return true, if they have same parent or grandparent.
		 * 判断y型链表是否有交点，若存在交点返回交点
		 * **/
		public TreeNode getSameParent(TreeNode t){
			//将较长的链表指针从头部移动到与较短链表长度相等的位置
			int tLen=t.len;
			int lenMore=Math.abs(tLen-this.len);
			TreeNode tem1=this;
			TreeNode tem2=t;
			if(this.len<t.len){
				tem1=t;
				tem2=this;
			}
			for(int i=0;i<lenMore;i++){
				tem1=tem1.getParent();
			}
			int minlen=t.len<this.len?t.len:this.len;
			for(int i=0;i<minlen;i++){
				if(tem1==tem2){
					return tem1;
				}else{
					tem1=tem1.getParent();
					tem2=tem2.getParent();
				}
			}
			return null;
		}
	}
	private List<NodePair> getSortedPointPair(){
		List<NodePair> nps=new ArrayList<NodePair>(); 
		for(int i=0;i<p.length;i++){
			for(int j=i+1;j<p.length;j++){
				NodePair pair=new NodePair(tns.get(i),tns.get(j),p[i].calculateDistance(p[j]));
				nps.add(pair);
			}
		}
		Collections.sort(nps);
		return nps;
	}

	public void doCluster(){
		List<NodePair> sortedPair= getSortedPointPair();
		int clusternum=p.length; 
		for(NodePair p : sortedPair){
			TreeNode f=p.getFirst();
			TreeNode s=p.getSecond();
			TreeNode sameParent= f.getSameParent(s);
			//如果两个节点不具有最小公共父节点，则两个节点在不同的cluster中，否则在同一个cluster中
			if(sameParent==null){
				TreeNode.addParentNode(f, s);
				clusternum--;
				Set<TreeNode> tset=new HashSet<TreeNode>(); 
				for(TreeNode tn:tns){
					tset.add(tn.getTailNode());
				}
				this.kToCluster.put(clusternum, tset);
			}
		}
		
	}
	public Set<Point[]> getKClsuter(int k){
		Set<TreeNode> tns= this.kToCluster.get(k);
		Set<Point[]> ret=new HashSet<Point[]>();
		for(TreeNode n: tns){
			List<TreeNode> rel=new ArrayList<TreeNode>();
			n.getAllLeafNode(n, rel);
			Point[] ps=new Point[rel.size()];
			for(int i=0;i<rel.size();i++){
				ps[i]=(Point)rel.get(i).getValue();
			}
			ret.add(ps);
		}
		return ret;
	}
}
