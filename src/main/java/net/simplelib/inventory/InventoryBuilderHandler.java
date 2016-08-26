package net.simplelib.inventory;

import api.simplelib.capabilities.CapabilityBuilderHandler;
import api.simplelib.inventory.*;
import api.simplelib.utils.Consumer;
import api.simplelib.utils.TypeUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
public class InventoryBuilderHandler implements CapabilityBuilderHandler.JsonBase<InventoryBuilder>
{
	@Override
	public InventoryBuilder createBuilder(Object contextSrc)
	{
		return new Impl();
	}

	@Override
	public ICapabilityProvider build(InventoryBuilder inventoryBuilder, Object context)
	{
		Impl impl = (Impl) inventoryBuilder;
		InvImpl inv = impl.buildInventory();
		return new Provider(inv);
	}

	@Override
	public Capability<?>[] allCapability()
	{
		return new Capability<?>[]{CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Inventory.CAPABILITY};
	}

	@Nonnull
	@Override
	public ResourceLocation getName()
	{
		return new ResourceLocation("helper", "inventories");
	}

	private class Provider implements ICapabilitySerializable<NBTBase>
	{
		Inventory inventory;

		public Provider(Inventory inventory)
		{
			this.inventory = inventory;
		}

		@Override
		public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
		{
			return capability == Inventory.CAPABILITY ||
					(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && inventory.getBySide(facing) != null);
		}

		@Override
		public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
		{
			return TypeUtils.cast(capability == Inventory.CAPABILITY ? inventory : capability == CapabilityItemHandler
					.ITEM_HANDLER_CAPABILITY ? inventory.getBySide(facing) : null);
		}

		@Override
		public NBTBase serializeNBT()
		{
			return Inventory.CAPABILITY.getStorage().writeNBT(Inventory.CAPABILITY, inventory, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt)
		{
			Inventory.CAPABILITY.getStorage().readNBT(Inventory.CAPABILITY, inventory, null, nbt);
		}
	}

	static class Impl implements InventoryBuilder
	{
		private int currentIdx = 0;
		private InvImpl inv = new InvImpl();
		private EnumMap<EnumFacing, int[]> sideMap = new EnumMap<EnumFacing, int[]>(EnumFacing.class);
		private ArrayList<InventoryElement> elements = new ArrayList<InventoryElement>();

		public InvImpl buildInventory()
		{
			if (currentIdx == 0)
				return null;
			inv.build(sideMap, currentIdx, elements);
			return inv;
		}

		public InventorySpace newSpace(String name, int size, InventoryRule rule, EnumFacing... facing)
		{
			if (facing != null)
			{
				int[] newArr = new int[size];
				for (int i = 0; i < size; i++)
					newArr[i] = currentIdx + i;
				for (EnumFacing enumFacing : facing)
				{
					int[] temp;
					if (sideMap.containsKey(enumFacing))
						temp = ArrayUtils.addAll(sideMap.get(enumFacing), newArr);
					else temp = newArr;
					sideMap.put(enumFacing, temp);
				}
			}
			InvSpaceImpl space = new InvSpaceImpl(name, inv, currentIdx, size, rule);
			currentIdx += size;
			elements.add(space);
			return space;
		}

		public InventorySlot newSlot(String name, InventoryRule rule, EnumFacing... facing)
		{
			if (facing != null)
			{
				int[] newArr = new int[]{currentIdx};
				for (EnumFacing enumFacing : facing)
				{
					int[] temp;
					if (sideMap.containsKey(enumFacing))
						temp = ArrayUtils.addAll(sideMap.get(enumFacing), newArr);
					else temp = newArr;
					sideMap.put(enumFacing, temp);
				}
			}
			SlotSpaceImpl slotSpace = new SlotSpaceImpl(name, inv, currentIdx, rule);
			++currentIdx;
			elements.add(slotSpace);
			return slotSpace;
		}

		public InventorySpace newSpace(String name, int size, EnumFacing... facing)
		{
			return this.newSpace(name, size, null, facing);
		}


		public InventorySlot newSlot(String name, EnumFacing... facing)
		{
			return this.newSlot(name, InventoryRule.COMMON, facing);
		}

		@Override
		public Allocator getAllocator()
		{
			return null;
		}
	}

	public static Map<String, InventoryRule> ruleMap = Maps.newHashMap();

