package slexom.earthtojava.mobs.utils;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterMobEntity;
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

    private static void setSpawnBiomes(EntityType<?> entity, String[] spawnBiomes, int weight, int minGroupSize, int maxGroupSize, EntityClassification classification) {
        List<String> blackList = Arrays.stream(spawnBiomes).filter(id -> id.contains("!")).collect(Collectors.toList());
        List<String> spawnList = expandSpawnList(Arrays.stream(spawnBiomes).filter(id -> !id.contains("!")).collect(Collectors.toList()));
        blackList.replaceAll(s -> s.replace("!", ""));
        spawnList.removeAll(blackList);
        addEntityToBiomes(entity, spawnList, weight, minGroupSize, maxGroupSize, classification);
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
            Set<Biome> biomesInCategory = BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeCategory.toUpperCase()));
            for (Biome biome : biomesInCategory) {
                biomesFromDictionary.add(biome.getRegistryName().toString());
            }
        }
        return Stream.concat(biomes.stream(), biomesFromDictionary.stream()).collect(Collectors.toList());
    }

    private static boolean isBiomeCategory(String identifier) {
        return identifier.split(":").length == 1;
    }

    private static void addEntityToBiomes(EntityType<?> entity, List<String> spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn, EntityClassification classification) {
        for (String identifier : spawnBiomes) {
            Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(identifier));
            if (biome != null) {
                biome.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
            } else {
                System.out.println("[Earth2Java] Unable to find biome " + identifier + " while registering entity spawn");
            }
        }
    }

    public static <T extends AnimalEntity> void setCreatureSpawnBiomes(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        setSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn, EntityClassification.CREATURE);
    }

    public static <T extends WaterMobEntity> void setWaterCreatureSpawnBiomes(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        setSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn, EntityClassification.WATER_CREATURE);
    }

    public static <T extends MonsterEntity> void setMonsterSpawnBiomes(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        setSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn, EntityClassification.MONSTER);
    }

    public static <T extends MobEntity> void setMobSpawnBiomes(EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        setSpawnBiomes(entity, spawnBiomes, weight, minGroupCountIn, maxGroupCountIn, EntityClassification.MISC);
    }

    public static List<String> convertForConfig(String[] ary) {
        return Arrays.stream(ary).collect(Collectors.toList());
    }

}
