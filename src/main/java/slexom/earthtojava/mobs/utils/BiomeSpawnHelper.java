package slexom.earthtojava.mobs.utils;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class BiomeSpawnHelper {

    public static final String[] ALBINO_COW_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN);
    public static final String[] AMBER_CHICKEN_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.DRY, BiomeDictionary.Type.SAVANNA);
    public static final String[] ASHEN_COW_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.MOUNTAIN);
    public static final String[] BONE_SPIDER_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.MESA);
    public static final String[] BRONZED_CHICKEN_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN);
    public static final String[] CLUCKSHROOM_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.MUSHROOM);
    public static final String[] FLECKED_SHEEP_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.FOREST);
    public static final String[] FURNACE_GOLEM_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.HOT);
    public static final String[] GLOW_SQUID_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.RIVER, BiomeDictionary.Type.SWAMP);
    public static final String[] HARELEQUIN_RABBIT_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS);
    public static final String[] HORNED_SHEEP_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN);
    public static final String[] INKY_SHEEP_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN);
    public static final String[] JOLLY_LLAMA_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.SNOWY);
    public static final String[] JUMBO_RABBIT_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS);
    public static final String[] MELON_GOLEM_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.SNOWY);
    public static final String[] MIDNIGHT_CHICKEN_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS);
    public static final String[] MOOBLOOM_SPAWN_BIOMES = getBiomesListFromBiomes(new String[]{"minecraft:flower_forest"});
    public static final String[] MUDDY_FOOT_RABBIT_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS);
    public static final String[] MUDDY_PIG_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.RIVER);
    public static final String[] PALE_PIG_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.SNOWY);
    public static final String[] PIEBALD_PIG_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SAVANNA);
    public static final String[] PINK_FOOTED_PIG_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN);
    public static final String[] RAINBOW_SHEEP_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.FOREST);
    public static final String[] ROCKY_SHEEP_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN);
    public static final String[] SKELETON_WOLF_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.MESA);
    public static final String[] SPOTTED_PIG_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.SWAMP);
    public static final String[] STORMY_CHICKEN_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD);
    public static final String[] SUNSET_COW_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.SAVANNA);
    public static final String[] TROPICAL_SLIME_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.BEACH);
    public static final String[] VESTED_RABBIT_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.PLAINS);
    public static final String[] WOOLY_COW_SPAWN_BIOMES = getBiomesListFromBiomeTypes(BiomeDictionary.Type.SNOWY);

    private BiomeSpawnHelper() {
    }

    public static String[] getBiomesListFromBiomes(String[]... biomes) {
        return Stream.of(biomes).flatMap(Stream::of).toArray(String[]::new);
    }

    public static String[] getBiomesListFromBiomeTypes(BiomeDictionary.Type... types) {
        return Stream.of(types).flatMap(Stream::of).map(BiomeDictionary.Type::getName).toArray(String[]::new);
    }

    public static List<Biome> getBiomesFromConfig(String[] spawnBiomes) {
        List<String> blackList = Arrays.stream(spawnBiomes).filter(id -> id.contains("!")).collect(Collectors.toList());
        List<String> spawnList = expandSpawnList(Arrays.stream(spawnBiomes).filter(id -> !id.contains("!")).collect(Collectors.toList()));
        blackList.replaceAll(s -> s.replace("!", ""));
        spawnList.removeAll(blackList);
        return spawnList.stream().map( identifier -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(identifier))).collect(Collectors.toList());
    }

    private static List<String> expandSpawnList(List<String> spawnList) {
        List<String> biomes = new ArrayList<>(Collections.emptyList());
        List<String> biomeCategories = new ArrayList<>(Collections.emptyList());
        List<String> biomesFromDictionary = new ArrayList<>(Collections.emptyList());
        spawnList.forEach(identifier -> {
            if (isBiomeCategory(identifier)) {
                biomeCategories.add(identifier);
            } else {
                biomes.add(identifier);
            }
        });
        for (String biomeCategory : biomeCategories) {
            Set<RegistryKey<Biome>> biomesInCategory = BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeCategory.toUpperCase()));
            for (RegistryKey<Biome> biome : biomesInCategory) {
                biomesFromDictionary.add(biome.getRegistryName().toString());
            }
        }
        return Stream.concat(biomes.stream(), biomesFromDictionary.stream()).collect(Collectors.toList());
    }

    private static boolean isBiomeCategory(String identifier) {
        return identifier.split(":").length == 1;
    }

    public static List<String> convertForConfig(String[] ary) {
        return Arrays.stream(ary).collect(Collectors.toList());
    }

}
