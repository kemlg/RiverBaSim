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
 * @author Luis
 *
 */
public class Pollutant {

	private String name;
	private double concentration;
	private String units;
	
	public Pollutant(String name, double concentration){
		this.name = name;
		this.concentration = concentration;
		this.units = "g/m3";
	}
	
	public Pollutant(Pollutant pollutantToCopy){
		this.name          = pollutantToCopy.getName();
		this.units 		   = pollutantToCopy.getUnits();
		this.concentration = pollutantToCopy.getConcentration();
	}
	/**
	 * Returns pollutant name
	 * @return String name
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * Returns pollutant concentration
	 * @return double concentration
	 */
	public double getConcentration(){
		return this.concentration;
	}
	/**
	 * Returns pollutant units
	 * @return String units
	 */
	public String getUnits(){
		return this.units;
	}
	
	/**
	 * Reduces the amount of pollutant concentration according to a given absolute value
	 * E.g., 0.2 reduces the amount of pollutant by 0.2 units (we have 100 units, it is reduced to 99.8)
	 * @param float absoluteConcentrationReduced
	 */
	public void reduceAbsoluteConcentration(float absoluteConcentrationReduced){
		if (this.concentration<=absoluteConcentrationReduced) this.concentration = 0;
		else this.concentration -= absoluteConcentrationReduced;
	}
	/**
	 * Reduces the amount of pollutant concentration according to a given percentage (0-1)
	 * E.g., 0.2 reduces the concentration of pollutant by a 20% (we have 100 g/m3, it is reduced to 80 g/m3)
	 * @param float percentageConcentrationReduced
	 * @throws Exception 
	 */
	public void reducePercentageConcentration(double percentageConcentrationReduced) throws Exception{
		if (percentageConcentrationReduced>=0 && percentageConcentrationReduced<=1){
			this.concentration *=percentageConcentrationReduced;
		}
		else{
			throw new Exception("Invalid percentage value "+percentageConcentrationReduced);
		}
	}

	public void setConcentration(double newConcentration){
		this.concentration=newConcentration;
	}


	
	/**
	 * A pollutant is equal to another one if they are the same kind of pollutant (same Name) and 
	 * they have the same magnitude units (same Units).
	 * @param Pollutant other 
	 * @return boolean
	 */
	public boolean equals(Pollutant other){
		return (this.name.equalsIgnoreCase(other.getName())&&this.units.equalsIgnoreCase(other.getUnits()));
	}
	/**
	 * To display pollutant information: Amount+units+name
	 */
	public String toString(){
		return "Pollutant information: "+this.concentration+" "+this.units+" of "+this.name+"\n";
	}
}