package slexom.earthtojava.mobs.config;


import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;
import slexom.earthtojava.mobs.Earth2JavaMod;
import slexom.earthtojava.mobs.utils.BiomeSpawnHelper;

import java.text.MessageFormat;
import java.util.List;

@Config(name = Earth2JavaMod.MOD_ID)
public class ModConfig implements ConfigData {

    @ConfigEntry.Category("default")
    @ConfigEntry.Gui.CollapsibleObject
    public WanderingTraderConfig modWanderingTrader = new WanderingTraderConfig(false, 4, 20);

    @ConfigEntry.Category("generation")
    @ConfigEntry.Gui.CollapsibleObject
    public OreConfig rubyOre = new OreConfig(false, 0, 0, 2, 8);
    @ConfigEntry.Category("generation")
    @ConfigEntry.Gui.CollapsibleObject
    public MudLakeConfig mudLakeConfig = new MudLakeConfig(true, 25);

    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig albinoCow = new EntityConfig(BiomeSpawnHelper.ALBINO_COW_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig amberChicken = new EntityConfig(BiomeSpawnHelper.AMBER_CHICKEN_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig ashenCow = new EntityConfig(BiomeSpawnHelper.ASHEN_COW_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig boneSpider = new EntityConfig(BiomeSpawnHelper.BONE_SPIDER_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig boldStripedRabbit = new EntityConfig(BiomeSpawnHelper.BOLD_STRIPED_RABBIT_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig boulderingZombie = new EntityConfig(BiomeSpawnHelper.BOULDERING_ZOMBIE_SPAWN_BIOMES, 15);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig bronzedChicken = new EntityConfig(BiomeSpawnHelper.BRONZED_CHICKEN_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig cluckshroom = new EntityConfig(BiomeSpawnHelper.CLUCKSHROOM_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig cookieCow = new EntityConfig(BiomeSpawnHelper.COOKIE_COW_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig creamCow = new EntityConfig(BiomeSpawnHelper.CREAM_COW_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig dairyCow = new EntityConfig(BiomeSpawnHelper.DAIRY_COW_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig fancyChicken = new EntityConfig(BiomeSpawnHelper.FANCY_CHICKEN_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig fleckedSheep = new EntityConfig(BiomeSpawnHelper.FLECKED_SHEEP_SPAWN_BIOMES, 12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig freckledRabbit = new EntityConfig(BiomeSpawnHelper.FRECKLED_RABBIT_SPAWN_BIOMES, 4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig fuzzySheep = new EntityConfig(BiomeSpawnHelper.FUZZY_SHEEP_SPAWN_BIOMES, 12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public GlowSquidConfig glowSquid = new GlowSquidConfig(BiomeSpawnHelper.GLOW_SQUID_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig goldCrestedChicken = new EntityConfig(BiomeSpawnHelper.GOLD_CRESTED_CHICKEN_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig harelequinRabbit = new EntityConfig(BiomeSpawnHelper.HARELEQUIN_RABBIT_SPAWN_BIOMES, 4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig hornedSheep = new EntityConfig(BiomeSpawnHelper.HORNED_SHEEP_SPAWN_BIOMES, 12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig inkySheep = new EntityConfig(BiomeSpawnHelper.INKY_SHEEP_SPAWN_BIOMES, 12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig jollyLlama = new EntityConfig(BiomeSpawnHelper.JOLLY_LLAMA_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig jumboRabbit = new EntityConfig(BiomeSpawnHelper.JUMBO_RABBIT_SPAWN_BIOMES, 4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig lobberZombie = new EntityConfig(BiomeSpawnHelper.LOBBER_ZOMBIE_SPAWN_BIOMES, 15);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig longNosedSheep = new EntityConfig(BiomeSpawnHelper.LONG_NOSED_SHEEP_SPAWN_BIOMES, 12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig midnightChicken = new EntityConfig(BiomeSpawnHelper.MIDNIGHT_CHICKEN_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig moobloom = new EntityConfig(BiomeSpawnHelper.MOOBLOOM_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig moolip = new EntityConfig(BiomeSpawnHelper.MOOLIP_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig mottledPig = new EntityConfig(BiomeSpawnHelper.MOTTLED_PIG_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig muddyPig = new EntityConfig(BiomeSpawnHelper.MUDDY_PIG_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig muddyFootRabbit = new EntityConfig(BiomeSpawnHelper.MUDDY_FOOT_RABBIT_SPAWN_BIOMES, 4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig palePig = new EntityConfig(BiomeSpawnHelper.PALE_PIG_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig patchedSheep = new EntityConfig(BiomeSpawnHelper.PATCHED_SHEEP_SPAWN_BIOMES, 12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig piebaldPig = new EntityConfig(BiomeSpawnHelper.PIEBALD_PIG_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig pinkFootedPig = new EntityConfig(BiomeSpawnHelper.PINK_FOOTED_PIG_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig pintoCow = new EntityConfig(BiomeSpawnHelper.PINTO_COW_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig rainbowSheep = new EntityConfig(BiomeSpawnHelper.RAINBOW_SHEEP_SPAWN_BIOMES, 12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig rockySheep = new EntityConfig(BiomeSpawnHelper.ROCKY_SHEEP_SPAWN_BIOMES, 12);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig skeletonWolf = new EntityConfig(BiomeSpawnHelper.SKELETON_WOLF_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig skewbaldChicken = new EntityConfig(BiomeSpawnHelper.SKEWBALD_CHICKEN_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig sootyPig = new EntityConfig(BiomeSpawnHelper.SOOTY_PIG_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig spottedPig = new EntityConfig(BiomeSpawnHelper.SPOTTED_PIG_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig stormyChicken = new EntityConfig(BiomeSpawnHelper.STORMY_CHICKEN_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig sunsetCow = new EntityConfig(BiomeSpawnHelper.SUNSET_COW_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig tropicalSlime = new EntityConfig(BiomeSpawnHelper.TROPICAL_SLIME_SPAWN_BIOMES, 10);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig umbraCow = new EntityConfig(BiomeSpawnHelper.UMBRA_COW_SPAWN_BIOMES, 8);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig vestedRabbit = new EntityConfig(BiomeSpawnHelper.VESTED_RABBIT_SPAWN_BIOMES, 4);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig vilerWitch = new EntityConfig(BiomeSpawnHelper.VILER_WITCH_SPAWN_BIOMES, 1, 1, 1);
    @ConfigEntry.Category("entities")
    @ConfigEntry.Gui.CollapsibleObject
    public EntityConfig woolyCow = new EntityConfig(BiomeSpawnHelper.WOOLY_COW_SPAWN_BIOMES, 8);

    @Override
    public void validatePostLoad() throws ValidationException {
        if (modWanderingTrader.currencyItem == null) {
            modWanderingTrader.currencyItem = "earthtojavamobs:ruby";
            printCorrectionMessage("modWanderingTrader.currencyItem", "null", "earthtojavamobs:ruby");
        }
        if (glowSquid.spawnHeight == null) {
            GlowSquidConfig.SpawnHeightConfig spawnHeightConfig = new GlowSquidConfig.SpawnHeightConfig();
            spawnHeightConfig.spawnHeightMin = 20;
            spawnHeightConfig.spawnHeightMax = 45;
            glowSquid.spawnHeight = spawnHeightConfig;
            printCorrectionMessage("glowSquid.spawnHeight", "null", "\n" +
                    "\"spawnHeight\": {\n" +
                    "  \"spawnHeightMin\": 20,\n" +
                    "  \"spawnHeightMax\": 45\n" +
                    "}");
        }
    }

    private void printCorrectionMessage(String field, String old, String corrected) {
        System.out.println(MessageFormat.format("[Earth2Java] (Config) Corrected field {0}: was {1}, now {2}", field, old, corrected));
    }

    public static class EntityConfig {
        public boolean spawn;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int weight;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        public int groupMin;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        public int groupMax;
        public List<String> spawnBiomes;

        EntityConfig(String[] spawnBiomes, int weight, int groupMin, int groupMax) {
            this.spawnBiomes = BiomeSpawnHelper.convertForConfig(spawnBiomes);
            this.spawn = true;
            this.weight = weight;
            this.groupMin = groupMin;
            this.groupMax = groupMax;
        }

        EntityConfig(String[] spawnBiomes, int weight) {
            this(spawnBiomes, weight, 2, 4);
        }
    }

    public static class GlowSquidConfig {
        public boolean spawn;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int weight;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        public int groupMin;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        public int groupMax;

        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        public SpawnHeightConfig spawnHeight;
        public List<String> spawnBiomes;

        GlowSquidConfig(String[] spawnBiomes, int weight, int groupMin, int groupMax) {
            this.spawnBiomes = BiomeSpawnHelper.convertForConfig(spawnBiomes);
            this.spawn = true;
            this.weight = weight;
            this.groupMin = groupMin;
            this.groupMax = groupMax;
            this.spawnHeight = new SpawnHeightConfig();
        }

        GlowSquidConfig(String[] spawnBiomes, int weight) {
            this(spawnBiomes, weight, 2, 4);
        }

        public static class SpawnHeightConfig {
            @ConfigEntry.BoundedDiscrete(min = 1, max = 255)
            public int spawnHeightMin;
            @ConfigEntry.BoundedDiscrete(min = 1, max = 255)
            public int spawnHeightMax;

            SpawnHeightConfig() {
                this.spawnHeightMin = 20;
                this.spawnHeightMax = 45;
            }
        }
    }

    public static class OreConfig {
        public boolean canGenerate;
        public int topOffset;
        public int bottomOffset;
        public int count;
        public int maximum;

        OreConfig(boolean canGenerate, int bottomOffset, int topOffset, int count, int maximum) {
            this.canGenerate = canGenerate;
            this.topOffset = topOffset;
            this.bottomOffset = bottomOffset;
            this.count = count;
            this.maximum = maximum;
        }

    }

    public static class MudLakeConfig {
        public boolean canGenerate;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int mudLakeFrequency;

        MudLakeConfig(boolean canGenerate, int mudLakeFrequency) {
            this.canGenerate = canGenerate;
            this.mudLakeFrequency = mudLakeFrequency;
        }
    }

    public static class WanderingTraderConfig {
        public boolean canSpawn;
        @Comment("Game days between spawn")
        public int delay;
        public int chance;
        public String currencyItem;

        WanderingTraderConfig(boolean canSpawn, int delay, int chance, String currencyItem) {
            this.canSpawn = canSpawn;
            this.delay = delay;
            this.chance = chance;
            this.currencyItem = currencyItem;
        }

        WanderingTraderConfig(boolean canSpawn, int delay, int chance) {
            this.canSpawn = canSpawn;
            this.delay = delay;
            this.chance = chance;
            this.currencyItem = "earthtojavamobs:ruby";
        }

    }
}