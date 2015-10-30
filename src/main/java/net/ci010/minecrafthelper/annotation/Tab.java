package net.ci010.minecrafthelper.annotation;

import net.minecraft.creativetab.CreativeTabs;

public @interface Tab
{
	Class<? extends CreativeTabs> value();
}
