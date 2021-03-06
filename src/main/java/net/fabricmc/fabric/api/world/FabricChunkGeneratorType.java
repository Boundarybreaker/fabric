package net.fabricmc.fabric.api.world;

import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;

import java.util.function.Supplier;

public class FabricChunkGeneratorType<C extends ChunkGeneratorConfig, T extends ChunkGenerator<C>> extends ChunkGeneratorType<C, T> {
	private final FabricChunkGeneratorFactory<C, T> factory;

	public FabricChunkGeneratorType(FabricChunkGeneratorFactory<C, T> factory, boolean buffetScreenOption, Supplier<C> settingsSupplier) {
		super(null, buffetScreenOption, settingsSupplier);
		this.factory = factory;
	}

	@Override
	public T create(World world, BiomeSource source, C config) {
		return this.factory.create(world, source, config);
	}

}
