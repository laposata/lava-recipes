package com.dreamtea.mixin;

import com.dreamtea.data.LiquidRecipeListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin extends FlowingFluid {

    @Shadow
    protected abstract void fizz(LevelAccessor level, BlockPos pos);

    @Inject(
            method = "spreadTo",
            at = @At("HEAD"),
            cancellable = true)
    public void shouldSpread(LevelAccessor level, BlockPos pos, BlockState state, Direction direction, FluidState target, CallbackInfo ci){
        if(level instanceof ServerLevel serverLevel){
            var recipes = LiquidRecipeListener.getRecipes(serverLevel);
            if(recipes.stream().anyMatch(r -> !r.shouldFlow(serverLevel, pos, direction))){
                this.fizz(level, pos);
            }
        }
        super.spreadTo(level, pos, state, direction, target);
        ci.cancel();
    }
}
