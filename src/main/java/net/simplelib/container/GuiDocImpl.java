package net.simplelib.container;

import api.simplelib.ui.GuiDocument;
import api.simplelib.ui.elements.Element;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author ci010
 */
public class GuiDocImpl implements GuiDocument
{
	private Map<String, Element> map;
	private List<Element> list, root;
	private final ResourceLocation location;

	public GuiDocImpl(ResourceLocation location, List<Element> root)
	{
		this.root = ImmutableList.copyOf(root);
		this.list = Lists.newArrayList();
		this.map = Maps.newHashMap();
		this.discover(root);
		this.list = ImmutableList.copyOf(list);
		this.map = ImmutableMap.copyOf(map);
		this.location = location;
	}

	private void discover(List<Element> elements)
	{
		for (Element element : elements)
		{
			list.add(element);
			map.put(element.getName(), element);
		}
		for (Element element : elements)
		{
			List<Element> children = element.getChildren();
			if (!children.isEmpty())
				discover(children);
		}
	}

	@Override
	public Collection<Element> getRoots()
	{
		return root;
	}

	@Override
	public ResourceLocation location()
	{
		return location;
	}

	@Nullable
	@Override
	public Element getById(int id)
	{
		return list.get(id);
	}

	@Nullable
	@Override
	public Element getByName(@Nonnull String name)
	{
		return map.get(name);
	}

	@Nonnull
	@Override
	public Collection<? extends Element> getAll()
	{
		return list;
	}

	@Override
	public Element[] toArray()
	{
		return list.toArray(new Element[list.size()]);
	}

	@Override
	public int size()
	{
		return list.size();
	}

	@Nonnull
	@Override
	public Collection<String> allPresent()
	{
		return map.keySet();
	}
}
