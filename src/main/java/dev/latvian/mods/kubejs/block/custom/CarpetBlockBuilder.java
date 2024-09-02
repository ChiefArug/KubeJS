package dev.latvian.mods.kubejs.block.custom;

import dev.latvian.mods.kubejs.client.VariantBlockStateGenerator;
import dev.latvian.mods.kubejs.generator.KubeAssetGenerator;
import dev.latvian.mods.kubejs.util.ID;
import dev.latvian.mods.rhino.util.ReturnsSelf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;

@ReturnsSelf
public class CarpetBlockBuilder extends ShapedBlockBuilder {
	public static final ResourceLocation[] CARPET_TAGS = {
		BlockTags.WOOL_CARPETS.location(),
	};

	public CarpetBlockBuilder(ResourceLocation i) {
		super(i, "_carpet");
		tagBoth(CARPET_TAGS);
	}

	@Override
	public Block createObject() {
		return new CarpetBlock(createProperties());
	}

	@Override
	protected void generateBlockState(VariantBlockStateGenerator bs) {
		var mod = id.withPath(ID.BLOCK);
		bs.variant("", (v) -> v.model(mod));
	}

	@Override
	protected void generateBlockModel(KubeAssetGenerator generator) {
		var texture = textures.get("texture");

		generator.blockModel(id, m -> {
			m.parent("minecraft:block/carpet");
			m.texture("wool", texture);
		});
	}

	public CarpetBlockBuilder texture(String texture) {
		return (CarpetBlockBuilder) textureAll(texture);
	}
}
