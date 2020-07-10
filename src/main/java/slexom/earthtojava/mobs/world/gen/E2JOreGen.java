package slexom.earthtojava.mobs.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import slexom.earthtojava.mobs.init.BlockInit;

public class E2JOreGen {

    public static void generateOre() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            biome.addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.RUBY_ORE.get().getDefaultState(), 8))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 0, 0, 16)))
            );
        }
    }

}
