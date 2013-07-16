/**
 * 
 */
package datamodel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Luis
 *
 */
public class Pollutants {
	private ArrayList<Pollutant> pollutants;
	
	public Pollutants(float solidConcentration, float BODConcentration, float CODConcentration, float NtConcentration, float PtConcentration){
		pollutants = new ArrayList<Pollutant>();
		pollutants.add(new Pollutant("Solid", solidConcentration));
		pollutants.add(new Pollutant("BOD", BODConcentration));
		pollutants.add(new Pollutant("COD", CODConcentration));
		pollutants.add(new Pollutant("Nt", NtConcentration));
		pollutants.add(new Pollutant("Pt", PtConcentration));
	}

	public Pollutants(){
		pollutants = new ArrayList<Pollutant>();
		pollutants.add(new Pollutant("Solid", 0));
		pollutants.add(new Pollutant("BOD", 0));
		pollutants.add(new Pollutant("COD", 0));
		pollutants.add(new Pollutant("Nt", 0));
		pollutants.add(new Pollutant("Pt", 0));
	}
	
	
	public ArrayList<Pollutant> getPollutants(){
		return pollutants;
	}
	
	public void mergePollutants(float thisVolume, float otherVolume, Pollutants other){
		Iterator<Pollutant> otherPollutants  = other.pollutants.iterator();
		int index = 0;
		while (otherPollutants.hasNext()){
			Pollutant otherPollutant = otherPollutants.next();
			Pollutant thisPollutant = this.pollutants.get(index);
			
			// We need to know the exact amount of each pollutant from each water mass
			float amountOtherPollutant = otherPollutant.getConcentration()*otherVolume;
			float amountThisPollutant = thisPollutant.getConcentration()*thisVolume;
			
			// We calculate the new concentration
			thisPollutant.setConcentration((amountOtherPollutant+amountThisPollutant)/(otherVolume+thisVolume));
			
			// And replace the old pollutant for the new pollutant in the merged water mass
			this.pollutants.set(index, thisPollutant);
			index++;
		}
	}
	
	public void depollute(float percentageSolid, float percentageBOD, float percentageCOD, float percentageNt, float percentagePt){
		try {
			this.pollutants.get(0).reducePercentageConcentration(percentageSolid);
			this.pollutants.get(1).reducePercentageConcentration(percentageBOD);
			this.pollutants.get(2).reducePercentageConcentration(percentageCOD);
			this.pollutants.get(3).reducePercentageConcentration(percentageNt);
			this.pollutants.get(4).reducePercentageConcentration(percentagePt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		String result ="";
		Iterator<Pollutant> pollutantsItr  = this.pollutants.iterator();
		while (pollutantsItr.hasNext()){
			result +=pollutantsItr.next().toString();
		}
		return result;
	}
}
