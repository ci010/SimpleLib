package test.waggon;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.world.World;
import net.simplelib.annotation.type.ModCommand;

/**
 * @author ci010
 */
@ModCommand
public class TestCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "test_waggon";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		EntityHorse horse = new EntityHorse(world);
		EntityWaggon waggon = new EntityWaggon(world);
		horse.setPosition(sender.getPosition().getX() + 5, sender.getPosition().getY(), sender.getPosition().getZ());
		waggon.setPosition(sender.getPosition().getX() + 10, sender.getPosition().getY(), sender.getPosition().getZ());
		world.spawnEntityInWorld(horse);
		world.spawnEntityInWorld(waggon);
		waggon.linkTo(horse, 10);
	}
}
