package api.simplelib.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.Source;

import javax.sound.midi.Sequencer;
import java.util.Map;

/**
 * @author ci010
 */
@SideOnly(Side.CLIENT)
public class SoundHack
{
	private static SoundHack INSTANCE;
	private Sequencer sequencer;

	private SoundHack()
	{
		hack();
	}

	private void hack()
	{
		SoundCategory bgm1 = EnumHelper.addEnum(SoundCategory.class, "BGM1", "bgm1", 9);
		SoundCategory bgm2 = EnumHelper.addEnum(SoundCategory.class, "BGM2", "bgm2", 10);
		Map<String, SoundCategory> NAME_CATEGORY_MAP = ReflectionHelper.getPrivateValue(SoundCategory.class, null,
				"NAME_CATEGORY_MAP");
		Map<Integer, SoundCategory> ID_CATEGORY_MAP = ReflectionHelper.getPrivateValue(SoundCategory.class, null,
				"ID_CATEGORY_MAP");
		NAME_CATEGORY_MAP.put(bgm1.getCategoryName(), bgm1);
		ID_CATEGORY_MAP.put(bgm1.getCategoryId(), bgm1);
		NAME_CATEGORY_MAP.put(bgm2.getCategoryName(), bgm2);
		ID_CATEGORY_MAP.put(bgm2.getCategoryId(), bgm2);
	}

	public static SoundHack instance()
	{
		if (INSTANCE == null)
			INSTANCE = new SoundHack();
		return INSTANCE;
	}
}
