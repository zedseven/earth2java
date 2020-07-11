package slexom.earthtojava.mobs.entity.merchant.villager;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import slexom.earthtojava.mobs.init.ItemInit;

import java.util.Random;

/**
 * [ minecraft:fire_resistance=net.minecraft.potion.Potion@3ae555b3, minecraft:harming=net.minecraft.potion.Potion@c12a5f5, minecraft:healing=net.minecraft.potion.Potion@56e7875f, minecraft:invisibility=net.minecraft.potion.Potion@5b637df0, minecraft:leaping=net.minecraft.potion.Potion@73e0ddf1, minecraft:long_fire_resistance=net.minecraft.potion.Potion@4f1f084b, minecraft:long_invisibility=net.minecraft.potion.Potion@1d62f1b9, minecraft:long_leaping=net.minecraft.potion.Potion@70ccfa27, minecraft:long_night_vision=net.minecraft.potion.Potion@701e763c, minecraft:long_poison=net.minecraft.potion.Potion@42544f53, minecraft:long_regeneration=net.minecraft.potion.Potion@cc1b3ed, minecraft:long_slow_falling=net.minecraft.potion.Potion@3fc5bea0, minecraft:long_slowness=net.minecraft.potion.Potion@3d2bb383, minecraft:long_strength=net.minecraft.potion.Potion@ffa0a1, minecraft:long_swiftness=net.minecraft.potion.Potion@6c08e161, minecraft:long_turtle_master=net.minecraft.potion.Potion@711a2559, minecraft:long_water_breathing=net.minecraft.potion.Potion@76774425, minecraft:long_weakness=net.minecraft.potion.Potion@64ba9d0a, minecraft:luck=net.minecraft.potion.Potion@19bdd394, minecraft:mundane=net.minecraft.potion.Potion@25e60b96, minecraft:night_vision=net.minecraft.potion.Potion@65b17ba7, minecraft:poison=net.minecraft.potion.Potion@2abf341f, minecraft:regeneration=net.minecraft.potion.Potion@5f8d7bcf, minecraft:slow_falling=net.minecraft.potion.Potion@1931b94e, minecraft:slowness=net.minecraft.potion.Potion@4b3a6b0f, minecraft:strength=net.minecraft.potion.Potion@3418e6ee, minecraft:strong_harming=net.minecraft.potion.Potion@40088e3e, minecraft:strong_healing=net.minecraft.potion.Potion@2ee8dd76, minecraft:strong_leaping=net.minecraft.potion.Potion@30008533, minecraft:strong_poison=net.minecraft.potion.Potion@5b7077e1, minecraft:strong_regeneration=net.minecraft.potion.Potion@6c0f5bf1, minecraft:strong_slowness=net.minecraft.potion.Potion@16834eae, minecraft:strong_strength=net.minecraft.potion.Potion@1202588, minecraft:strong_swiftness=net.minecraft.potion.Potion@7c50799e, minecraft:strong_turtle_master=net.minecraft.potion.Potion@114d3752, minecraft:swiftness=net.minecraft.potion.Potion@5815ce4d, minecraft:thick=net.minecraft.potion.Potion@7ecab39c, minecraft:turtle_master=net.minecraft.potion.Potion@69605969, minecraft:water=net.minecraft.potion.Potion@17e66e6c, minecraft:water_breathing=net.minecraft.potion.Potion@1db7edeb, minecraft:weakness=net.minecraft.potion.Potion@17e6368e]
 */
public class E2JWanderingTraderEntity extends WanderingTraderEntity {

    public static final Int2ObjectMap<VillagerTrades.ITrade[]> TRADES = new Int2ObjectOpenHashMap<>(ImmutableMap.of(
            1, new VillagerTrades.ITrade[]{
                    new PotionForRubiesTrade("minecraft:long_fire_resistance", 4, 1, 6, 1, 0.05F)},
            2, new VillagerTrades.ITrade[]{new ItemsForRubiesTrade(ItemInit.MUD_BUCKET.get(), 1, 1, 4, 1)}
    ));

