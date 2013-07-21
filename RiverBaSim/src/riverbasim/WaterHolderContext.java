package riverbasim;

import java.util.HashMap;
import java.util.Iterator;

import org.jgrapht.graph.Multigraph;

import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;
import repast.simphony.util.collections.IndexedIterable;

public class WaterHolderContext extends DefaultContext<WaterHolder> {
	private Geography<WaterHolder> waterHolderGeography;
	private Multigraph<RiverSection, RiverBlock> graph;
	private HashMap<RiverSection, RiverSection> flow;

	public WaterHolderContext()
	{
		super("WaterHolderContext"); // Very important otherwise repast complains

		System.out.println("WaterHolderContext: Building WaterHolder context");
		/* Create a Geography to store junctions in spatially */
		GeographyParameters<WaterHolder> geoParams = new GeographyParameters<WaterHolder>();
		waterHolderGeography = GeographyFactoryFinder
				.createGeographyFactory(null).createGeography("WaterHolderGeography",
						this, geoParams);
		System.out.println("Created WaterHolderGeography");
	}

	public void createSubContexts()
	{
		WaterPlantContext wpc = new WaterPlantContext();
		this.addSubContext(wpc);
		RiverContext rc = new RiverContext();
		this.addSubContext(rc);
		// here we should place:
		// IndustryContext ic = new IndustryContext();
		// this.addSubContext(ic);
		// ic.setIndustries(rc.getIndustries());
		
		// getWaterPlants returns the set of WWTP that were randomly selected from the RiverSections that compose the river
		wpc.setWaterPlants(rc.getWaterPlants());


		IndexedIterable<RiverSection> it = rc.getObjects(RiverSection.class);
		Iterator<RiverSection> ite = it.iterator();
		while(ite.hasNext()) {
			RiverSection r = ite.next();
			System.out.println("Adding to WaterHolderContext: " + r);
			this.add(r);
			waterHolderGeography.move(r, ContextCreator.getRiverGeography().getGeometry(r));
		}
		
		IndexedIterable<WaterPlant> itw = wpc.getObjects(WaterPlant.class);
		Iterator<WaterPlant> itwe = itw.iterator();
		while(itwe.hasNext()) {
			WaterPlant w = itwe.next();
			System.out.println("Adding to WaterHolderContext: " + w);
			this.add(w);
			waterHolderGeography.move(w, ContextCreator.getWaterPlantGeography().getGeometry(w));
		}
		
		// getIndustries() returns the set of Industries that were placed near the WWTP and, therefore, they are associated to them in their RiverSection Location property
		// wpc.setIndustries(rc.getIndustries());
		/*
		 IndexedIterable<Industry> iti = rc.getObjects(Industry.class);
		 Iterator<Industry> itie = it.iterator();
		 while(ite.hasNext()) {
			Industry i = itie.next();
			System.out.println("adding " + i);
			this.add(i);
			waterHolderGeography.move(i, ContextCreator.getIndustryGeography().getGeometry(i));
		}
		 */
		
		
		System.out.println(this.getAgentTypes());
		System.out.println(waterHolderGeography.getAllObjects());
	}
}
