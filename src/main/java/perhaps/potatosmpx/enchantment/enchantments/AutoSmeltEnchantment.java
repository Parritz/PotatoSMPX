package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
    public int getMinCost(int level) {
        return 14;
    }

    @Override
    public int getMaxCost(int level) {
        return 64;
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
        Player player = event.getPlayer();
        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
        if (level <= 0 || world.isClientSide) return;

        BlockState state = event.getState();
        List<ItemStack> drops = Block.getDrops(state, (ServerLevel) world, event.getPos(), null, player, heldItem);
        List<ItemStack> newDrops = new ArrayList<>();

        for (ItemStack drop : drops) {
            Item dropItem = drop.getItem();

            ItemStack result;
            if (blockRecipeCache.containsKey(dropItem)) {
                result = blockRecipeCache.get(dropItem);
            } else {
                SimpleContainer itemContainer = new SimpleContainer(drop);
                Optional<SmeltingRecipe> optional = world.getRecipeManager().getRecipeFor(RecipeType.SMELTING, itemContainer, world);

                if (optional.isPresent()) {
                    SmeltingRecipe smeltingRecipe = optional.get();
                    result = smeltingRecipe.getResultItem().copy();
                    blockRecipeCache.put(dropItem, result);
                } else {
                    result = null;
                }
            }

            if (result != null) {
                ItemStack copyItem = result.copy();
                copyItem.setCount(drop.getCount());

                newDrops.add(copyItem);
            } else {
                newDrops.add(drop);
            }
        }

        world.destroyBlock(event.getPos(), false); // Remove the original block without dropping items
        for (ItemStack newDrop : newDrops) {
            ItemEntity itemEntity = new ItemEntity(world, event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, newDrop);
            world.addFreshEntity(itemEntity);
        }

        int xpToDrop = event.getExpToDrop();
        if (xpToDrop <= 0) return;
        ExperienceOrb experienceOrb = new ExperienceOrb(world, event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, xpToDrop);
        world.addFreshEntity(experienceOrb);
    }
}
