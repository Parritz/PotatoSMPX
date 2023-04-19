package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.potatosmpx.enchantment.ModEnchantments;

import java.util.*;

public class BlastMasteryEnchantment extends Enchantment {
    public BlastMasteryEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int level) {
        return level * 16;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 50;
    }

    private final Map<Item, ItemStack> recipeCache = new HashMap<>();

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other) && other != ModEnchantments.AUTO_SMELT.get();
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();

        if (!heldItem.isEmpty()) {
            int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
            if (level == 0) {
                return;
            }

            Level world = player.level;
            if (!world.isClientSide) {
                event.setCanceled(true);

                BlockState state = event.getState();
                List<ItemStack> drops = Block.getDrops(state, (ServerLevel) world, event.getPos(), null, player, heldItem);

                System.out.println(drops.get(0));
                System.out.println(drops.get(0).getCount());
                List<ItemStack> newDrops = new ArrayList<>();

                for (ItemStack drop : drops) {
                    Item dropItem = drop.getItem();

                    ItemStack result;
                    if (recipeCache.containsKey(dropItem)) {
                        result = recipeCache.get(dropItem);
                    } else {
                        SimpleContainer itemContainer = new SimpleContainer(drop);
                        Optional<BlastingRecipe> optional = world.getRecipeManager().getRecipeFor(RecipeType.BLASTING, itemContainer, world);

                        if (optional.isPresent()) {
                            BlastingRecipe blastingRecipe = optional.get();
                            result = blastingRecipe.getResultItem().copy();
                            recipeCache.put(dropItem, result);
                        } else {
                            result = null;
                        }
                    }

                    if (result != null) {
                        ItemStack copyItem = result.copy();
                        int count = drop.getCount();

                        copyItem.setCount(count);
                        if (world.getRandom().nextFloat() <= (0.05f * level)) {
                            int extraDupe = world.getRandom().nextFloat() <= (0.05f * level) ? 4 : 2;
                            if (extraDupe == 4 && (world.getRandom().nextFloat() <= 0.025f * level)) {
                                extraDupe = 8;
                            }

                            copyItem.setCount(count * extraDupe);
                        }

                        newDrops.add(copyItem);
                    } else {
                        newDrops.add(drop);
                    }
                }

                // Remove the original block without dropping items
                world.setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);

                // Spawn the new drops
                for (ItemStack newDrop : newDrops) {
                    ItemEntity itemEntity = new ItemEntity(world, event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, newDrop);
                    world.addFreshEntity(itemEntity);
                }

                int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, heldItem);
                int silkTouchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, heldItem);
                // Spawn experience orbs
                state.getBlock().popExperience((ServerLevel) world, event.getPos(), state.getExpDrop(world, event.getPos(), fortuneLevel, silkTouchLevel));
            }
        }
    }
}
