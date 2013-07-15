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

package datamodel;

import java.util.Iterator;
import java.util.Set;

/** @author Luis Oliva  **/
public class WaterMass {

	private float volume;
	private Set<Pollutant> pollutants;
	
	public WaterMass(){
		// Setting up the list of pollutants that are considered in the scenario
	}
	public void setPollutant(Pollutant p){
		//pollutants.
	}
	
	public float getVolume(){
		return this.volume;
	}
	public void mergeWith(WaterMass other){
		this.volume += other.getVolume();
		
		Iterator<Pollutant> otherPollutants  = other.pollutants.iterator();
		while (otherPollutants.hasNext()){
			Pollutant otherPollutant = otherPollutants.next();
			if (pollutants.contains(o))
		}
	}
}
