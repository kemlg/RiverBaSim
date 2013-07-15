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
	private float amount;
	private String units;
	
	public Pollutant(String name, float amount, String units){
		this.name = name;
		this.amount = amount;
		this.units = units;
	}
	
	public String getName(){
		return this.name;
	}
	public float getAmount(){
		return this.amount;
	}
	public String getUnits(){
		return this.units;
	}
	
	
	public void reduceAbsoluteAmount(float absoluteAmountReduced){
		if (this.amount<=absoluteAmountReduced) this.amount = 0;
		else this.amount -= absoluteAmountReduced;
	}
	
	public void reducePercentageAmount(float percentageAmountReduced){
		
	}
	
	public float mergeWith(Pollutant other){
		return 0;
		
	}
}
