package com.dreamtea;

import com.dreamtea.data.FluidTagProvider;
import com.dreamtea.data.LiquidRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static com.mojang.text2speech.Narrator.LOGGER;

public class LavaRecipeDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        LOGGER.info("Generating");
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(FluidTagProvider::new);
        pack.addProvider(LiquidRecipeProvider::new);
    }
}
