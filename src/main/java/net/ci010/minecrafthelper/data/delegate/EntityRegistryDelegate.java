package net.ci010.minecrafthelper.data.delegate;

import com.google.common.collect.Maps;
import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.ModEntity;
import net.ci010.minecrafthelper.util.FMLModUtil;
import net.ci010.minecrafthelper.util.GenericUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Map;

/**
 * @author ci010
 */
@ASMDelegate
public class EntityRegistryDelegate extends RegistryDelegate<ModEntity>
{
	static Map<String, Integer> m = Maps.newHashMap();

	static int nextId(String modid)
	{
		if (!m.containsKey(modid))
			m.put(modid, -1);
		int next = m.get(modid) + 1;
		m.put(modid, next);
		return next;
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		String modid = this.getModid();
		ModEntity anno = this.getAnnotation();
		Class<? extends net.minecraft.entity.Entity> clz = GenericUtil.cast(this.getAnnotatedClass());
		String name = anno.name();
		if (name.equals(""))
			name = clz.getName();
		int id = anno.id();
		if (id == -1)
			id = nextId(modid);
		EntityRegistry.registerModEntity(clz, name, id, FMLModUtil.getModContainer(modid).getMod(), anno.trackingRange(), anno
				.updateFrequency(), anno.sendsVelocityUpdates());
	}
}
