package ada.ml.tfIdf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/**
 * @author zhouxc
 * 2014-04-15 11:49:30 create
 * 
 * **/
public class TfIdfGenerator {
	private int allDocCount=0;
	private Map<String,Map<String,Double>> termToDocIdToTf=new HashMap<String,Map<String,Double>>();
	private Set<String> termSet=new HashSet<String>();
	private Map<String,Integer> termToIndex=new HashMap<String,Integer>();
	public void addDoc(String docId,Map<String,Integer> tfMap,double wordNumber){
		Set<Entry<String,Integer>> ens=tfMap.entrySet();
		for(Entry<String,Integer> en:ens){
			allDocCount++;
			String term=en.getKey();
			Map<String,Double> docIds=termToDocIdToTf.get(term);
			if(docIds==null){
				docIds=new HashMap<String,Double>();
				termToDocIdToTf.put(term, docIds );
			}
			double tf=en.getValue()/wordNumber;
			docIds.put(docId, tf);
			termSet.add(term);
		}
	}
	public void finishAddDoc(){
		int index=0;
		for(String term:termSet){
			termToIndex.put(term,index );
			index++;
		}
		
	}
	public double[] getTfIdf(String docId,Set<String> termSet){
		double[] tfidf=new double[termToDocIdToTf.size()];
		for(String term:termSet){
			int index=termToIndex.get(term);
			double docHasTerm=termToDocIdToTf.get(term).size();
			double idf=Math.log(allDocCount/(docHasTerm+1));
			double tf=termToDocIdToTf.get(term).get(docId);
			tfidf[index]=tf*idf;
		}
		return tfidf;
	}
}
