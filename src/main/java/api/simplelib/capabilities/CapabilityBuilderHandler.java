package api.simplelib.capabilities;


import api.simplelib.registry.FreeConstruct;
import api.simplelib.utils.Consumer;
import com.google.gson.JsonDeserializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The handle which creates the context and handles it to capability instance.
 * Use {@link ModCapabilityBuilderHandler} to register.
 *
 * @author ci010
 */
@FreeConstruct
public interface CapabilityBuilderHandler<Builder>
{
	/**
	 * Create builder and provide this to user to buildUnsaved. We just collect the information after they use this builder.
	 *
	 * @param contextSrc The src could be {@link net.minecraft.entity.Entity}, {@link net.minecraft.item.ItemStack}
	 *                   and {@link net.minecraft.tileentity.TileEntity}.
	 * @return The context. If you don't want to support to handle this context, just return null.
	 */
	@Nullable
	Builder createBuilder(Object contextSrc);

	/**
	 * Create the capability instance by the builder after user's operation.
	 *
	 * @param builder The capability builder after operation.
	 * @param context The capability context.
	 */
	ICapabilityProvider build(Builder builder, Object context);

	@Nonnull
	Capability<?>[] allCapability();

	@Nonnull
	ResourceLocation getName();

	interface JsonBase<Builder> extends CapabilityBuilderHandler<Builder>
	{
		JsonDeserializer<Consumer<Builder>> getDeserializer();
	}
}
