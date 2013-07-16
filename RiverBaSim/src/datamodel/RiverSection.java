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

/**
 * This class represents a section of the river basin. It basically takes care 
 * of managing the income of different water masses and merging them.
 * @author Luis
 *
 */
public class RiverSection {
	private WaterMass currentWater;
	
	public RiverSection(double waterPerSection){
		this.currentWater = new WaterMass(waterPerSection);
	}
	
	public void dumpWater(WaterMass dumpedWater){
		this.currentWater.mergeWith(dumpedWater);
	}
	
	public WaterMass getCurrentWater(){
		return this.currentWater;
	}

	public void setWater(WaterMass waterToMove) {
		// TODO Auto-generated method stub
		this.currentWater = new WaterMass(waterToMove.getVolume(), waterToMove.getPollutants());
	}
	
	public String toString(){
		return "River section: "+this.currentWater.toString();
	}
	public void depolluteSection(double d, double e, double f, double g, double h){
		this.currentWater.depolluteWaterMass(d, e, f, g, h);
	}
}
