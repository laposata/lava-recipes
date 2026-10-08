package com.dreamtea.registration;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/***
 * A data provider that handles a default holder. To implement this class extend it and define the constructor.
 * After that, the implementation must be added to the DataGenerationEntrypoint.
 * @param <T> The type of the data
 * @param <U> The type of the holder that contains the set of data.
 */
public abstract class SimpleDataProvider<T, U extends DefaultHolder<T>>  extends FabricCodecDataProvider<T> {
    private final U dataHolder;
    private final String dirName;

    /***
     * To implement this class override this constructor. The new constructor have parameters packOutput and registriesFuture
     * @param packOutput this value should remain in the parent constructor
     * @param registriesFuture  this value should remain in the parent constructor
     * @param target  Data pack or Resource pack
     * @param directoryName where the files are stored
     * @param codec The codec for the data
     * @param dataHolderInstance The instance of the data holder which contains the data to be outputted.
     */
    protected SimpleDataProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture, PackOutput.Target target, String directoryName, Codec<T> codec, U dataHolderInstance) {
        super(packOutput, registriesFuture, target, directoryName, codec);
        this.dirName = directoryName;
        this.dataHolder = dataHolderInstance;
    }

    @Override
    protected void configure(BiConsumer<Identifier, T> provider, HolderLookup.Provider registryLookup) {
        dataHolder.register(provider, registryLookup);
    }

    @Override
    public String getName() {
        return dirName;
    }
}
