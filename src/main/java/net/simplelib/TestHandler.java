package net.simplelib;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author ci010
 */
//@ModHandler
public class TestHandler
{
	public static void main(String[] args)
	{
		File f = new File("C:\\Users\\CIJhn\\Desktop\\root");
		Collection<File> files = FileUtils.listFiles(f, null, true);
		Set<String> stringSet = Sets.newHashSet();
		for (File file : files)
			stringSet.add(file.getPath().replace(f.getPath() + "\\", "").replace('\\', '/'));
		for (String s : stringSet)
			System.out.println(s);
		System.out.println();
		testZip();
	}

	static void testZip()
	{
		Set<String> strings = new HashSet<String>();
		try
		{
			ZipFile zipFile = new ZipFile(new File("C:/Users/CIJhn/Desktop/mobend.zip.src.zip"));
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements())
				strings.add(entries.nextElement().getName());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		strings = Sets.filter(strings, new Predicate<String>()
		{
			@Override
			public boolean apply(@Nullable String input)
			{
				return !(input.endsWith("/") || input.endsWith("db"));
			}
		});
		for (String string : strings)
			System.out.println(string);
	}
}
