package net.simplelib.common.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author ci010
 */
public class LanguageReporter
{
	private static LanguageReporter instance = new LanguageReporter();
	/**
	 * The support language type of the mod.
	 */
	private List<String> langNodes = Lists.newArrayList();
	/**
	 * The keys needed to be localized.
	 */
	private Set<File> fileLang = Sets.newHashSet();

	public void report(String unlocalizedName)
	{
		langNodes.add(unlocalizedName.concat(".name="));
	}

	public void report(Block block)
	{
		this.report(block.getUnlocalizedName());
	}

	public void report(Item item)
	{
		this.report(item.getUnlocalizedName());
	}

	public LanguageReporter setLangType(String modid, String[] str)
	{
		try
		{
			for (String name : str)
			{
				File f = new File(FileReference.getRefer(modid).dirLang, name.concat(".lang"));
				if (!f.exists())
					f.createNewFile();
				fileLang.add(f);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return this;
	}

	public void writeLang() throws IOException
	{
		for (File lang : fileLang)
		{
			BufferedWriter writer = new BufferedWriter(new FileWriterWithEncoding(lang, "UTF-8"));
			Collections.sort(langNodes);
			for (String name : langNodes)
			{
				writer.write(name);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		}
	}

	public static LanguageReporter instance()
	{
		return instance;
	}

	void close()
	{
		instance = null;
	}
}
