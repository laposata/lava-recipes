package com.dreamtea.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record InteractionSet(@NonNull List<InteractionDirection> directions) implements Comparable<InteractionSet> {
    public static final Codec<InteractionSet> CODEC = Codec.either(InteractionDirection.CODEC, InteractionDirection.CODEC.listOf()).xmap(
            either -> either.map(
                    left -> new InteractionSet(List.of(left)),
                     InteractionSet::new
                    ),
            set -> set.directions.size() == 1 ? Either.left(set.directions.getFirst()) : Either.right(set.directions)
    );
    public InteractionSet(InteractionDirection ... directions){
        this(List.of(directions));
    }
    public Set<BlockPos> findPos(BlockPos pos){
        return directions.stream().flatMap(dir -> dir.findPos(pos).stream()).collect(Collectors.toSet());
    }

    public int getDirectionCount(){
        return directions.stream().map(d -> d.directions).reduce(Integer::sum).orElse(0);
    }
    @Override
    public int compareTo(@NonNull InteractionSet o) {
        var thisCount = getDirectionCount();
        var thatCount = o.getDirectionCount();
        if(thisCount != thatCount){
            return thisCount - thatCount;
        }
        if(this.directions.contains(InteractionDirection.Self)){
            if(o.directions.contains(InteractionDirection.Self)){
                return 0;
            }
            return 1;
        }
        if(o.directions.contains(InteractionDirection.Self)){
            return -1;
        }
        return 0;
    }
}