	public static JsonDeserializer<Void> ruleJsonDeserializer = new JsonDeserializer<Void>()
	{
		@Override
		public Void deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
				JsonParseException
		{
			JsonArray arr = json.getAsJsonArray();
			for (JsonElement element : arr)
			{
				JsonObject obj = element.getAsJsonObject();
				String name = obj.get("name").getAsString();
				int limit = obj.get("limit").getAsJsonPrimitive().getAsInt();
				String mode = obj.get("mode").getAsString();
				JsonArray items = obj.get("items").getAsJsonArray();
				ArrayList<ItemStack> stacks = Lists.newArrayList();
				for (JsonElement item : items)
				{
					String str = item.getAsString();
					String[] split = str.split(":");
					if (split.length == 1)
					{
						Item itm = Item.REGISTRY.getObject(new ResourceLocation(split[0]));
						if (itm != null)
							stacks.add(new ItemStack(itm, OreDictionary.WILDCARD_VALUE));
					}
					else if (split.length == 2)
					{
						if (split[1].matches("[0-9]"))
						{
							Item itm = Item.REGISTRY.getObject(new ResourceLocation(split[0]));
							if (itm != null)
								stacks.add(new ItemStack(itm, Integer.parseInt(split[1])));
						}
						else
						{
							Item itm = Item.REGISTRY.getObject(new ResourceLocation(split[0], split[1]));
							if (itm != null)
								stacks.add(new ItemStack(itm, OreDictionary.WILDCARD_VALUE));
						}
					}
					else if (split.length == 3)
					{
						Item itm = Item.REGISTRY.getObject(new ResourceLocation(split[0], split[1]));
						if (itm != null)
							stacks.add(new ItemStack(itm, Integer.parseInt(split[2])));
					}
					else
					{
					}
				}
				ruleMap.put(name, new RuleObj(stacks.toArray(new ItemStack[stacks.size()]),
						!mode.equals("allow"), limit));
			}
			return null;
		}
	};

	private static class RuleObj implements InventoryRule
	{
		ItemStack[] stacks;
		boolean itemBlackList;
		int limit;

		public RuleObj(ItemStack[] stacks, boolean itemBlackList, int limit)
		{
			this.stacks = stacks;
			this.itemBlackList = itemBlackList;
			this.limit = limit;
		}

		@Override
		public boolean isUsebleByPlayer(EntityPlayer player)
		{
			return true;
		}

		@Override
		public boolean isItemValid(ItemStack stack)
		{
			for (ItemStack itemStack : stacks)
				if (OreDictionary.itemMatches(itemStack, stack, false) ^ itemBlackList)
					return false;
			return false;
		}

		@Override
		public int getInventoryStackLimit()
		{
			return limit;
		}
	}

	private class Bean implements Consumer<InventoryBuilder>
	{
		InventoryRule[] rule;
		String[] name;
		int[] size;
		EnumFacing[][] sides;
		int idx;

		Bean(int totalSize)
		{
			this.idx = 0;
			this.rule = new InventoryRule[totalSize];
			this.name = new String[totalSize];
			this.sides = new EnumFacing[totalSize][];
			this.size = new int[totalSize];
		}

		void newSpace(String name, int size, @Nullable EnumFacing... facing)
		{
			this.newSpace(name, size, null, facing);
		}

		void newSpace(String name, int size,
					  @Nullable InventoryRule rule, @Nullable EnumFacing... facing)
		{
			this.name[idx] = name;
			this.size[idx] = size;
			this.rule[idx] = rule;
			this.sides[idx++] = facing;
		}

		void newSlot(String name, @Nullable EnumFacing... facing)
		{
			newSpace(name, -1, null, facing);
		}

		void newSlot(String name, @Nullable InventoryRule rule, @Nullable EnumFacing... facing)
		{
			newSpace(name, -1, rule, facing);
		}

		@Override
		public void accept(@Nullable InventoryBuilder input)
		{
			for (int i = 0; i < idx; i++)
			{
				if (size[i] <= 1)
					input.newSlot(name[i], rule[i], sides[i]);
				else input.newSpace(name[i], size[i], rule[i], sides[i]);
			}
		}
	}

	@Override
	public JsonDeserializer<Consumer<InventoryBuilder>> getDeserializer()
	{
		return new JsonDeserializer<Consumer<InventoryBuilder>>()
		{
			@Override
			public Consumer<InventoryBuilder> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext
					context) throws JsonParseException
			{
				JsonObject obj = json.getAsJsonObject();
				Set<Map.Entry<String, JsonElement>> set = obj.entrySet();
				Bean impl = new Bean(set.size());
				for (Map.Entry<String, JsonElement> entry : set)
				{
					JsonElement value = entry.getValue();
					JsonObject ele = value.getAsJsonObject();
					InventoryRule rule = null;
					EnumFacing[] side = null;
					int slots = 0;
					JsonElement sidesEle = ele.get("side");
					if (sidesEle != null)
						if (sidesEle.isJsonPrimitive())
							side = new EnumFacing[]{EnumFacing.byName(sidesEle.getAsJsonPrimitive().getAsString())};
						else if (sidesEle.isJsonArray())
						{
							JsonArray arr = sidesEle.getAsJsonArray();
							side = new EnumFacing[arr.size()];
							for (int i = 0; i < arr.size(); i++)
								side[i] = EnumFacing.byName(arr.get(i).getAsString());
						}
					JsonElement size = ele.get("size");
					if (size != null)
						slots = size.getAsJsonPrimitive().getAsInt();
					JsonElement rul = ele.get("rule");
					if (rul != null)
						rule = ruleMap.get(rul.getAsString());
					if (slots > 0)
						impl.newSpace(entry.getKey(), slots, rule, side);
					else impl.newSlot(entry.getKey(), rule, side);
				}
				return impl;
			}
		};
	}
}
