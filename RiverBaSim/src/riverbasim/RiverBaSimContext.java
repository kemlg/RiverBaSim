package riverbasim;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;

import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;

public class RiverBaSimContext extends DefaultContext<Object> {
	private HashSet<RiverSection> setBesos;
	HashMap<RiverSection,RiverSection> flow = null;

	private Graph<RiverSection,RiverBlock>	graph;
	
	/**
	 * Constructs a CityContextContext and creates a Geography (called
	 * RoadNetworkGeography) which is part of this context.
	 */
	public RiverBaSimContext()
	{
		super("RiverBaSimContext"); // Very important otherwise repast complains

		System.out.println("RiverBaSimContext: Building RiverBaSim context");

		/* Create a geography to display the sub-contexts of the city. */
		GeographyParameters<Object> geoParams = new GeographyParameters<Object>();
		GeographyFactoryFinder.createGeographyFactory(null).createGeography(
				"RiverBaSimGeography", this, geoParams);
	}

	public void createSubContexts()
	{
		WaterHolderContext whc = new WaterHolderContext();
		this.addSubContext(whc);
		whc.createSubContexts();
		this.addSubContext(new FlowContext());
		
		buildRiverFlow();
	}
	
	
	public void buildRiverFlow()
	{
		Geography<RiverBlock>	rbGeography;
		Geography<RiverSection>	rsGeography;
		RiverContext			riverContext;
		FlowContext				flowContext;
		
		graph = new Multigraph<RiverSection,RiverBlock>(RiverBlock.class);
		System.out.println("RiverBaSimContext: building river flow graph");
		
		rbGeography = (Geography<RiverBlock>)ContextCreator.getFlowGeography();
		rsGeography = (Geography<RiverSection>)ContextCreator.getRiverGeography();
		riverContext = (RiverContext)ContextCreator.getRiverContext();
		flowContext = (FlowContext)ContextCreator.getFlowContext();
		
		flow = riverContext.getFlow();
		Iterator<Entry<RiverSection,RiverSection>> itMap = flow.entrySet().iterator();
		while(itMap.hasNext()) {
			Entry<RiverSection,RiverSection> entry = itMap.next();
			RiverSection origin, destination;
			origin = entry.getKey();
			destination = entry.getValue();
			if(origin != null && destination != null) {
				ContextCreator.getFlowNetwork().addEdge(origin, destination);
				destination.setWatchedAgent(origin);
			}
		}	
	}
}
