package net.simplelib.interactive.process;

import api.simplelib.interactive.Interactive;
import api.simplelib.interactive.process.ProcessPipeline;
import net.simplelib.interactive.inventory.PropertyDataInventory;

/**
 * @author ci010
 */
public class PropertyDataProcess extends PropertyDataInventory
{
	protected ProcessHandlerImpl handler;
	private int PROCESS, INTEGER, SYNC;

	@Override
	public boolean shouldApply(Interactive interactive)
	{
		return super.shouldApply(interactive) && interactive instanceof ProcessPipeline;
	}

	@Override
	public void init(Interactive interactive)
	{
		handler = new ProcessHandlerImpl((ProcessPipeline) interactive);
		if (!handler.available())
			handler = null;
	}

//	public List<Process> getProcess(InteractiveEntity entity)
//	{
//		return GenericUtil.cast(entity.getProperty(this.PROCESS));
//	}
//
//	public List<VarInteger> getSyncInteger(InteractiveEntity entity)
//	{
//		return GenericUtil.cast(entity.getProperty(this.INTEGER));
//	}
//
//	public ImmutableList<VarSync> getSyncVar(InteractiveEntity entity)
//	{
//		return GenericUtil.cast(entity.getProperty(this.SYNC));
//	}
//
//	@Override
//	public InteractiveEntity injectToEntity(InteractiveEntity entity)
//	{
//		super.injectToEntity(entity);
//
//		ImmutableMap<String, InventoryImpl> inventories = this.getInventories(entity);
//		if (this.handler == null)
//			return entity;
//		List<Process> processes = handler.buildProcess();
//		List<VarInteger> ints = new ArrayList<VarInteger>(handler.numOfInt);
//		List<VarSync> nbts = new ArrayList<VarSync>(handler.numOfSync);
//		try
//		{
//			for (int i = 0; i < processes.size(); ++i)
//			{
//				Process p = processes.get(i);
//				ProcessHandler.Info info = this.handler.infoList.get(i);
//				for (Field field : info.integers)
//				{
//					VarInteger temp = (VarInteger) field.get(p);
//					if (temp == null)
//						field.set(p, temp = new VarInteger(0));
//					ints.add(temp);
//				}
//				for (Field field : info.sync)
//				{
//					VarSync temp = (VarSync) field.get(p);
//					if (temp == null)
//						field.set(p, temp = new VarSync());
//					nbts.add(temp);
//				}
//				for (ProcessHandler.HolderInfo holderInfo : info.holderInfos)
//				{
//					InventoryImpl inv;
//					VarItemHolder holder = (VarItemHolder) holderInfo.field.get(p);
//					if (holder == null)
//						holderInfo.field.set(p, holder = new VarItemHolder());
//					if (holderInfo.owner == null)
//						inv = inventories.get("default");
//					else
//						inv = inventories.get(holderInfo.owner);
////						if (inv.holders == null)//TODO make a assign method
////							inv.holders = new ArrayList<VarItemHolder>(handler.numOfStack);
//					inv.assignNamespace(holderInfo.name, holder);
//				}
//			}
//		}
//		catch (IllegalAccessException e)
//		{
//			e.printStackTrace();
//		}
//		PROCESS = entity.injectProperty(processes);
//		INTEGER = entity.injectProperty(ints);
//		SYNC = entity.injectProperty(nbts);
////		entity = new InteractiveEntityUpdate("interactive_".concat(entity.getId()), entity.getWorld()).loadProcess
////				(processes,
////						ints,
////						nbts);
//		return entity;
//	}
}
