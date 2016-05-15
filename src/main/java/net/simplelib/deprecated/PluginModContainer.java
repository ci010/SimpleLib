package net.simplelib.deprecated;

import api.simplelib.coremod.PluginMod;
import api.simplelib.utils.ASMDataUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ModCandidate;
import net.minecraftforge.fml.common.discovery.ModDiscoverer;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLLoadEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.VersionRange;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ci010
 */
public abstract class PluginModContainer implements ModContainer
{
	protected ModContainer delegate;

	public PluginModContainer()
	{
		ModMetadata metadata = new ModMetadata();
		metadata.modId = this.getModId();
		delegate = new DummyModContainer(metadata);
	}

	@Override
	public String getName()
	{
		checkState();
		return delegate.getName();
	}

	@Override
	public String getVersion() {return delegate.getVersion();}

	@Override
	public ModMetadata getMetadata() {return delegate.getMetadata();}

	@Override
	public void bindMetadata(MetadataCollection mc)
	{
		System.out.println("bind meta");
		try
		{
			System.out.println(this.getSource());
			MetadataCollection from = MetadataCollection.from(new FileInputStream(this.getSource()), this.getSource()
					.getName());
			ModMetadata metadataForId = from.getMetadataForId(this.getModId(), Collections.<String, Object>emptyMap());
			delegate = new DummyModContainer(metadataForId);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		delegate.bindMetadata(mc);
	}

	@Override
	public void setEnabledState(boolean enabled) {delegate.setEnabledState(enabled);}

	@Override
	public Set<ArtifactVersion> getRequirements() {return delegate.getRequirements();}

	@Override
	public List<ArtifactVersion> getDependencies() {return delegate.getDependencies();}

	@Override
	public List<ArtifactVersion> getDependants() {return delegate.getDependants();}

	@Override
	public String getSortingRules() {return delegate.getSortingRules();}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}

	@Override
	public boolean matches(Object mod) {return delegate.matches(mod);}

	private void checkState()
	{
		if (this.delegate instanceof DummyModContainer)
		{
			System.out.println("getmod in loading");
			ModDiscoverer discoverer = ReflectionHelper.getPrivateValue(Loader.class, Loader.instance(), "discoverer");
			ASMDataTable dataTable = discoverer.getASMTable();
			SetMultimap<String, ASMDataTable.ASMData> annotations = dataTable.getAnnotationsFor(this);
			Map<String, Object> modDescriptor = Maps.newHashMap();
			for (ASMDataTable.ASMData asmData : annotations.get(PluginMod.class.getName()))
			{
				System.out.println(asmData.getClassName());
				System.out.println(asmData.getClassName());
				System.out.println(asmData.getClassName());
				System.out.println(asmData.getClassName());
				PluginMod annotation = ASMDataUtil.getAnnotation(asmData, PluginMod.class);
				if (annotation.modid().equals(this.getModId()))
				{
					ModCandidate modCandidate = asmData.getCandidate();
					modDescriptor.putAll(asmData.getAnnotationInfo());
					this.delegate = new FMLModContainer(asmData.getClassName(), modCandidate, modDescriptor);
					break;
				}
			}
		}
	}

	@Override
	public Object getMod()
	{
		System.out.println("getMod");
		checkState();
		if (delegate == null || delegate.getMod() == null)
			return this;
		return delegate.getMod();
	}

	@Override
	public ArtifactVersion getProcessedVersion() {return delegate.getProcessedVersion();}

	@Override
	public boolean isImmutable() {return delegate.isImmutable();}

	@Override
	public String getDisplayVersion() {return delegate.getDisplayVersion();}

	@Override
	public VersionRange acceptableMinecraftVersionRange() {return delegate.acceptableMinecraftVersionRange();}

	@Override
	public Certificate getSigningCertificate() {return delegate.getSigningCertificate();}

	@Override
	public Map<String, String> getCustomModProperties() {return delegate.getCustomModProperties();}

	@Override
	public Class<?> getCustomResourcePackClass() {return delegate.getCustomResourcePackClass();}

	@Override
	public Map<String, String> getSharedModDescriptor() {return delegate.getSharedModDescriptor();}

	@Override
	public Disableable canBeDisabled() {return delegate.canBeDisabled();}

	@Override
	public String getGuiClassName() {return delegate.getGuiClassName();}

	@Override
	public List<String> getOwnedPackages() {return delegate.getOwnedPackages();}

	@Override
	public boolean shouldLoadInEnvironment() {return delegate.shouldLoadInEnvironment();}

	@Override
	public URL getUpdateUrl() {return delegate.getUpdateUrl();}

}
