package api.simplelib.coremod;

import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.common.ILanguageAdapter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SaveInspectionHandler;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Just a copy version of {@link net.minecraftforge.fml.common.Mod}
 *
 * @author ci010
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginMod
{
	/**
	 * The unique mod identifier for this mod
	 */
	String modid();

	/**
	 * A user friendly name for the mod
	 */
	String name() default "";

	/**
	 * A version string for this mod
	 */
	String version() default "";

	/**
	 * A simple dependency string for this mod (see modloader's "priorities" string specification)
	 */
	String dependencies() default "";

	/**
	 * Whether to use the mcmod.info metadata by default for this mod.
	 * If true, settings in the mcmod.info file will override settings in these annotations.
	 */
	boolean useMetadata() default false;

	/**
	 * If true, this mod will not be loaded on the Dedicated Server environment.
	 * Will crash if both serverSideOnly and clientSideOnly are set to true.
	 */
	boolean clientSideOnly() default false;

	/**
	 * If true, this mod will not be loaded on the Client environment.
	 * Will crash if both serverSideOnly and clientSideOnly are set to true.
	 */
	boolean serverSideOnly() default false;

	/**
	 * The acceptable range of minecraft versions that this mod will load and run in
	 * The default ("empty string") indicates that the currently RUNNING minecraft version is acceptable.
	 * This means ANY version that the end user adds the mod to. Modders PLEASS set this.
	 * FML will refuse to run with an error if the minecraft version is not in this range across all mods.
	 *
	 * @return A version range as specified by the maven version range specification or the empty string
	 */
	String acceptedMinecraftVersions() default "";

	/**
	 * A replacement for the no-longer-existing "versionRange" of NetworkMod. Specify a remote version range
	 * that this mod will accept as valid. Defaults to nothing, which is interpreted as "only this version".
	 * Another special value is '*' which means accept all versions.
	 * <p/>
	 * This is ignored if there is a {@link NetworkCheckHandler} annotation on a method in this class.
	 *
	 * @return A version range as specified by the maven version range specification or the empty string
	 */
	String acceptableRemoteVersions() default "";

	/**
	 * A version range specifying compatible save version information. If your mod follows good version numbering
	 * practice <a href="http://semver.org/">Like this (http://semver.org/)</a> then this should be sufficient.
	 * <p/>
	 * Advanced users can specify a {@link SaveInspectionHandler} instead.
	 *
	 * @return A version range as specified by the maven version range specification or the empty string
	 */
	String acceptableSaveVersions() default "";

	/**
	 * Specifying this field allows for a mod to expect a signed jar with a fingerprint matching this value.
	 * The fingerprint should be SHA-1 encoded, lowercase with ':' removed. An empty value indicates that
	 * the mod is not expecting to be signed.
	 * <p/>
	 * Any incorrectness of the fingerprint, be it missing or wrong, will result in the {@link FMLFingerprintViolationEvent}
	 * event firing <i>prior to any other event on the mod</i>.
	 *
	 * @return A certificate fingerprint that is expected for this mod.
	 */
	String certificateFingerprint() default "";

	/**
	 * The language the mod is authored in. This will be used to control certain compatibility behaviours for this mod.
	 * Valid values are currently "java", "scala"
	 *
	 * @return The language the mod is authored in
	 */
	String modLanguage() default "java";

	/**
	 * The language adapter to be used to load this mod. This overrides the value of modLanguage. The class must have a
	 * public zero variable constructor and implement {@link ILanguageAdapter} just like the Java and Scala adapters.
	 * <p/>
	 * A class with an invalid constructor or that doesn't implement {@link ILanguageAdapter} will throw an exception and
	 * halt loading.
	 *
	 * @return The full class name of the language adapter
	 */
	String modLanguageAdapter() default "";

	/**
	 * If your mod doesn't have a runtime persistent effect on the state of the game, and can be disabled without side effects
	 * (minimap mods, graphical tweak mods) then you can set true here and receive the FMLDeactivationEvent to perform deactivation
	 * tasks.
	 * This does not affect administrative disabling through the system property fml.modStates or the config file fmlModState.properties.
	 * The mod will only be deactivated outside of a running game world - FML will never allow mod deactivation whilst a game server
	 * is running.
	 *
	 * @return if this mod can be deactivated whilst the game is open.
	 */
	boolean canBeDeactivated() default false;

	/**
	 * An optional GUI factory for this mod. This is the name of a class implementing {@link IModGuiFactory} that will be instantiated
	 * on the client side, and will have certain configuration/options guis requested from it.
	 *
	 * @return The name of a class implementing {@link IModGuiFactory}
	 */
	String guiFactory() default "";

	/**
	 * An optional URL to a JSON file that will be checked once per launch to determine if there is an updated
	 * version of this mod and notify the end user. For more information see ForgeVersion.
	 * Format is defined here: https://gist.github.com/LexManos/7aacb9aa991330523884
	 *
	 * @return URL to update metadata json
	 */
	String updateJSON() default "";

	/**
	 * A list of custom properties for this mod. Completely up to the mod author if/when they
	 * want to put anything in here.
	 *
	 * @return an optional list of custom properties
	 */
	Mod.CustomProperty[] customProperties() default {};

	/**
	 * A custom key => value property pair for use with {@link Mod#customProperties()}
	 * @author cpw
	 *
	 */
}
