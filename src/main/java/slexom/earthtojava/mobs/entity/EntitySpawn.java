package slexom.earthtojava.mobs.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import slexom.earthtojava.mobs.config.E2JModConfig;
import slexom.earthtojava.mobs.entity.passive.GlowSquidEntity;
import slexom.earthtojava.mobs.init.EntityTypesInit;
import slexom.earthtojava.mobs.utils.BiomeSpawnHelper;

import java.util.List;

public class EntitySpawn {


    public static void init() {
    }

    public static <T extends AnimalEntity> void registerAnimalEntitySpawn(BiomeLoadingEvent event, EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        List<Biome> entitySpawnBiomes = BiomeSpawnHelper.getBiomesFromConfig(spawnBiomes);
        for (Biome biome : entitySpawnBiomes) {
            if (biome.getRegistryName() == event.getName()) {
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(entity, weight, minGroupCountIn, maxGroupCountIn));
                EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
            }
        }
    }

    public static <T extends MonsterEntity> void registerMonsterEntitySpawn(BiomeLoadingEvent event, EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        List<Biome> entitySpawnBiomes = BiomeSpawnHelper.getBiomesFromConfig(spawnBiomes);
        for (Biome biome : entitySpawnBiomes) {
            if (biome.getRegistryName() == event.getName()) {
                event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, weight, minGroupCountIn, maxGroupCountIn));
                EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawn);
            }
        }
    }

    public static <T extends MobEntity> void registerMobEntitySpawn(BiomeLoadingEvent event, EntityType<T> entity, String[] spawnBiomes, int weight, int minGroupCountIn, int maxGroupCountIn) {
        List<Biome> entitySpawnBiomes = BiomeSpawnHelper.getBiomesFromConfig(spawnBiomes);
        for (Biome biome : entitySpawnBiomes) {
            if (biome.getRegistryName() == event.getName()) {
                event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(entity, weight, minGroupCountIn, maxGroupCountIn));
                EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
            }
        }
    }

    public static void registerGlowingSquidSpawn(BiomeLoadingEvent event) {
        List<Biome> entitySpawnBiomes = BiomeSpawnHelper.getBiomesFromConfig(E2JModConfig.glowSquidSpawnBiomes.toArray(new String[0]));
        for (Biome biome : entitySpawnBiomes) {
            if (biome.getRegistryName() == event.getName()) {
                event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(EntityTypesInit.GLOW_SQUID_REGISTRY_OBJECT.get(), E2JModConfig.glowSquidWeight, E2JModConfig.glowSquidGroupMin, E2JModConfig.glowSquidGroupMax));
                EntitySpawnPlacementRegistry.register(EntityTypesInit.GLOW_SQUID_REGISTRY_OBJECT.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlowSquidEntity::canGlowingSquidSpawn);
            }
        }
     }

}
