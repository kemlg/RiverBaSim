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

/** @author Luis Oliva  **/
public class WaterMass {

	/**
	 * Volume of water (m3)
	 */
	private float volume;
	/**
	 * List of pollutants in the water mass. It is a fixed list of pollutants (Solid, BOD, COD, Nt and Pt)
	 * These pollutants are expressed in terms of concentration (g/m3)
	 */
	//private ArrayList<Pollutant> pollutants;
	private Pollutants pollutants;
	
	/**
	 * Creation of a fresh water mass, free of pollutants
	 * @param volume
	 */
	public WaterMass(float volume){
		this.volume = volume;
		// Setting up the list of pollutants that are considered in the scenario
		pollutants = new Pollutants();
	}
	
	public WaterMass(float volume, Pollutants pollutants){
		this.volume = volume;
		this.pollutants = pollutants;
	}
	
	/**
	 * Returns the amount of water in the water mass
	 * @return float volume
	 */
	public float getVolume(){
		return this.volume;
	}
	/**
	 * Returns the list of pollutants in the water mass
	 * @return Pollutants pollutants
	 */
	public Pollutants getPollutants(){
		return this.pollutants;
	}
	
	public void depolluteWaterMass(float percentageSolid, float percentageBOD, float percentageCOD, float percentageNt, float percentagePt){
		this.pollutants.depollute(percentageSolid, percentageBOD, percentageCOD, percentageNt, percentagePt);
	}

	

	
	/**
	 * Merges two different Water masses. Water volume is added pollutants have to be diluted as well:
	 * 	- If the pollutant is not already present in both water masses, then its concentration has to be recalculated for the new water volume
	 *  - If the pollutant is already present in both water masses, then we have to extract the amount of each pollutant in each water volume, 
	 *    add it up and calculate the new concentration according to the new water volume 
	 * @param WaterMass other
	 */
	public void mergeWith(WaterMass other){
		// Merging pollutants
		this.pollutants.mergePollutants(this.volume, other.getVolume(), other.getPollutants());
		// We effectively merge both water masses
		this.volume += other.getVolume();
	}
	
	public String toString(){
		return "Water mass information: "+this.volume+" m3 of water with the following pollutants:\n"+this.pollutants.toString();
	}
}