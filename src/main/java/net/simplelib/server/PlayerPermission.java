package net.simplelib.server;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.Collection;
import java.util.Set;

/**
 * @author ci010
 */
public interface PlayerPermission extends CommandAccess
{
	@CapabilityInject(PlayerPermission.class)
	Capability<PlayerPermission> CAPABILITY = null;

	ResourceLocation BREAK_BLOCK_GLOBAL = new ResourceLocation("permission:global.break");
	ResourceLocation USE_BLOCK_GLOBAL = new ResourceLocation("permission:global.activate");
	ResourceLocation PLACE_BLOCK_GLOBAL = new ResourceLocation("permission:global.place");
	ResourceLocation MOVE_GLOBAL = new ResourceLocation("permission:global.move");

	Collection<CommandAccess> getCommandAccesses();

	void addAccess(CommandAccess access);

	boolean removeAccess(CommandAccess access);

	Set<Group> getGroups();

	void addCustomPermission(ResourceLocation loc);

	boolean hasCustomPermission(ResourceLocation loc);

	void removeCustomPermission(ResourceLocation loc);
}
