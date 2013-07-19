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

package riverbasim;

import repast.simphony.parameter.StringConverter;

/**
 * @author Luis
 *
 */
public class WaterFeatureConverter implements StringConverter<WaterFeature> {
	public WaterFeature fromString(String strRep) {
		strRep = strRep.substring(1, strRep.length()-2);
		String[] pairs = strRep.split("\\, ");
		WaterFeature result = new WaterFeature();
		for(int i=0; i<pairs.length;i++){
			int index = pairs[i].indexOf("=");
		    String tick = pairs[i].substring(0, index);
		    String amount = pairs[i].substring(index + 1, pairs[i].length());
		    result.put(Integer.parseInt(tick), Double.parseDouble(amount));
		}
		return result;
	}
	
	public String toString(WaterFeature obj) {
		return obj.getFeature().toString();
	}
}