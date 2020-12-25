package slexom.earthtojava.mobs.init;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.DimensionType;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import slexom.earthtojava.mobs.config.E2JModConfig;

import java.util.Random;

public class FeatureInit {

    public static ConfiguredFeature<?, ?> BUTTERCUP_CONFIGURED_FEATURE;
    public static ConfiguredFeature<?, ?> RUBY_ORE_CONFIGURED_FEATURE;
    public static ConfiguredFeature<?, ?> MUD_LAKE_CONFIGURED_FEATURE;

    public static void init() {

        RUBY_ORE_CONFIGURED_FEATURE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "ruby_ore_configured_feature",
                Feature.ORE.withConfiguration(
                        new OreFeatureConfig(
                                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                                BlockInit.RUBY_ORE.get().getDefaultState(), 8)
                ).func_242730_a(FeatureSpread.func_242252_a(E2JModConfig.rubyOreCount)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(E2JModConfig.rubyOreBottomOffset, E2JModConfig.rubyOreTopOffset, E2JModConfig.rubyOreMaximum)))
        );
        BUTTERCUP_CONFIGURED_FEATURE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "buttercup_configured_feature",
                Feature.FLOWER
                        .withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockInit.BUTTERCUP.get().getDefaultState()), new SimpleBlockPlacer())).tries(64).build())
                        .withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
        MUD_LAKE_CONFIGURED_FEATURE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "mud_lake_configured_feature",
                Feature.LAKE
                        .withConfiguration(new BlockStateFeatureConfig(BlockInit.MUD_BLOCK.get().getDefaultState()))
                        .withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(E2JModConfig.mudLakeFrequency))));

    }

}
