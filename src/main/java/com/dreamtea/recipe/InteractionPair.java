package com.dreamtea.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.FluidPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.Set;

public class InteractionPair implements Comparable<InteractionPair> {
    public static final MapCodec<InteractionPair> BLOCK_CODEC =  RecordCodecBuilder.mapCodec(instance -> instance.group(
            InteractionSet.CODEC.fieldOf("directions").forGetter(i -> i.directions),
            BlockPredicate.CODEC.optionalFieldOf("blockPredicate").forGetter(i -> Optional.ofNullable(i.blockPredicate)),
            FluidPredicate.CODEC.optionalFieldOf("fluidPredicate").forGetter(i -> Optional.ofNullable(i.fluidPredicate)),
            Codec.INT.optionalFieldOf("matchCount", 1).forGetter(i -> i.matchCount)
    ).apply(instance, InteractionPair::new));

    public final InteractionSet directions;
    private final BlockPredicate blockPredicate;
    private final FluidPredicate fluidPredicate;
    public final int matchCount;

    private InteractionPair(InteractionSet directions, Optional<BlockPredicate> block, Optional<FluidPredicate> fluid, int matchCount){
        this.directions = directions;
        this.matchCount = matchCount;
        if(block.isPresent() && fluid.isPresent()){
            throw new IllegalArgumentException("Block interaction must only contain blockPredicate or fluidPredicate, not both");
        }
        if(block.isEmpty() && fluid.isEmpty()){
            throw new IllegalArgumentException("Block interaction must only one of blockPredicate or fluidPredicate");
        }
        this.blockPredicate = block.orElse(null);
        this.fluidPredicate = fluid.orElse(null);
    }

    public InteractionPair(InteractionSet directions, BlockPredicate predicate, int matchCount) {
        this.directions = directions;
        this.blockPredicate = predicate;
        this.fluidPredicate = null;
        this.matchCount = matchCount;
    }

    public InteractionPair(InteractionSet directions, BlockPredicate predicate) {
        this.directions = directions;
        this.blockPredicate = predicate;
        this.fluidPredicate = null;
        this.matchCount = 1;
    }

    public InteractionPair(InteractionSet directions, FluidPredicate predicate, int matchCount) {
        this.directions = directions;
        this.blockPredicate = null;
        this.fluidPredicate = predicate;
        this.matchCount = matchCount;
    }

    public InteractionPair(InteractionSet directions, FluidPredicate predicate) {
        this.directions = directions;
        this.blockPredicate = null;
        this.fluidPredicate = predicate;
        this.matchCount = 1;
    }

    public boolean checkSurroundings(final ServerLevel level, final BlockPos pos) {
        var directionSet = directions.findPos(pos);
        if(blockPredicate != null) return checkBlocks(level, directionSet);
        return checkFluids(level, directionSet);
    }

    private boolean checkBlocks(final ServerLevel level, Set<BlockPos> directions){
        int matched = 0;
        for (BlockPos blockPos : directions) {
            if(blockPredicate.matches(level, blockPos)){
                matched ++;
            }
            if(matched >= matchCount) return true;
        }
        return false;
    }
    private boolean checkFluids(final ServerLevel level, Set<BlockPos> directions){
        int matched = 0;
        for (BlockPos blockPos : directions) {
            if(fluidPredicate.matches(level, blockPos)){
                matched ++;
            }
            if(matched >= matchCount) return true;
        }
        return false;
    }

    @Override
    public int compareTo(@NonNull InteractionPair o) {
        var checkFailures = directions.getDirectionCount() - matchCount;
        var otherCheckFailures = o.directions.getDirectionCount() - o.matchCount;
        if(checkFailures != otherCheckFailures){
            return checkFailures - otherCheckFailures;
        }
        if(matchCount != o.matchCount){
            return matchCount - o.matchCount;
        }
        if(blockPredicate != null){
            if(o.blockPredicate == null) return 1;
            return directions.compareTo(o.directions);
        }
        if(o.blockPredicate != null) return -1;
        return directions.compareTo(o.directions);
    }
}
