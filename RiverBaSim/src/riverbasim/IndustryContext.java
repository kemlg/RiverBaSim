package riverbasim;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;

public class IndustryContext extends DefaultContext<Industry>{
	private TreeMap<String,Industry>	map;
	private HashSet<Industry> setBesos;
	private HashMap<Industry, WaterPlant> flow;
	private Geography<Industry> industryGeography;
	
	public HashMap<Industry, WaterPlant> getFlow() {
		return flow;
	}

	public void setFlow(HashMap<Industry, WaterPlant> flow) {
		this.flow = flow;
	}

	public IndustryContext()
	{
		super("IndustryContext");

		System.out.println("IndustryContext building river section context and projections");

	/* Create a Geography to store junctions in spatially */
		GeographyParameters<Industry> geoParams = new GeographyParameters<Industry>();
		industryGeography = GeographyFactoryFinder.createGeographyFactory(null).createGeography("IndustryGeography", this, geoParams);
		
		System.out.println("Created IndustryGeography");
		
		map = new TreeMap<String,Industry>();
	}
	
	public void setIndustry(Set<Industry> inds) {
		Iterator<Industry> it = inds.iterator();
		while(it.hasNext()) {
			Industry ind = it.next();
			System.out.println("Adding " + ind + " attached to "+ind.getAssignedWWTP().toString());
			this.add(ind);
		}
	}
		public Geography<Industry> getIndustryGeography() {
			return industryGeography;
		}

		public void setIndustries(HashSet<Industry> industry) {
			Iterator<Industry> it = industry.iterator();
			while(it.hasNext()) {
				Industry ind = it.next();
				System.out.println("Adding " + ind + " attached to "+ind.getAssignedWWTP().toString());
				this.add(ind);
				industryGeography.move(ind, ind.getLocation());
			}
			
		}
}
