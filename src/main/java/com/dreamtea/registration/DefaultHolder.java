package com.dreamtea.registration;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Creates a set of data to be outputted by a dataProvider.
 * Classes that extend this one should use a singleton pattern.
 * @param <T> The type of the default instances
 */
public abstract class DefaultHolder<T> {
    protected DefaultHolder(){}
    private final Map<Identifier, Function<HolderLookup.Provider, T>> dataMap = new HashMap<>();
    public void register(BiConsumer<Identifier, T> provider, HolderLookup.Provider lookup){
        dataMap.forEach((id, func) -> {
            var recipe = func.apply(lookup);
            provider.accept(id, recipe);
        });
    }

    public Identifier create(Identifier id, T data){
        dataMap.put(id, (provider) -> data);
        return id;
    }

    public Identifier create(Identifier id, Function<HolderLookup.Provider, T> dataCreator){
        dataMap.put(id, dataCreator);
        return id;
    }
}
