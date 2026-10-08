package com.dreamtea.mixin;

import com.dreamtea.data.LiquidRecipeListener;
import com.dreamtea.data.LiquidRecipeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.LiquidBlock.POSSIBLE_FLOW_DIRECTIONS;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {
    @Shadow
    protected abstract void fizz(LevelAccessor level, BlockPos pos);

    @Inject(
            method = "shouldSpreadLiquid",
            at = @At("HEAD"),
            cancellable = true)
    public void shouldSpread(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir){
        if(level instanceof ServerLevel serverLevel){
            var recipes = LiquidRecipeListener.getRecipes(serverLevel);
            for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                if(recipes.stream().anyMatch(r -> !r.shouldFlow(serverLevel, pos, direction))){
                    this.fizz(level, pos);
                    cir.setReturnValue(false);
                }
            }
        }
        cir.setReturnValue(true);
    }
}
