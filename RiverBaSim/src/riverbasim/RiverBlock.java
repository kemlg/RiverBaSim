package riverbasim;

import repast.simphony.space.graph.RepastEdge;

public class RiverBlock extends RepastEdge<RiverSection>{

	public RiverBlock(RiverSection origin, RiverSection destination, boolean b) {
		super(origin, destination, b);
	}

}
