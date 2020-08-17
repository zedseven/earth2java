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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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

    public static String[] getBiomesList(String[]... identifiers) {
        List<String> biomes = new ArrayList<>();
        List<String> types = new ArrayList<>();
        String[] flatted = Stream.of(identifiers).flatMap(Stream::of).toArray(String[]::new);
        for (String identifier : flatted) {
            String[] splitted = identifier.split(":");
            if (splitted.length == 2) {
                biomes.add(identifier);
            }
            if (splitted.length == 1) {
                types.add(identifier);
            }
        }
        return Stream.concat(biomes.stream(), types.stream()).toArray(String[]::new);
    }

    public static String[] getBiomesListFromBiomes(String[]... biomes) {
        return Stream.of(biomes).flatMap(Stream::of).toArray(String[]::new);
    }

    public static String[] getBiomesListFromBiomeTypes(BiomeDictionary.Type... types) {
        return Stream.of(types).flatMap(Stream::of).map(BiomeDictionary.Type::getName).toArray(String[]::new);
    }

    private static boolean isOverworld(Biome biome) {
        Set<Biome> biomesNether = BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER);
        Set<Biome> biomesEnd = BiomeDictionary.getBiomes(BiomeDictionary.Type.END);
        for (Biome biomeNether : biomesNether) {
            if (biome == biomeNether) {
                return false;
            }
        }
        for (Biome biomeEnd : biomesEnd) {
            if (biome == biomeEnd) {
                return false;
            }
        }
        return true;
    }

    private static void setSpawnBiomes(EntityType<?> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn, EntityClassification classification) {
        for (String identifier : spawnBiomes) {
            String[] splitted = identifier.split(":");
            if (splitted.length == 2) {
                Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(identifier));
                if (biome != null) {
                    biome.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
                }
            }
            if (splitted.length == 1) {
                Set<Biome> biomes = BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(identifier.toUpperCase()));
                for (Biome biome : biomes) {
                    if (isOverworld(biome)) {
                        biome.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
                    }
                }
            }
        }
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            boolean biomeCriteria = Arrays.asList(spawnBiomes).contains(ForgeRegistries.BIOMES.getKey(biome).toString());
            if (!biomeCriteria)
                continue;
            biome.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
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
