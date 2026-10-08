package com.dreamtea.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public record LiquidRecipe(
        List<InteractionPair> surroundingBlocks,
        List<Direction> directionList,
        BlockState output,
        int priority
) implements Comparable<LiquidRecipe> {
    public LiquidRecipe(
            List<InteractionPair> surroundingBlocks,
            List<Direction> directionList,
            BlockState output,
            int priority
    ){
        this.directionList = directionList;
        this.output = output;
        this.surroundingBlocks = surroundingBlocks.stream().sorted().collect(Collectors.toList());
        this.priority = priority;
    }

    public LiquidRecipe(
            List<InteractionPair> surroundingBlocks,
            List<Direction> directionList,
            BlockState output
    ){
        this(surroundingBlocks, directionList, output, 0);
    }

    public static final MapCodec<LiquidRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            InteractionPair.BLOCK_CODEC.codec().listOf().optionalFieldOf("surrounding_blocks", List.of()).forGetter(i -> i.surroundingBlocks),
            Direction.CODEC.listOf().optionalFieldOf("flow", List.of()).forGetter(i -> i.directionList),
            BlockState.CODEC.fieldOf("output").forGetter(i -> i.output),
            Codec.INT.optionalFieldOf("priority", 0).forGetter(i -> i.priority)
    ).apply(instance, LiquidRecipe::new));

    public boolean shouldFlow(final ServerLevel level, final BlockPos pos, Direction flowDirection){
        if(check(level, pos, flowDirection)){
            setOutputs(level, pos);
            return false;
        }
        return true;
    }

    private boolean check(final ServerLevel level, final BlockPos pos, Direction flowDirection){
        return checkFlowDirection(flowDirection)
                && checkBlocks(level, pos);
    }

    private boolean checkFlowDirection(Direction flowDirection){
        if(directionList == null || directionList.isEmpty()) return true;
        return directionList.contains(flowDirection);
    }

    private boolean checkBlocks(final ServerLevel level, final BlockPos pos){
        return surroundingBlocks == null
                || surroundingBlocks.stream().allMatch(s -> s.checkSurroundings(level, pos));
    }

    private void setOutputs(final ServerLevel level, final BlockPos pos){
        if(output != null ){
            level.setBlockAndUpdate(pos, output);
        }
    }

    @Override
    public int compareTo(@NonNull LiquidRecipe o) {
        return this.priority - o.priority;
    }
}
