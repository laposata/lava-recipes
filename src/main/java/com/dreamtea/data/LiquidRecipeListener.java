package com.dreamtea.data;

import com.dreamtea.recipe.LiquidRecipe;
import com.dreamtea.registration.ReloadListener;
import net.fabricmc.fabric.api.resource.v1.DataResourceLoader;
import net.fabricmc.fabric.api.resource.v1.DataResourceStore;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class LiquidRecipeListener extends ReloadListener<LiquidRecipe, Map<Identifier, LiquidRecipe>> {
    private static final DataResourceStore.Key<List<LiquidRecipe>> LIQUID_RECIPE_MAP = new DataResourceStore.Key<>();

    public LiquidRecipeListener() {
        super("liquid_recipes",
                LiquidRecipe.CODEC.codec(),
                (map) -> map);
    }

    @Override
    protected void apply(Map<Identifier, LiquidRecipe> prepared, SharedState state) {
        state.get(DataResourceLoader.DATA_RESOURCE_STORE_KEY).put(
                LIQUID_RECIPE_MAP,
                prepared.values().stream().sorted().toList()
        );
    }

    public static List<LiquidRecipe> getInstance(ServerLevel level) {
        if(level != null) {
            return level.getServer().getOrThrow(LIQUID_RECIPE_MAP);
        }
        return List.of();
    }

    public static List<LiquidRecipe> getRecipes(ServerLevel level){
        return getInstance(level);
    }
    public static BiFunction<Level, BlockPos, List<LiquidRecipe>> getInstance(){
        return (level, pos) -> level instanceof ServerLevel server ? getInstance(server) : null;
    }
}
