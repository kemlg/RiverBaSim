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
	
	public Pollutants(double solidConcentration, double BODConcentration, double CODConcentration, double NtConcentration, double PtConcentration){
		this.pollutants = new ArrayList<Pollutant>();
		this.pollutants.add(new Pollutant("Solid", solidConcentration));
		this.pollutants.add(new Pollutant("BOD", BODConcentration));
		this.pollutants.add(new Pollutant("COD", CODConcentration));
		this.pollutants.add(new Pollutant("Nt", NtConcentration));
		this.pollutants.add(new Pollutant("Pt", PtConcentration));
	}

	public Pollutants(){
		this.pollutants = new ArrayList<Pollutant>();
		this.pollutants.add(new Pollutant("Solid", 0));
		this.pollutants.add(new Pollutant("BOD", 0));
		this.pollutants.add(new Pollutant("COD", 0));
		this.pollutants.add(new Pollutant("Nt", 0));
		this.pollutants.add(new Pollutant("Pt", 0));
	}
	
	public Pollutants (Pollutants pollutantsToCopy){
		this.pollutants = new ArrayList<Pollutant>();
		Iterator<Pollutant> itr = pollutantsToCopy.getPollutants().iterator();
		while (itr.hasNext()){
			Pollutant pollutant = itr.next();
			this.pollutants.add(new Pollutant(pollutant));
			
		}
	
	}
	
	public ArrayList<Pollutant> getPollutants(){
		return pollutants;
	}
	
	
	public void setPollutants(Pollutants newPollutants){
		for (int index=0; index<this.pollutants.size(); index++){
			this.pollutants.get(index).setConcentration(newPollutants.getPollutants().get(index).getConcentration());
		}
	}
	public void mergePollutants(double d, double e, Pollutants other){
		Iterator<Pollutant> otherPollutants  = other.pollutants.iterator();
		int index = 0;
		while (otherPollutants.hasNext()){
			Pollutant otherPollutant = otherPollutants.next();
			Pollutant thisPollutant = this.pollutants.get(index);
			
			// We need to know the exact amount of each pollutant from each water mass
			double amountOtherPollutant = otherPollutant.getConcentration()*e;
			double amountThisPollutant = thisPollutant.getConcentration()*d;
			
			// We calculate the new concentration
			thisPollutant.setConcentration((amountOtherPollutant+amountThisPollutant)/(e+d));
			
			// And replace the old pollutant for the new pollutant in the merged water mass
			this.pollutants.set(index, thisPollutant);
			index++;
		}
	}
	
	public void depollute(double percentageSolid, double percentageBOD, double percentageCOD, double percentageNt, double percentagePt){
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