    public E2JWanderingTraderEntity(EntityType<? extends WanderingTraderEntity> type, World worldIn) {
        super(type, worldIn);
    }


    protected void populateTradeData() {
        VillagerTrades.ITrade[] avillagertrades$itrade = TRADES.get(1);
        VillagerTrades.ITrade[] avillagertrades$itrade1 = TRADES.get(2);
        if (avillagertrades$itrade != null && avillagertrades$itrade1 != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addTrades(merchantoffers, avillagertrades$itrade, 8);
            int i = this.rand.nextInt(avillagertrades$itrade1.length);
            VillagerTrades.ITrade villagertrades$itrade = avillagertrades$itrade1[i];
            MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.rand);
            if (merchantoffer != null) {
                merchantoffers.add(merchantoffer);
            }

        }
    }


    static class ItemsForRubiesTrade implements VillagerTrades.ITrade {
        private final ItemStack itemStack;
        private final int currencyAmount;
        private final int sellingItemAmount;
        private final int maxInStock;
        private final int givenExp;
        private final float priceMultiplier;

        public ItemsForRubiesTrade(Block block, int currencyAmount, int sellingItemAmount, int maxInStock, int givenExp) {
            this(new ItemStack(block), currencyAmount, sellingItemAmount, maxInStock, givenExp);
        }

        public ItemsForRubiesTrade(Item item, int currencyAmount, int sellingItemAmount, int givenExp) {
            this(new ItemStack(item), currencyAmount, sellingItemAmount, 12, givenExp);
        }

        public ItemsForRubiesTrade(Item item, int currencyAmount, int sellingItemAmount, int maxInStock, int givenExp) {
            this(new ItemStack(item), currencyAmount, sellingItemAmount, maxInStock, givenExp);
        }

        public ItemsForRubiesTrade(ItemStack itemStack, int currencyAmount, int sellingItemAmount, int maxInStock, int givenExp) {
            this(itemStack, currencyAmount, sellingItemAmount, maxInStock, givenExp, 0.05F);
        }

        public ItemsForRubiesTrade(ItemStack itemStack, int currencyAmount, int sellingItemAmount, int maxInStock, int givenExp, float priceMultiplier) {
            this.itemStack = itemStack;
            this.currencyAmount = currencyAmount;
            this.sellingItemAmount = sellingItemAmount;
            this.maxInStock = maxInStock;
            this.givenExp = givenExp;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(ItemInit.RUBY.get(), this.currencyAmount), new ItemStack(this.itemStack.getItem(), this.sellingItemAmount), this.maxInStock, this.givenExp, this.priceMultiplier);
        }
    }

    static class PotionForRubiesTrade implements VillagerTrades.ITrade {
        private final ItemStack itemStack;
        private final String potionType;
        private final int currencyAmount;
        private final int sellingItemAmount;
        private final int maxInStock;
        private final int givenExp;
        private final float priceMultiplier;

        public PotionForRubiesTrade(String potionType, int currencyAmount, int sellingItemAmount, int maxInStock, int givenExp, float priceMultiplier) {
            this.itemStack = new ItemStack(Items.POTION);
            this.potionType = potionType;
            this.currencyAmount = currencyAmount;
            this.sellingItemAmount = sellingItemAmount;
            this.maxInStock = maxInStock;
            this.givenExp = givenExp;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            Potion potion = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(this.potionType));
            ItemStack potionItemStack = PotionUtils.addPotionToItemStack(new ItemStack(this.itemStack.getItem(), sellingItemAmount), potion);
            return new MerchantOffer(new ItemStack(ItemInit.RUBY.get(), this.currencyAmount), potionItemStack, this.maxInStock, this.givenExp, this.priceMultiplier);
        }
    }

}
