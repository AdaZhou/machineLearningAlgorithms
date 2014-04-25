package ada.ml.common;
/**
 * @author zhouxc
 * 2014-04-13 01:29:30 create
 * 注意这里起名字是距离，也就是说数值越大，距离越大
 * **/
public interface DistanceCalculator {
	public double getDistance(Object p1, Object p2);
}
