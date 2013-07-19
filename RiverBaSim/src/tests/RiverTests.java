package tests;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import datamodel.Pollutants;
import datamodel.River;
import datamodel.WaterMass;

/**
 * 
 */

/**
 * @author Luis
 *
 */
public class RiverTests {

	private River river;
	private static final Logger log =Logger.getLogger(RiverTests.class.getName());
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.river = new River(5, 1000);
	}
	
	@Test
	public void testFlow(){
		log.info("Starting pollution");
		Pollutants pol = new Pollutants(100, 30, 20, 5, 5);
		for(int i=0; i<5;i++){
			log.info(this.river);
			this.river.flowWater(new WaterMass(1000, pol));
		}
		log.info(this.river);
		//this.river.setWaterInSection(2, new WaterMass(1, new Pollutants(120, 30, 20, 5, 5)));
		//log.info(this.river);
	}

}
