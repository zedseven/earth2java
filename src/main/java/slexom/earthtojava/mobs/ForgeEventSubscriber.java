package slexom.earthtojava.mobs;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import slexom.earthtojava.mobs.config.E2JModConfig;
import slexom.earthtojava.mobs.entity.EntitySpawn;
import slexom.earthtojava.mobs.entity.passive.FurnaceGolemEntity;
import slexom.earthtojava.mobs.init.BlockInit;
import slexom.earthtojava.mobs.init.EntityTypesInit;
import slexom.earthtojava.mobs.init.FeatureInit;

import java.util.Random;
import java.util.function.Predicate;


@EventBusSubscriber(modid = EarthToJavaMobsMod.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {

    private static final Predicate<BlockState> IS_PUMPKIN = (blockState) -> blockState != null && (blockState.getBlock() == Blocks.CARVED_PUMPKIN || blockState.getBlock() == Blocks.JACK_O_LANTERN);

    @SubscribeEvent
    public static void onEggThrown(ProjectileImpactEvent.Throwable event) {
        ThrowableEntity throwable = event.getThrowable();
        World world = throwable.world;
        if (throwable instanceof EggEntity) {
            if (!world.isRemote) {
                if (new Random().nextInt(8) == 0) {
                    int i = 1;
                    if (new Random().nextInt(32) == 0) {
                        i = 4;
                    }
                    for (int j = 0; j < i; ++j) {
                        ChickenEntity chickenentity = getChickenEntity(world);
                        chickenentity.setGrowingAge(-24000);
                        chickenentity.setLocationAndAngles(throwable.getPosX(), throwable.getPosY(), throwable.getPosZ(), throwable.rotationYaw, 0.0F);
                        world.addEntity(chickenentity);
                    }
                }
                world.setEntityState(throwable, (byte) 3);
                throwable.remove();
            }
        }
    }

    private static ChickenEntity getChickenEntity(World world) {
        int chickenType = new Random().nextInt(6);
        ChickenEntity chickenentity;
        switch (chickenType) {
            case 0:
                chickenentity = EntityTypesInit.AMBER_CHICKEN_REGISTRY_OBJECT.get().create(world);
                break;
            case 2:
                chickenentity = EntityTypesInit.MIDNIGHT_CHICKEN_REGISTRY_OBJECT.get().create(world);
                break;
            case 4:
                chickenentity = EntityTypesInit.STORMY_CHICKEN_REGISTRY_OBJECT.get().create(world);
                break;
            default:
                chickenentity = EntityType.CHICKEN.create(world);
                break;
        }
        return chickenentity;
    }

    @SubscribeEvent
    public static void onCarvingMelon(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getPos();
        Hand hand = event.getHand();
        ItemStack itemstack = player.getHeldItem(hand);
        World worldIn = player.world;
        BlockState blockState = worldIn.getBlockState(pos);
        Block block = blockState.getBlock();
        if (itemstack.getItem() == Items.SHEARS && block == Blocks.MELON) {
            if (!worldIn.isRemote) {
                Direction direction = event.getFace();
                Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlockState(pos, BlockInit.CARVED_MELON.get().getDefaultState().with(CarvedPumpkinBlock.FACING, direction1), 11);
                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + 0.5D + (double) direction1.getXOffset() * 0.65D, (double) pos.getY() + 0.1D, (double) pos.getZ() + 0.5D + (double) direction1.getZOffset() * 0.65D, new ItemStack(Items.MELON_SEEDS, 4));
                itementity.setMotion(0.05D * (double) direction1.getXOffset() + worldIn.rand.nextDouble() * 0.02D, 0.05D, 0.05D * (double) direction1.getZOffset() + worldIn.rand.nextDouble() * 0.02D);
                worldIn.addEntity(itementity);
                itemstack.damageItem(1, player, (entity) -> {
                    entity.sendBreakAnimation(hand);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onFurnaceGolemCreation(BlockEvent.EntityPlaceEvent event) {
        BlockState blockState = event.getPlacedBlock();
        Block block = blockState.getBlock();
        if (block == Blocks.IRON_BLOCK || block == Blocks.BLAST_FURNACE || block == Blocks.JACK_O_LANTERN || block == Blocks.CARVED_PUMPKIN) {
            World world = event.getEntity().getEntityWorld();
            BlockPos pos = event.getPos();
            BlockPattern golemPattern = BlockPatternBuilder.start().aisle("~^~", "#@#", "~#~").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.IRON_BLOCK))).where('@', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.BLAST_FURNACE))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
            BlockPattern.PatternHelper patternHelper = golemPattern.match(world, pos);
            if (patternHelper != null) {
                for (int j = 0; j < golemPattern.getPalmLength(); ++j) {
                    for (int k = 0; k < golemPattern.getThumbLength(); ++k) {
                        CachedBlockInfo cachedBlockInfo = patternHelper.translateOffset(j, k, 0);
                        world.setBlockState(cachedBlockInfo.getPos(), Blocks.AIR.getDefaultState(), 2);
                        world.playEvent(2001, cachedBlockInfo.getPos(), Block.getStateId(cachedBlockInfo.getBlockState()));
                    }
                }
                BlockPos blockpos = patternHelper.translateOffset(1, 2, 0).getPos();
                FurnaceGolemEntity furnaceGolemEntity = EntityTypesInit.FURNACE_GOLEM_REGISTRY_OBJECT.get().create(world);
                furnaceGolemEntity.setPlayerCreated(true);
                furnaceGolemEntity.setLocationAndAngles((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.05D, (double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
                world.addEntity(furnaceGolemEntity);
                for (ServerPlayerEntity serverplayerentity1 : world.getEntitiesWithinAABB(ServerPlayerEntity.class, furnaceGolemEntity.getBoundingBox().grow(5.0D))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity1, furnaceGolemEntity);
                }
                for (int i1 = 0; i1 < golemPattern.getPalmLength(); ++i1) {
                    for (int j1 = 0; j1 < golemPattern.getThumbLength(); ++j1) {
                        CachedBlockInfo cachedblockinfo1 = patternHelper.translateOffset(i1, j1, 0);
                        world.func_230547_a_(cachedblockinfo1.getPos(), Blocks.AIR);
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void onBiomeLoadingRegisterFeatures(final BiomeLoadingEvent biome) {
        if (biome.getCategory() == Biome.Category.NETHER || biome.getCategory() == Biome.Category.THEEND) return;

        biome.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> FeatureInit.RUBY_ORE_CONFIGURED_FEATURE);
        biome.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> FeatureInit.BUTTERCUP_CONFIGURED_FEATURE);
        biome.getGeneration().getFeatures(GenerationStage.Decoration.LAKES).add(() -> FeatureInit.MUD_LAKE_CONFIGURED_FEATURE);
    }

    @SubscribeEvent
    public void onBiomeLoadingRegisterSpawners(final BiomeLoadingEvent event) {
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.AMBER_CHICKEN_REGISTRY_OBJECT.get(), E2JModConfig.amberChickenSpawnBiomes.toArray(new String[0]), E2JModConfig.amberChickenWeight, E2JModConfig.amberChickenGroupMin, E2JModConfig.amberChickenGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.ASHEN_COW_REGISTRY_OBJECT.get(), E2JModConfig.ashenCowSpawnBiomes.toArray(new String[0]), E2JModConfig.ashenCowWeight, E2JModConfig.ashenCowGroupMin, E2JModConfig.ashenCowGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.CLUCKSHROOM_REGISTRY_OBJECT.get(), E2JModConfig.cluckshroomSpawnBiomes.toArray(new String[0]), E2JModConfig.cluckshroomWeight, E2JModConfig.cluckshroomGroupMin, E2JModConfig.cluckshroomGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.FLECKED_SHEEP_REGISTRY_OBJECT.get(), E2JModConfig.fleckedSheepSpawnBiomes.toArray(new String[0]), E2JModConfig.fleckedSheepWeight, E2JModConfig.fleckedSheepGroupMin, E2JModConfig.fleckedSheepGroupMax);
        EntitySpawn.registerGlowingSquidSpawn(event);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.HARELEQUIN_RABBIT_REGISTRY_OBJECT.get(), E2JModConfig.harelequinRabbitSpawnBiomes.toArray(new String[0]), E2JModConfig.harelequinRabbitWeight, E2JModConfig.harelequinRabbitGroupMin, E2JModConfig.harelequinRabbitGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.HORNED_SHEEP_REGISTRY_OBJECT.get(), E2JModConfig.hornedSheepSpawnBiomes.toArray(new String[0]), E2JModConfig.hornedSheepWeight, E2JModConfig.hornedSheepGroupMin, E2JModConfig.hornedSheepGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.INKY_SHEEP_REGISTRY_OBJECT.get(), E2JModConfig.inkySheepSpawnBiomes.toArray(new String[0]), E2JModConfig.inkySheepWeight, E2JModConfig.inkySheepGroupMin, E2JModConfig.inkySheepGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.MIDNIGHT_CHICKEN_REGISTRY_OBJECT.get(), E2JModConfig.midnightChickenSpawnBiomes.toArray(new String[0]), E2JModConfig.midnightChickenWeight, E2JModConfig.midnightChickenGroupMin, E2JModConfig.midnightChickenGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.MOOBLOOM_REGISTRY_OBJECT.get(), E2JModConfig.moobloomSpawnBiomes.toArray(new String[0]), E2JModConfig.moobloomWeight, E2JModConfig.moobloomGroupMin, E2JModConfig.moobloomGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.MUDDY_FOOT_RABBIT_REGISTRY_OBJECT.get(), E2JModConfig.muddyFootRabbitSpawnBiomes.toArray(new String[0]), E2JModConfig.muddyFootRabbitWeight, E2JModConfig.muddyFootRabbitGroupMin, E2JModConfig.muddyFootRabbitGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.MUDDY_PIG_REGISTRY_OBJECT.get(), E2JModConfig.muddyPigSpawnBiomes.toArray(new String[0]), E2JModConfig.muddyPigWeight, E2JModConfig.muddyPigGroupMin, E2JModConfig.muddyPigGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.PALE_PIG_REGISTRY_OBJECT.get(), E2JModConfig.palePigSpawnBiomes.toArray(new String[0]), E2JModConfig.palePigWeight, E2JModConfig.palePigGroupMin, E2JModConfig.palePigGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.PIEBALD_PIG_REGISTRY_OBJECT.get(), E2JModConfig.piebaldPigSpawnBiomes.toArray(new String[0]), E2JModConfig.piebaldPigWeight, E2JModConfig.piebaldPigGroupMin, E2JModConfig.piebaldPigGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.PINK_FOOTED_PIG_REGISTRY_OBJECT.get(), E2JModConfig.pinkFootedPigSpawnBiomes.toArray(new String[0]), E2JModConfig.pinkFootedPigWeight, E2JModConfig.pinkFootedPigGroupMin, E2JModConfig.pinkFootedPigGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.ROCKY_SHEEP_REGISTRY_OBJECT.get(), E2JModConfig.rockySheepSpawnBiomes.toArray(new String[0]), E2JModConfig.rockySheepWeight, E2JModConfig.rockySheepGroupMin, E2JModConfig.rockySheepGroupMax);
        EntitySpawn.registerMonsterEntitySpawn(event, EntityTypesInit.SKELETON_WOLF_REGISTRY_OBJECT.get(), E2JModConfig.skeletonWolfSpawnBiomes.toArray(new String[0]), E2JModConfig.skeletonWolfWeight, E2JModConfig.skeletonWolfGroupMin, E2JModConfig.skeletonWolfGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.SPOTTED_PIG_REGISTRY_OBJECT.get(), E2JModConfig.spottedPigSpawnBiomes.toArray(new String[0]), E2JModConfig.spottedPigWeight, E2JModConfig.spottedPigGroupMin, E2JModConfig.spottedPigGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.STORMY_CHICKEN_REGISTRY_OBJECT.get(), E2JModConfig.stormyChickenSpawnBiomes.toArray(new String[0]), E2JModConfig.stormyChickenWeight, E2JModConfig.stormyChickenGroupMin, E2JModConfig.stormyChickenGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.SUNSET_COW_REGISTRY_OBJECT.get(), E2JModConfig.sunsetCowSpawnBiomes.toArray(new String[0]), E2JModConfig.sunsetCowWeight, E2JModConfig.sunsetCowGroupMin, E2JModConfig.sunsetCowGroupMax);
        EntitySpawn.registerMobEntitySpawn(event, EntityTypesInit.TROPICAL_SLIME_REGISTRY_OBJECT.get(), E2JModConfig.tropicalSlimeSpawnBiomes.toArray(new String[0]), E2JModConfig.tropicalSlimeWeight, E2JModConfig.tropicalSlimeGroupMin, E2JModConfig.tropicalSlimeGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.VESTED_RABBIT_REGISTRY_OBJECT.get(), E2JModConfig.vestedRabbitSpawnBiomes.toArray(new String[0]), E2JModConfig.vestedRabbitWeight, E2JModConfig.vestedRabbitGroupMin, E2JModConfig.vestedRabbitGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.WOOLY_COW_REGISTRY_OBJECT.get(), E2JModConfig.woolyCowSpawnBiomes.toArray(new String[0]), E2JModConfig.woolyCowWeight, E2JModConfig.woolyCowGroupMin, E2JModConfig.woolyCowGroupMax);
        //  EntitySpawn.registerMobEntitySpawn(EntityTypesInit.FURNACE_GOLEM_REGISTRY_OBJECT.get(), E2JModConfig.furnaceGolemSpawnBiomes.toArray(new String[0]), E2JModConfig.furnaceGolemWeight, E2JModConfig.furnaceGolemGroupMin, E2JModConfig.furnaceGolemGroupMax);
        // EntitySpawn.registerMobEntitySpawn(EntityTypesInit.MELON_GOLEM_REGISTRY_OBJECT.get(), E2JModConfig.melonGolemSpawnBiomes.toArray(new String[0]), E2JModConfig.melonGolemWeight, E2JModConfig.melonGolemGroupMin, E2JModConfig.melonGolemGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.ALBINO_COW_REGISTRY_OBJECT.get(), E2JModConfig.albinoCowSpawnBiomes.toArray(new String[0]), E2JModConfig.albinoCowWeight, E2JModConfig.albinoCowGroupMin, E2JModConfig.albinoCowGroupMax);
        EntitySpawn.registerMonsterEntitySpawn(event, EntityTypesInit.BONE_SPIDER_REGISTRY_OBJECT.get(), E2JModConfig.boneSpiderSpawnBiomes.toArray(new String[0]), E2JModConfig.boneSpiderWeight, E2JModConfig.boneSpiderGroupMin, E2JModConfig.boneSpiderGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.JUMBO_RABBIT_REGISTRY_OBJECT.get(), E2JModConfig.jumboRabbitSpawnBiomes.toArray(new String[0]), E2JModConfig.jumboRabbitWeight, E2JModConfig.jumboRabbitGroupMin, E2JModConfig.jumboRabbitGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.JOLLY_LLAMA_REGISTRY_OBJECT.get(), E2JModConfig.jollyLlamaSpawnBiomes.toArray(new String[0]), E2JModConfig.jollyLlamaWeight, E2JModConfig.jollyLlamaGroupMin, E2JModConfig.jollyLlamaGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.RAINBOW_SHEEP_REGISTRY_OBJECT.get(), E2JModConfig.rainbowSheepSpawnBiomes.toArray(new String[0]), E2JModConfig.rainbowSheepWeight, E2JModConfig.rainbowSheepGroupMin, E2JModConfig.rainbowSheepGroupMax);
        EntitySpawn.registerAnimalEntitySpawn(event, EntityTypesInit.BRONZED_CHICKEN_REGISTRY_OBJECT.get(), E2JModConfig.bronzedChickenSpawnBiomes.toArray(new String[0]), E2JModConfig.bronzedChickenWeight, E2JModConfig.bronzedChickenGroupMin, E2JModConfig.bronzedChickenGroupMax);

    }
}