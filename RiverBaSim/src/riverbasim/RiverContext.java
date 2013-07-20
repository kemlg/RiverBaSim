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
import java.util.Random;
import java.util.TreeMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.dbf.DbaseFileReader.Row;

import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.context.space.graph.NetworkFactory;
import repast.simphony.context.space.graph.NetworkFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;
import repast.simphony.space.gis.ShapefileLoader;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiLineString;

public class RiverContext extends DefaultContext<RiverSection>
{
	private TreeMap<String,RiverSection>	map;
	private HashSet<RiverSection> setBesos;
	private HashMap<RiverSection, RiverSection> flow;
	private HashSet<WaterPlant> waterPlants;
	private Geography<RiverSection> riverGeography;
	
	public HashSet<WaterPlant> getWaterPlants() {
		return waterPlants;
	}

	public void setWaterPlants(HashSet<WaterPlant> waterPlants) {
		this.waterPlants = waterPlants;
	}

	public HashMap<RiverSection, RiverSection> getFlow() {
		return flow;
	}

	public void setFlow(HashMap<RiverSection, RiverSection> flow) {
		this.flow = flow;
	}

	public RiverContext()
	{
		super("RiverContext");

		System.out.println("RiverContext building river section context and projections");

		/* Create a Network projection for the road network */
		NetworkFactory netFac = NetworkFactoryFinder
				.createNetworkFactory(new HashMap<String, Object>());
		netFac.createNetwork("FlowNetwork", this, false);

		GridFactoryFinder.createGridFactory(null).createGrid("Grid2D", this,
				new GridBuilderParameters(new repast.simphony.space.grid.WrapAroundBorders(), 
                new RandomGridAdder(), true, 100, 100)); 

		/* Create a Geography to store junctions in spatially */
		GeographyParameters<RiverSection> geoParams = new GeographyParameters<RiverSection>();
		riverGeography = GeographyFactoryFinder
				.createGeographyFactory(null).createGeography("RiverGeography",
						this, geoParams);
		System.out.println("Created RiverGeography");
		
		map = new TreeMap<String,RiverSection>();

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
				System.out.println(row);
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

		setBesos = new HashSet<RiverSection>();
		for (RiverSection p : riverGeography.getAllObjects()) {
			Geometry geom = riverGeography.getGeometry(p);
			Coordinate coord = geom.getCoordinate();
			MultiLineString line = (MultiLineString)geom;
			if(p.getNom().startsWith("el Besòs")) {
				setBesos.add(p);
				System.out.println(p.getNom() + " is at: (" + coord.x + ","
						+ coord.y + ") [size of set: " + setBesos.size() + "]");
			} else {
				this.remove(p);
			}
		}
		
		flow = new HashMap<RiverSection,RiverSection>();
		HashSet<RiverSection> tempBesos = new HashSet<RiverSection>();
		tempBesos.addAll(setBesos);
		Iterator<RiverSection> it1, it2;
		it1 = setBesos.iterator();
		RiverSection p1 = null;
		if (it1.hasNext()) {
			p1 = it1.next();
		}
		waterPlants = new HashSet<WaterPlant>();
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
					flow.put(p1, nearest);
					if (it1.hasNext()) {
						p1 = it1.next();
					} else {
						p1 = null;
					}
				}
			}
		}
	}
	
	public static void main(String args[]) {
		File selectedFile = new File("/Users/sergio/Downloads/spain-latest/buildings.dbf");
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
