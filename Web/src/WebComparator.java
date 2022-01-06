
import java.util.Comparator;

public class WebComparator implements Comparator<Keyword>{
	@Override
	public int compare(Keyword o1, Keyword o2){
		if(o1==null || o2==null) throw new NullPointerException();
		//1. compare
		if(o1.count < o2.count) {
			return -1;
		}else if(o1.count > o2.count) {
			return 1;
		}
		return 0;
	}
	
}