/**
 * 
 * This file was automatically generated by the Repast Simphony Agent Editor.
 * Please see http://repast.sourceforge.net/ for details.
 * 
 */

/**
 *
 * Set the package name.
 *
 */
package bin.riverbasim

/**
 *
 * Import the needed packages.
 *
 */
import java.io.*
import java.math.*
import java.util.*
import javax.measure.unit.*
import org.jscience.mathematics.number.*
import org.jscience.mathematics.vector.*
import org.jscience.physics.amount.*
import repast.simphony.adaptation.neural.*
import repast.simphony.adaptation.regression.*
import repast.simphony.context.*
import repast.simphony.context.space.continuous.*
import repast.simphony.context.space.gis.*
import repast.simphony.context.space.graph.*
import repast.simphony.context.space.grid.*
import repast.simphony.engine.environment.*
import repast.simphony.engine.schedule.*
import repast.simphony.engine.watcher.*
import repast.simphony.groovy.math.*
import repast.simphony.integration.*
import repast.simphony.matlab.link.*
import repast.simphony.query.*
import repast.simphony.query.space.continuous.*
import repast.simphony.query.space.gis.*
import repast.simphony.query.space.graph.*
import repast.simphony.query.space.grid.*
import repast.simphony.query.space.projection.*
import repast.simphony.parameter.*
import repast.simphony.random.*
import repast.simphony.space.continuous.*
import repast.simphony.space.gis.*
import repast.simphony.space.graph.*
import repast.simphony.space.grid.*
import repast.simphony.space.projection.*
import repast.simphony.ui.probe.*
import repast.simphony.util.*
import simphony.util.messages.*
import static java.lang.Math.*
import static repast.simphony.essentials.RepastEssentials.*

/**
 *
 * Agent representing a Industry.
 *
 */
public class Industry  {

    /**
     *
     * Concentration of BOD
     * @field bodConcentration
     *
     */
    @Parameter (displayName = "BOD concentration (gr./m3)", usageName = "bodConcentration")
    public double getBodConcentration() {
        return bodConcentration
    }
    public void setBodConcentration(double newValue) {
        bodConcentration = newValue
    }
    public double bodConcentration = 350

    /**
     *
     * Concentration of Nt
     * @field ntConcentration
     *
     */
    @Parameter (displayName = "Nitrogen Total concentration", usageName = "ntConcentration")
    public double getNtConcentration() {
        return ntConcentration
    }
    public void setNtConcentration(double newValue) {
        ntConcentration = newValue
    }
    public double ntConcentration = 38

    /**
     *
     * Concentration of solids (MES)
     * @field solidConcentration
     *
     */
    @Parameter (displayName = "Solid concentration (gr/m3)", usageName = "solidConcentration")
    public double getSolidConcentration() {
        return solidConcentration
    }
    public void setSolidConcentration(double newValue) {
        solidConcentration = newValue
    }
    public double solidConcentration = 150

    /**
     *
     * Concentration of Phosphorus Total
     * @field ptConcentration
     *
     */
    @Parameter (displayName = "Phosphorus Total concentration", usageName = "ptConcentration")
    public double getPtConcentration() {
        return ptConcentration
    }
    public void setPtConcentration(double newValue) {
        ptConcentration = newValue
    }
    public double ptConcentration = 2

    /**
     *
     * Flow (amount) of water
     * @field amountWater
     *
     */
    @Parameter (displayName = "Amount of water (m3)", usageName = "amountWater")
    public double getAmountWater() {
        return amountWater
    }
    public void setAmountWater(double newValue) {
        amountWater = newValue
    }
    public double amountWater = 2000

    /**
     *
     * Concentration of COD
     * @field codConcentration
     *
     */
    @Parameter (displayName = "COD concentration (gr./m3)", usageName = "codConcentration")
    public double getCodConcentration() {
        return codConcentration
    }
    public void setCodConcentration(double newValue) {
        codConcentration = newValue
    }
    public double codConcentration = 540

    /**
     *
     * Assigned WWTP
     * @field assignedWWTP
     *
     */
    @Parameter (displayName = "Assigned WWTP where this Industry will send the wastewater", usageName = "assignedWWTP")
    public riverbasim.WaterPlant getAssignedWWTP() {
        return assignedWWTP
    }
    public void setAssignedWWTP(riverbasim.WaterPlant newValue) {
        assignedWWTP = newValue
    }
    public riverbasim.WaterPlant assignedWWTP = null

    /**
     *
     * This value is used to automatically generate agent identifiers.
     * @field serialVersionUID
     *
     */
    private static final long serialVersionUID = 1L

    /**
     *
     * This value is used to automatically generate agent identifiers.
     * @field agentIDCounter
     *
     */
    protected static long agentIDCounter = 1

    /**
     *
     * This value is the agent's identifier.
     * @field agentID
     *
     */
    protected String agentID = "Industry " + (agentIDCounter++)

    /**
     *
     * Dump wastewater
     * @method dumpWastewater
     *
     */
    @ScheduledMethod(
        start = 1d,
        interval = 2d,
        shuffle = false
    )
    public def dumpWastewater() {

        // Define the return value variable.
        def returnValue

        // Note the simulation time.
        def time = GetTickCountInTimeUnits()

        // Dump wastewater to WWTP
        assignedWWTP.mixIncomingWater(amountWater, solidConcentration, bodConcentration, codConcentration, ntConcentration, ptConcentration)
        // Return the results.
        return returnValue

    }

    /**
     *
     * This method provides a human-readable name for the agent.
     * @method toString
     *
     */
    @ProbeID()
    public String toString() {

        // Define the return value variable.
        def returnValue

        // Note the simulation time.
        def time = GetTickCountInTimeUnits()

        // Set the default agent identifier.
        returnValue = this.agentID
        // Return the results.
        return returnValue

    }


}

