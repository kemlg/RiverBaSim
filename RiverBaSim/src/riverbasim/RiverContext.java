package riverbasim;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.dbf.DbaseFileReader.Row;

import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.context.space.graph.NetworkFactory;
import repast.simphony.context.space.graph.NetworkFactoryFinder;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;
import repast.simphony.space.gis.ShapefileLoader;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;





public class RiverContext extends DefaultContext<RiverSection>
{
	private TreeSet<RiverSection> setBesos;
	private LinkedHashMap<RiverSection, RiverSection> flow;
	private HashSet<WaterPlant> waterPlants;
	private HashSet<Industry> industry;
	private Geography<RiverSection> riverGeography;
	
	public HashSet<WaterPlant> getWaterPlants() {
		return waterPlants;
	}

	public void setIndustry(HashSet<Industry> industry) {
		this.industry = industry;
	}

	public HashSet<Industry> getIndustry() {
		return industry;
	}

	public void setWaterPlants(HashSet<Industry> industry) {
		this.industry = industry;
	}
	
	public LinkedHashMap<RiverSection, RiverSection> getFlow() {
		return flow;
	}

	public void setFlow(LinkedHashMap<RiverSection, RiverSection> flow) {
		this.flow = flow;
	}

	@SuppressWarnings("deprecation")
	public RiverContext()	{
		super("RiverContext");

		System.out.println("RiverContext building river section context and projections");

		/* Create a Network projection for the road network */
		NetworkFactory netFac = NetworkFactoryFinder
				.createNetworkFactory(new HashMap<String, Object>());
		netFac.createNetwork("FlowNetwork", this, false);
/*
		GridFactoryFinder.createGridFactory(null).createGrid("Grid2D", this,
				new GridBuilderParameters(new repast.simphony.space.grid.WrapAroundBorders(), 
                new RandomGridAdder(), true, 100, 100)); 
*/
		/* Create a Geography to store junctions in spatially */
		GeographyParameters<RiverSection> geoParams = new GeographyParameters<RiverSection>();
		riverGeography = GeographyFactoryFinder
				.createGeographyFactory(null).createGeography("RiverGeography",
						this, geoParams);
		System.out.println("Created RiverGeography");
		
		new TreeMap<String,RiverSection>();

		File selectedFile = new File("./contrib/x_besos/x_besos.dbf");
		FileChannel in = null;
		try {
			in = new FileInputStream(selectedFile).getChannel();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(getClass().getName()).log(Level.DEBUG, null, ex);
		}
		DbaseFileReader r = null;
		try {
			r = new DbaseFileReader(in, false, Charset.forName("ISO-8859-1"));
		} catch (IOException ex1) {
			Logger.getLogger(getClass().getName()).log(Level.DEBUG, null, ex1);
		}

		Object[] fields = new Object[r.getHeader().getNumFields()];
		while (r.hasNext()) {
			try {
				r.readEntry(fields);
				Row row = r.readRow();
				//System.out.println(row);
			} catch (IOException ex1) {
				Logger.getLogger(getClass().getName()).log(Level.DEBUG, null,
						ex1);
			}
		}
		try {
			r.close();
		} catch (IOException ex1) {
			Logger.getLogger(getClass().getName()).log(Level.DEBUG, null, ex1);
		}

		File shapefile = null;
		ShapefileLoader<RiverSection> riverLoader = null;
		try {
			shapefile = new File("./contrib/x_besos/x_besos.shp");
			riverLoader = new ShapefileLoader<RiverSection>(RiverSection.class,
					shapefile.toURL(), riverGeography, this);
		} catch (java.net.MalformedURLException e) {
			e.printStackTrace();
		}
		while (riverLoader.hasNext()) {
			riverLoader.next();
		}

		//setBesos = new HashSet<RiverSection>();
		setBesos = new TreeSet<RiverSection>(new RiverSectionCmp());
		for (RiverSection p : riverGeography.getAllObjects()) {
			Geometry geom = riverGeography.getGeometry(p);
			Coordinate coord = geom.getCoordinate();
			if(p.getNom().startsWith("el Besòs")) {
				setBesos.add(p);
				//System.out.println(p.agentID + " is at: (" + coord.x + ","+ coord.y + ") [size of set: " + setBesos.size() + "]");
			} else {
				this.remove(p);
			}
		}
		
		flow = new LinkedHashMap<RiverSection,RiverSection>();
		//TreeSet<RiverSection> tempBesos = new TreeSet<RiverSection>(new RiverSectionCmp());
		//tempBesos.addAll(setBesos);
		Iterator<RiverSection> it1;
		it1 = setBesos.iterator();
	
		RiverSection p1 = null, p2 = null;
		
		
		waterPlants = new HashSet<WaterPlant>();
		industry  = new HashSet<Industry>();
		if (it1.hasNext()) {
			p1 = it1.next();
		}
		GeometryFactory fac = new GeometryFactory();
		while (it1.hasNext()){
			p2 = it1.next();
			if (new Random().nextDouble() < 0.1) {
				// Assigned as WWTP
				WaterPlant wp = new WaterPlant();
				wp.setRiverSectionLocation(p1);
				waterPlants.add(wp);
				Geometry geoP2 = riverGeography.getGeometry(p2);
				
				ContextCreator.getWaterPlantGeography().move(wp, geoP2);
				// Here we should add industries
				for (int i=0; i<10; i++){
					Industry ind = new Industry();
					ind.setAssignedWWTP(wp);
					
					Coordinate coord = new Coordinate(); 
					coord.setCoordinate(geoP2.getCentroid().getCoordinate());
					coord.x +=0.003*i;
					coord.y +=0.003*i;
					Point geom = fac.createPoint(coord);
					ind.setLocation(geom);
					industry.add(ind);
				}

				this.remove(p2);
			}
			else{
				this.flow.put(p1, p2);
				p1 = p2;
			}
		}
		/*
		while (p1 != null) {
			
			Geometry geom1 = riverGeography.getGeometry(p1);
			if (geom1 == null) {
				if (it1.hasNext()) {
					p1 = it1.next();
				} else {
					p1 = null;
				}
			} else {
				Coordinate coord1 = geom1.getCoordinate();
				MultiLineString line1 = (MultiLineString) geom1;
				tempBesos.remove(p1);
				it2 = tempBesos.iterator();
				double min = Double.POSITIVE_INFINITY;
				RiverSection nearest = null;
				while (it2.hasNext()) {
					RiverSection p2 = it2.next();
					Geometry geom2 = riverGeography.getGeometry(p2);
					Coordinate coord2 = geom2.getCoordinate();
					MultiLineString line2 = (MultiLineString) geom2;
					double candidate = coord1.distance(coord2);
					/*if (p1.agentID.equals("RiverSection 5") || p1.agentID.equals("RiverSection 9")){
					System.out.println(p1.agentID + " is at: (" + coord1.x + ","
							+ coord1.y + ")"+ p2.agentID + " is at: (" + coord2.x + ","
							+ coord2.y + ") Distance: "+candidate);
					}
					if (candidate < min) {
						nearest = p2;
						min = candidate;
					}
				}
				if (new Random().nextDouble() < 0.1) {
					// Assigned as WWTP
					WaterPlant wp = new WaterPlant();
					wp.setRiverSectionLocation(p1);
					waterPlants.add(wp);
					ContextCreator.getWaterPlantGeography().move(wp,
							riverGeography.getGeometry(nearest));
					tempBesos.remove(nearest);
					this.remove(nearest);
				} else {
					this.flow.put(p1, nearest);
					if (it1.hasNext()) {
						p1 = it1.next();
					} else {
						p1 = null;
					}
				}
			}
		}
		*/
		System.out.println("Flow: "+this.flow.toString());
	}
	
	public static void main(String args[]) {
		File selectedFile = new File("./contrib/spain-latest/buildings.dbf");
		FileChannel in = null;
		try {
			in = new FileInputStream(selectedFile).getChannel();
		} catch (FileNotFoundException ex) {
		}
		DbaseFileReader r = null;
		try {
			r = new DbaseFileReader(in, false, Charset.forName("ISO-8859-1"));
		} catch (IOException ex1) {
		}

		Object[] fields = new Object[r.getHeader().getNumFields()];
		while (r.hasNext()) {
			try {
				r.readEntry(fields);
				Row row = r.readRow();
				String type = (String) row.read(2);
				if(type.toLowerCase().contains("water")) {
					System.out.println(type);
				}
			} catch (IOException ex1) {
			}
		}
		try {
			r.close();
		} catch (IOException ex1) {
		}
	}
}
