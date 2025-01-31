package dev.latvian.mods.kubejs.recipe.component;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.IngredientWrapper;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.SizedIngredientWrapper;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.match.ItemMatch;
import dev.latvian.mods.kubejs.recipe.match.ReplacementMatchInfo;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class SizedIngredientComponent implements RecipeComponent<SizedIngredient> {
	public static final SizedIngredientComponent FLAT = new SizedIngredientComponent("flat_sized_ingredient", SizedIngredient.FLAT_CODEC);
	public static final SizedIngredientComponent NESTED = new SizedIngredientComponent("nested_sized_ingredient", SizedIngredient.NESTED_CODEC);

	public final String name;
	public final Codec<SizedIngredient> codec;

	public SizedIngredientComponent(String name, Codec<SizedIngredient> codec) {
		this.name = name;
		this.codec = codec;
	}

	@Override
	public Codec<SizedIngredient> codec() {
		return codec;
	}

	@Override
	public TypeInfo typeInfo() {
		return SizedIngredientWrapper.TYPE_INFO;
	}

	@Override
	public boolean hasPriority(Context cx, KubeRecipe recipe, Object from) {
		return IngredientWrapper.isIngredientLike(from);
	}

	@Override
	public boolean matches(Context cx, KubeRecipe recipe, SizedIngredient value, ReplacementMatchInfo match) {
		return match.match() instanceof ItemMatch m && m.matches(cx, value.ingredient(), match.exact());
	}

	@Override
	public boolean isEmpty(SizedIngredient value) {
		return value.count() <= 0 || value.ingredient().isEmpty();
	}

	@Override
	public void buildUniqueId(UniqueIdBuilder builder, SizedIngredient value) {
		var tag = IngredientWrapper.tagKeyOf(value.ingredient());

		if (tag != null) {
			builder.append(tag.location());
		} else {
			var first = value.ingredient().kjs$getFirst();

			if (!first.isEmpty()) {
				builder.append(first.kjs$getIdLocation());
			}
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
