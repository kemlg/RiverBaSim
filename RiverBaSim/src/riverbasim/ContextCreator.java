package riverbasim;

import java.io.File;
import java.io.IOException;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.ShortestPath;

public class ContextCreator implements ContextBuilder<Object>
{
	private static Context<Object> mainContext; // Useful to keep a reference to
												// the main context
	private static int agentID; // Used to generate unique agent ids

	/**
	 * Used to build the model, creating all subcontexts.
	 */
	public Context<Object> build(Context<Object> context)
	{		
		ContextCreator.mainContext = context;

		RiverBaSimContext rbContext = new RiverBaSimContext();
		context.addSubContext(rbContext);
		rbContext.createSubContexts();

		return context;
	}

	/**
	 * Creates a unique identifier for an agent. IDs are created in ascending
	 * order.
	 * 
	 * @return the unique identifier.
	 */
	public static int generateAgentID()
	{
		return ContextCreator.agentID++;
	}

	public static FlowContext getFlowContext()
	{
		return (FlowContext) mainContext.findContext("FlowContext");
	}

	@SuppressWarnings("unchecked")
	public static Geography<RiverBlock> getFlowGeography()
	{
		return (Geography<RiverBlock>) ContextCreator.getFlowContext().getProjection("FlowGeography");
	}

	@SuppressWarnings("unchecked")
	public static Network<RiverSection> getFlowNetwork()
	{
		return (Network<RiverSection>) ContextCreator.getRiverContext().getProjection("FlowNetwork");
	}

	public static RiverContext getRiverContext()
	{
		return (RiverContext) mainContext.findContext("RiverContext");
	}

	@SuppressWarnings("unchecked")
	public static Geography<RiverSection> getRiverGeography()
	{
		return (Geography<RiverSection>) ContextCreator.getRiverContext().getProjection(
				"RiverGeography");
	}
	
	public static WaterHolderContext getWaterHolderContext()
	{
		return (WaterHolderContext) mainContext.findContext("WaterHolderContext");
	}

	@SuppressWarnings("unchecked")
	public static Geography<WaterHolder> getWaterHolderGeography()
	{
		return (Geography<WaterHolder>) ContextCreator.getWaterHolderContext().getProjection(
				"WaterHolderGeography");
	}
	
	public static WaterPlantContext getWaterPlantContext()
	{
		return (WaterPlantContext) mainContext.findContext("WaterPlantContext");
	}

	@SuppressWarnings("unchecked")
	public static Geography<WaterPlant> getWaterPlantGeography()
	{
		return (Geography<WaterPlant>) ContextCreator.getWaterPlantContext().getProjection(
				"WaterPlantGeography");
	}
	
	public static IndustryContext getIndustryContext()
	{
		return (IndustryContext) mainContext.findContext("IndustryContext");
	}

	@SuppressWarnings("unchecked")
	public static Geography<Industry> getIndustryGeography()
	{
		return (Geography<Industry>) ContextCreator.getIndustryContext().getProjection(
				"IndustryGeography");
	}

	
	public static void main(String args[])
	{
		ContextCreator	cc;
		
		cc = new ContextCreator();
		cc.build(new DefaultContext<Object>());
	}

	public Context<Object> getMainContext()
	{
		return mainContext;
	}
}
