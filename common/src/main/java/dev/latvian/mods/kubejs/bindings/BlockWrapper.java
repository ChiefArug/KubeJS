package dev.latvian.mods.kubejs.bindings;

import dev.latvian.mods.kubejs.block.MaterialJS;
import dev.latvian.mods.kubejs.block.MaterialListJS;
import dev.latvian.mods.kubejs.block.predicate.BlockEntityPredicate;
import dev.latvian.mods.kubejs.block.predicate.BlockIDPredicate;
import dev.latvian.mods.kubejs.block.predicate.BlockPredicate;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.Tags;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Info("Various block related helper functions")
public class BlockWrapper {
	@Info("Get a map of all KubeJS materials")
	public static Map<String, MaterialJS> getMaterial() {
		return MaterialListJS.INSTANCE.map;
	}

	public static BlockIDPredicate id(ResourceLocation id) {
		return new BlockIDPredicate(id);
	}

	public static BlockIDPredicate id(ResourceLocation id, Map<String, Object> properties) {
		var b = id(id);

		for (var entry : properties.entrySet()) {
			b = b.with(entry.getKey(), entry.getValue().toString());
		}

		return b;
	}

	public static BlockEntityPredicate entity(ResourceLocation id) {
		return new BlockEntityPredicate(id);
	}

	public static BlockPredicate custom(BlockPredicate predicate) {
		return predicate;
	}

	private static Map<String, Direction> facingMap;

	@Info("Get a map of direction name to Direction. Functionally identical to Direction.ALL")
	public static Map<String, Direction> getFacing() {
		if (facingMap == null) {
			facingMap = new HashMap<>(6);

			for (var facing : Direction.values()) {
				facingMap.put(facing.getSerializedName(), facing);
			}
		}

		return facingMap;
	}

	@Info("Gets a Block from a block id")
	public static Block getBlock(ResourceLocation id) {
		return KubeJSRegistries.blocks().get(id);
	}

	@Info("Gets a blocks id from the Block")
	@Nullable
	public static ResourceLocation getId(Block block) {
		return KubeJSRegistries.blocks().getId(block);
	}

	@Info("Gets a list of the classname of all registered blocks")
	public static List<String> getTypeList() {
		List<String> list = new ArrayList<>();

		for (var block : KubeJSRegistries.blocks().getIds()) {
			list.add(block.toString());
		}

		return list;
	}

	@Info("Gets a list of all blocks with tags")
	public static List<ResourceLocation> getTaggedIds(ResourceLocation tag) {
		return Util.make(new LinkedList<>(), list -> {
			for (var holder : Registry.BLOCK.getTagOrEmpty(Tags.block(tag))) {
				holder.unwrapKey().map(ResourceKey::location).ifPresent(list::add);
			}
		});
	}
}