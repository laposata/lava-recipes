package com.dreamtea.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import static com.dreamtea.LavaRecipes.id;

public class RecipeFluidTags {
    public static final TagKey<Fluid> WATER_SOURCE = create("water_source");
    public static final TagKey<Fluid> LAVA_SOURCE = create("lava_source");
    public static final TagKey<Fluid> WATER_FLOW = create("water_flow");
    public static final TagKey<Fluid> LAVA_FLOW = create("lava_flow");
    private static TagKey<Fluid> create(final String name) {
        return TagKey.create(Registries.FLUID, id(name));
    }
}
