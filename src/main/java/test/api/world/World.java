package test.api.world;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import test.api.component.block.StateBlock;
import test.api.component.entity.StateEntity;
import test.api.component.entity.livingbase.StatePlayer;
import test.api.component.item.StateItem;
import test.impl.EntityFinder;

import java.util.Random;

/**
 * @author ci010
 */
public interface World
{
	StateEntity getEntity(int id);

	EntityFinder getEntityFinder();

	void playSoundAtEntity(StatePlayer playerIn, String sound, float v, float v1);

	Random getRandom();

	void dropItem(StateItem item, BlockPos pos);

	StateBlock getBlockState(BlockPos pos);

	BiomeGenBase getBiome(BlockPos pos);

	boolean isRemote();

	int getHeight(int x, int z);

	boolean isDaytime();

	int getSeaLevel();

	long getWorldTime();

	long getSeed();

	void setWorldTime(long time);

	long getTotalWorldTime();

	BlockPos getSpawnPoint();

	void setSpawnPoint(BlockPos pos);
}
