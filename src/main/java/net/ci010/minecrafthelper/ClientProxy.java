package net.ci010.minecrafthelper;

import net.ci010.minecrafthelper.abstracts.ASMDataParser;
import net.ci010.minecrafthelper.annotation.GenLang;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;

public class ClientProxy extends CommonProxy
{
	@Override
	public void loadASMDataTable(ASMDataTable table)
	{
		super.loadASMDataTable(table);
		new ASMDataParser()
		{
			@Override
			protected void parse(ASMData data)
			{
				String[] arr = this.getAnnotation(data, GenLang.class).value();
				if (arr.length == 0)
					RegistryHelper.INSTANCE.setLang(this.getId(data), true);
				else
					RegistryHelper.INSTANCE.setLang(this.getId(data), true, arr);
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.GenLang"));

		new ASMDataParser()
		{
			@Override
			protected void parse(ASMData data)
			{
				RegistryHelper.INSTANCE.setModel(this.getId(data), true);
			}
		}.parse(table.getAll("net.ci010.minecrafthelper.annotation.GenModel"));
	}

	@Override
	public boolean isClient()
	{
		return true;
	}
}
