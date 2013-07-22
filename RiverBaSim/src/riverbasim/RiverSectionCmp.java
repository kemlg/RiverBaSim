package riverbasim;

import java.util.Comparator;

public class RiverSectionCmp implements Comparator<RiverSection>{

	public int compare(RiverSection arg0, RiverSection arg1) {
		// return -1 if `this` is less than `o`
	     //         0 if `this` is equal to `o`
	     //         1 of `this` is greater than `o`
		//String riverID0 = arg0.agentID.substring(0, arg0.getClass().getName().length()+1);
		//String riverID1 = arg1.agentID.substring(0, arg1.getClass().getName().length()+1);
		String riverID0 = arg0.agentID.substring("RiverSection ".length(), arg0.agentID.length());
		String riverID1 = arg1.agentID.substring("RiverSection ".length(), arg1.agentID.length());
		return Long.valueOf(riverID0).compareTo(Long.valueOf(riverID1));
	}

}
