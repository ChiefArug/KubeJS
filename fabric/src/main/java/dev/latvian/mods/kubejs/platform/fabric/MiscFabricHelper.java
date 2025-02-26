package dev.latvian.mods.kubejs.platform.fabric;

import dev.latvian.mods.kubejs.platform.MiscPlatformHelper;
import dev.latvian.mods.kubejs.script.PlatformWrapper;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.MobCategory;

@SuppressWarnings("UnstableApiUsage")
public class MiscFabricHelper implements MiscPlatformHelper {
	private Boolean dataGen;

	@Override
	public void setModName(PlatformWrapper.ModInfo info, String name) {
		try {
			var mc = FabricLoader.getInstance().getModContainer(info.getId());

			if (mc.isPresent()) {
				var meta = mc.get().getMetadata();
				var field = meta.getClass().getDeclaredField("name");
				field.setAccessible(true);
				field.set(meta, name);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public MobCategory getMobCategory(String name) {
		// safe cast, mojang just specified too general of a type
		return ((StringRepresentable.EnumCodec<MobCategory>) MobCategory.CODEC).byName(name);
	}

	@Override
	public boolean isDataGen() {
		if (dataGen == null) {
			// FabricDataGenHelper.ENABLED
			dataGen = System.getProperty("fabric-api.datagen") != null;
		}

		return dataGen;
	}

	@Override
	public long ingotFluidAmount() {
		return FluidConstants.INGOT;
	}

	@Override
	public long bottleFluidAmount() {
		return FluidConstants.BOTTLE;
	}
}
