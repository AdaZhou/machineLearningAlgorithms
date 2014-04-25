/**
 * @author zhouxiaocao
 * 2014年4月24日 下午4:37:30 create
 */
package ada.ml.tfIdf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author zhouxc
 *
 */
public class TfIdfManager {
	private Map<String,TfIdfGenerator> map=new HashMap<String,TfIdfGenerator>();

	public void addDoc(String field, String docId,Map<String,Integer> tfMap,double wordNumber){
		if(!map.containsKey(field)){
			map.put(field, new TfIdfGenerator());
		}
		map.get(field).addDoc(docId, tfMap, wordNumber);
	}
	public void finishAddDoc(){
		Set<String> kset=map.keySet();
		for(String k:kset){
			map.get(k).finishAddDoc();
		}
	}
	public double[] getTfIdf(String fieldId,String docId){
		return map.get(fieldId).getTfIdf(docId);
	}
}
