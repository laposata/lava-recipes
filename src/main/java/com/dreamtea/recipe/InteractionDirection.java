package com.dreamtea.recipe;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;

import java.util.Set;

public enum InteractionDirection implements StringRepresentable {
    Top(1) {
        @Override
        public Set<BlockPos> findPos(BlockPos pos) {
            return Set.of(pos.above());
        }
    },
    Bottom(1) {
        @Override
        public Set<BlockPos> findPos(BlockPos pos) {
            return Set.of(pos.below());
        }
    },
    Vertical(2) {
        @Override
        public Set<BlockPos> findPos(BlockPos pos) {
            return Set.of(pos.below(), pos.above());
        }
    },
    Horizontal(4) {
        @Override
        public Set<BlockPos> findPos(BlockPos pos) {
            return Set.of(pos.north(), pos.south(), pos.east(), pos.west());
        }
    },
    Any(6) {
        @Override
        public Set<BlockPos> findPos(BlockPos pos) {
            return Set.of(pos.north(), pos.south(), pos.east(), pos.west(), pos.above(), pos.below());
        }
    },
    Self(1) {
        @Override
        public Set<BlockPos> findPos(BlockPos pos) {
            return Set.of(pos);
        }
    };

    public static final Codec<InteractionDirection> CODEC = StringRepresentable.fromValues(InteractionDirection::values);

    InteractionDirection(int directions) {
        this.directions = directions;
    }

    abstract Set<BlockPos> findPos(BlockPos pos);
    public final int directions;
    public InteractionSet asSet(){
        return new InteractionSet(this);
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }

}
