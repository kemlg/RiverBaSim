package riverbasim;

import java.util.HashMap;
import java.util.Map;

import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;

public class FlowContext extends DefaultContext<RiverBlock> {
	private Map<String,RiverBlock>	blocks;
	
	public FlowContext()
	{
		super("FlowContext");

		System.out.println("FlowContext: building river flow context and projections");
		blocks = new HashMap<String,RiverBlock>();
		
		GeographyParameters<RiverBlock> geoParams = new GeographyParameters<RiverBlock>();
		Geography<RiverBlock> roadGeography = GeographyFactoryFinder
				.createGeographyFactory(null).createGeography("FlowGeography",
						this, geoParams);
	}

//	public void assignEdges(HashMap<RiverSection, RiverSection> flow) {
//		Iterator<Entry<RiverSection,RiverSection>> itMap = flow.entrySet().iterator();
//		while(itMap.hasNext()) {
//			Entry<RiverSection,RiverSection> entry = itMap.next();
//			RiverSection origin, destination;
//			origin = entry.getKey();
//			destination = entry.getValue();
//			if(origin != null && destination != null) {
//				RepastEdge<RiverSection> edge = new RepastEdge<RiverSection>(origin, destination, false);
//				network.addEdge(edge);
//			}
//		}
//	}
}
