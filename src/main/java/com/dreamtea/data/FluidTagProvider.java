package com.dreamtea.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidIds;

import java.util.concurrent.CompletableFuture;

import static com.dreamtea.data.RecipeFluidTags.*;

public class FluidTagProvider extends TagsProvider<Fluid> {

    public FluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.FLUID, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(WATER_FLOW).add(FluidIds.FLOWING_WATER);
        this.tag(WATER_SOURCE).add(FluidIds.WATER);
        this.tag(LAVA_FLOW).add(FluidIds.FLOWING_LAVA);
        this.tag(LAVA_SOURCE).add(FluidIds.LAVA);
    }
}
