package com.dreamtea.data;

import com.dreamtea.recipe.LiquidRecipe;
import com.dreamtea.registration.SimpleDataProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class LiquidRecipeProvider extends SimpleDataProvider<LiquidRecipe, DefaultRecipes> {

    /***
     * To implement this class override this constructor. The new constructor have parameters packOutput and registriesFuture
     * @param packOutput this value should remain in the parent constructor
     * @param registriesFuture  this value should remain in the parent constructor
     */
    public LiquidRecipeProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.DATA_PACK, "liquid_recipes", LiquidRecipe.CODEC.codec(), DefaultRecipes.instance);
    }
}
