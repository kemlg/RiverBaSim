package riverbasim;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.dbf.DbaseFileReader.Row;

import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;
import repast.simphony.space.gis.ShapefileLoader;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class RiverBaSimContext extends DefaultContext<RiverSection> {
	public RiverBaSimContext() {
		super("RiverBaSimContext"); // must match name in model.score
		GeographyParameters<RiverSection> geoParams = new GeographyParameters<RiverSection>();
		Geography<RiverSection> riverGeography = GeographyFactoryFinder
				.createGeographyFactory(null).createGeography("RiverGeography",
						this, geoParams);
		System.out.println("Created RiverGeography");

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
		int counter = 0;
		while (counter < 200 && riverLoader.hasNext()) {
			riverLoader.next();
			counter++;
		}

		for (RiverSection p : riverGeography.getAllObjects()) {
			Geometry geom = riverGeography.getGeometry(p);
			Coordinate coord = geom.getCoordinate();
			System.out.println(p.agentID + " is at: (" + coord.x + ","
					+ coord.y + ")");
		}
	}

	public static void main(String args[]) {
		new RiverBaSimContext();
	}
}
