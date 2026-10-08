package com.dreamtea.data;

import com.dreamtea.recipe.InteractionDirection;
import com.dreamtea.recipe.InteractionPair;
import com.dreamtea.recipe.InteractionSet;
import com.dreamtea.recipe.LiquidRecipe;
import com.dreamtea.registration.DefaultHolder;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.FluidPredicate;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

import static com.dreamtea.LavaRecipes.id;
import static com.dreamtea.data.RecipeFluidTags.LAVA_FLOW;
import static com.dreamtea.data.RecipeFluidTags.LAVA_SOURCE;

public class DefaultRecipes extends DefaultHolder<LiquidRecipe> {
    public static final DefaultRecipes instance = new DefaultRecipes();

    static {
        instance.create(id("lava_to_obsidian"), (provider) -> new LiquidRecipe(
                List.of(
                        new InteractionPair(
                                new InteractionSet(InteractionDirection.Horizontal, InteractionDirection.Top),
                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(FluidTags.WATER)).build()),
                        new InteractionPair(
                                InteractionDirection.Self.asSet(),
                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(LAVA_SOURCE)).build())),
                List.of(),
                Blocks.OBSIDIAN.defaultBlockState()
        ));
        instance.create(id("lava_to_cobble"), (provider) -> new LiquidRecipe(
                List.of(
                        new InteractionPair(
                                new InteractionSet(InteractionDirection.Top, InteractionDirection.Horizontal),
                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(FluidTags.WATER)).build()),
                        new InteractionPair(
                                InteractionDirection.Self.asSet(),
                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(LAVA_FLOW)).build())
                ),
                List.of(Direction.UP, Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH),
                Blocks.COBBLESTONE.defaultBlockState()
        ));

        instance.create(id("lava_to_basalt"), (provider) -> new LiquidRecipe(
                List.of(
                        new InteractionPair(
                                InteractionDirection.Bottom.asSet(),
                                BlockPredicate.Builder.block().of(provider.lookup(Registries.BLOCK).get(), Blocks.SOUL_SOIL).build()),
                        new InteractionPair(
                                InteractionDirection.Any.asSet(),
                                BlockPredicate.Builder.block().of(provider.lookup(Registries.BLOCK).get(), Blocks.BLUE_ICE).build()),
                        new InteractionPair(
                                InteractionDirection.Self.asSet(),
                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(LAVA_FLOW)).build())
                ),
                List.of(),
                Blocks.BASALT.defaultBlockState()
        ));
        instance.create(id("lava_to_stone"), (provider) -> new LiquidRecipe(
                List.of(
                        new InteractionPair(
                                InteractionDirection.Top.asSet(),
                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(FluidTags.LAVA)).build()),
                        new InteractionPair(
                                InteractionDirection.Self.asSet(),
                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(FluidTags.WATER)).build())
                ),
                List.of(),
                Blocks.STONE.defaultBlockState()
        ));
//        instance.create(id("lava_to_deepslate"), (provider) -> new LiquidRecipe(
//                List.of(
//                        new InteractionPair(
//                                InteractionDirection.Horizontal.asSet(),
//                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(FluidTags.WATER)).build()),
//                        new InteractionPair(
//                                InteractionDirection.Self.asSet(),
//                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(LAVA_FLOW)).build()),
//                        new InteractionPair(
//                                InteractionDirection.Any.asSet(),
//                                BlockPredicate.Builder.block().of(
//                                        provider.lookup(Registries.BLOCK).get(),
//                                        Blocks.BLACKSTONE,
//                                        Blocks.POLISHED_BLACKSTONE,
//                                        Blocks.CHISELED_POLISHED_BLACKSTONE,
//                                        Blocks.POLISHED_BLACKSTONE_BRICKS,
//                                        Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
//                                        Blocks.GILDED_BLACKSTONE).build(),
//                                4
//                        )
//                ),
//                List.of(),
//                Blocks.DEEPSLATE.defaultBlockState()));
//        instance.create(id("lava_to_cinnabar"), (provider) -> new LiquidRecipe(
//                List.of(
//                        new InteractionPair(
//                                InteractionDirection.Horizontal.asSet(),
//                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(FluidTags.WATER)).build()),
//                        new InteractionPair(
//                                InteractionDirection.Self.asSet(),
//                                FluidPredicate.Builder.fluid().of(provider.getOrThrow(LAVA_FLOW)).build()),
//                        new InteractionPair(
//                                InteractionDirection.Bottom.asSet(),
//                                BlockPredicate.Builder.block().of(
//                                        provider.lookup(Registries.BLOCK).get(),
//                                        Blocks.CINNABAR,
//                                        Blocks.CINNABAR_BRICKS,
//                                        Blocks.CHISELED_CINNABAR,
//                                        Blocks.POLISHED_CINNABAR
//                                ).build()
//                        )
//                ),
//                List.of(),
//                Blocks.CINNABAR.defaultBlockState(),
//                1));
    }
}
