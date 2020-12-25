package slexom.earthtojava.mobs.world.spawner;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import slexom.earthtojava.mobs.config.E2JModConfig;
import slexom.earthtojava.mobs.entity.merchant.villager.E2JWanderingTraderEntity;
import slexom.earthtojava.mobs.init.EntityTypesInit;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class E2JWanderingTraderSpawner {

    private final Random random = new Random();
    private final int DAY = 24000;
    private ServerWorld world;
    private IServerWorldInfo iServerWorldInfo;

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event) {
        if (E2JModConfig.canWanderingTraderSpawn) {
            if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
                world = (ServerWorld) event.world;
                if (world.getGameRules().getBoolean(GameRules.DO_TRADER_SPAWNING) && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
                    iServerWorldInfo = world.getServer().getServerConfiguration().getServerWorldInfo();
                    int CHANCE = E2JModConfig.wanderingTraderChance;
                    int DELAY = E2JModConfig.wanderingTraderDelay;
                    long dayTime = iServerWorldInfo.getDayTime();
                    if (dayTime % ((long) (DAY * DELAY)) == 1500) {
                        if (random.nextInt(100) < CHANCE) {
                            spawnTrader(this.world);
                        }
                    }
                }
            }
        }
    }

    private void spawnTrader(ServerWorld world) {
        PlayerEntity playerentity = world.getRandomPlayer();
        if (playerentity != null) {
            BlockPos blockpos = playerentity.getPosition();
            PointOfInterestManager pointofinterestmanager = world.getPointOfInterestManager();
            Optional<BlockPos> optional = pointofinterestmanager.find(PointOfInterestType.MEETING.getPredicate(), (p_221241_0_) -> true, blockpos, 48, PointOfInterestManager.Status.ANY);
            BlockPos blockpos1 = optional.orElse(blockpos);
            BlockPos blockpos2 = this.getLlamaSpawnPosition(world, blockpos1, 48);
            if (blockpos2 != null && this.isValid(world, blockpos2)) {
                if (world.func_242406_i(blockpos2).equals(Optional.of(Biomes.THE_VOID))) {
                    return  ;
                }
                E2JWanderingTraderEntity traderEntity = EntityTypesInit.WANDERING_TRADER_REGISTRY_OBJECT.get().spawn(this.world, (CompoundNBT) null, (ITextComponent) null, (PlayerEntity) null, blockpos2, SpawnReason.EVENT, false, false);
                if (traderEntity != null) {
                    for (int j = 0; j < 2; ++j) {
                        this.spawnTraderLlama(traderEntity);
                    }
                    iServerWorldInfo.setWanderingTraderID(traderEntity.getUniqueID()); // setWanderingTraderId in 115
                    traderEntity.setDespawnDelay(32000);
                    traderEntity.setWanderTarget(blockpos1);
                    traderEntity.setHomePosAndDistance(blockpos1, 16);
                }
            }
        }
    }

    private void spawnTraderLlama(WanderingTraderEntity traderEntity) {
        BlockPos blockpos = this.getLlamaSpawnPosition(traderEntity.world, traderEntity.getPosition(), 4);
        if (blockpos != null) {
            TraderLlamaEntity traderLlamaEntity = EntityType.TRADER_LLAMA.spawn(this.world, (CompoundNBT) null, (ITextComponent) null, (PlayerEntity) null, blockpos, SpawnReason.EVENT, false, false);
            if (traderLlamaEntity != null) {
                traderLlamaEntity.setLeashHolder(traderEntity, true);
            }
        }
    }

    @Nullable
    private BlockPos getLlamaSpawnPosition(IWorldReader world, BlockPos pos, int radius) {
        BlockPos blockPos = null;
        for (int i = 0; i < 10; ++i) {
            int x = pos.getX() + this.random.nextInt(radius * 2) - radius;
            int z = pos.getZ() + this.random.nextInt(radius * 2) - radius;
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
            BlockPos blockPos1 = new BlockPos(x, y, z);
            if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.world, blockPos1, EntityTypesInit.WANDERING_TRADER_REGISTRY_OBJECT.get())) {
                blockPos = blockPos1;
                break;
            }
        }
        return blockPos;
    }

    private boolean isValid(IBlockReader blockReader, BlockPos pos) {
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos, pos.add(1, 2, 1))) {
            if (!blockReader.getBlockState(blockpos).getCollisionShape(this.world, blockpos).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
