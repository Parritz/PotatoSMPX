package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;
import perhaps.potatosmpx.enchantment.EnchantmentUtils;
import perhaps.potatosmpx.api.registry.EnchantmentBase;
import perhaps.potatosmpx.api.onBlockBreak.OnBlockBreak;

import java.util.*;

public class AutoSmeltEnchantment extends Enchantment {
    public AutoSmeltEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.RARE.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.RARE.getMaxCost(level); }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment other) {
        return super.checkCompatibility(other) && EnchantmentUtils.isEnchantmentAllowed(other, Enchantments.SILK_TOUCH, EnchantmentBase.SMOKE_MASTERY.get(), EnchantmentBase.BLAST_MASTERY.get());
    }

    private final Map<Item, ItemStack> entityRecipeCache = new HashMap<>();
    private final Map<Item, ItemStack> blockRecipeCache = new HashMap<>();

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getDirectEntity();
        Collection<ItemEntity> entityDrops = event.getDrops();

        if (!(attacker instanceof Player player)) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
        if (level <= 0 || heldItem.isEmpty() || world.isClientSide) return;

        for (ItemEntity itemEntity : entityDrops) {
            ItemStack drop = itemEntity.getItem();
            Item dropItem = drop.getItem();

            ItemStack result;
            if (entityRecipeCache.containsKey(dropItem)) {
                result = entityRecipeCache.get(dropItem);
            } else {
                SimpleContainer itemContainer = new SimpleContainer(drop);
                Optional<SmeltingRecipe> optional = world.getRecipeManager().getRecipeFor(RecipeType.SMELTING, itemContainer, world);

                if (optional.isPresent()) {
                    SmeltingRecipe furnaceRecipe = optional.get();
                    result = furnaceRecipe.getResultItem().copy();
                    entityRecipeCache.put(dropItem, result);
                } else {
                    result = null;
                }
            }

            if (result == null) return;
            ItemStack copyItem = result.copy();
            itemEntity.setItem(copyItem);
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        OnBlockBreak.listenBlockBreak(event);
    }
}
