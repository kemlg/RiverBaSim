/*******************************************************************************
 * Copyright (c) 2013 RiverBaSim - River Basin scenario Simulator    	        
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Luis Oliva - Created class and main functionalities
 ******************************************************************************/


import org.junit.Test;
import org.junit.Before;
import org.apache.log4j.Logger;


import datamodel.Pollutants;
import datamodel.WaterMass;

/**
 * @author Luis
 *
 */
public class PollutantTests{
	
	private WaterMass cleanWater, pollutedWater;
	private static final Logger log =Logger.getLogger(PollutantTests.class.getName());;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		
		this.cleanWater = new WaterMass(1);
		Pollutants pollutants = new Pollutants(120, 30, 20, 5, 5);
		this.pollutedWater = new WaterMass(10, pollutants);
	}
	@Test
	public void testMergingWater(){
		log.info("Before merging: \n"+cleanWater.toString());
		cleanWater.mergeWith(pollutedWater);
		log.info("After merging: \n"+cleanWater.toString());
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
	}

}
