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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class represents the river basin, which is composed of an ordered list of
 * river sections. It takes care of moving water from one section to the next one, and
 * introducing fresh water into the first section.
 * @author Luis
 *
 */
public class River {
	private List<RiverSection> river;
	@SuppressWarnings("unused")
	private static final Logger log =Logger.getLogger(River.class.getName());
	
	public River(int numSections, float totalWaterInRiver){
		this.setRiver(new ArrayList<RiverSection>());
		double waterPerSection = totalWaterInRiver/numSections;
		for (int i=0; i<numSections;i++){
			this.river.add(new RiverSection(waterPerSection));
		}
	}
	public void setRiver(List<RiverSection> river) {
		this.river = river;
	}
	public List<RiverSection> getRiver() {
		return river;
	}
	
	public void flowWater(WaterMass waterToIntroduce){
		WaterMass waterToMove = waterToIntroduce;
		for (RiverSection currentSection: this.river){
			WaterMass currentWater = new WaterMass(currentSection.getCurrentWater());
			currentSection.setWater(waterToMove);
			waterToMove = currentWater;
		}
		this.selfCleaningProcessWholeRiver();
	}
	
	private void selfCleaningProcessWholeRiver() {
		for(RiverSection section : this.river){
			section.depolluteSection(0.4, 0.90, 0.92, 0.98, 0.97);
		}
	}
	public String toString(){
		String result = "River information: \n";
		Iterator<RiverSection> itr = this.river.iterator();
		while (itr.hasNext()){
			result+=itr.next().toString();
		}
		return result;
	}
	
	public void dumpWaterInSection(int section, WaterMass dumpedWater){
		this.river.get(section).dumpWater(dumpedWater);
	}
	public void setWaterInSection(int section, WaterMass dumpedWater){
		RiverSection riverSection = this.river.get(section);
		riverSection.setWater(dumpedWater);
		this.river.set(section, riverSection);
	}
	
}