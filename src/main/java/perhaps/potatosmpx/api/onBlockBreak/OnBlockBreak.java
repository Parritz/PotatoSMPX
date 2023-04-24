package perhaps.potatosmpx.api.onBlockBreak;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.config.WeightedItems;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import javax.annotation.Nullable;
import java.util.*;

import static perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.BountifulHarvest.bountifulHarvestEnchantment;
import static perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.CropCompressor.cropCompressorEnchantment;
import static perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.FarmersDelight.farmersDelightEnchantment;
import static perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.MomentumBreak.momentumEnchantment;
import static perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.Quake.quakeEnchantment;
import static perhaps.potatosmpx.api.onBlockBreak.listeners.enchantments.Replenish.replenishEnchantment;

public class OnBlockBreak {
    private static final Map<BlockState, List<ItemStack>> blockMap = new HashMap<>();
    private static final Map<BlockState, BlockState> blockStateMap = new HashMap<>();

    public static void changeState(BlockState state, BlockState newState) {
        blockStateMap.put(state, newState);
    }

    public static void removeState(BlockState state) {
        blockStateMap.remove(state);
    }

    public static void createDrop(BlockState state, List<ItemStack> drops) {
        blockMap.put(state, drops);
    }

    public static void removeDrop(BlockState state) {
        blockMap.remove(state);
    }

    public static List<ItemStack> getDrop(BlockState state, ServerLevel world, BlockPos pos, Player player, ItemStack heldItem, boolean createDropT) {
        if (blockMap.containsKey(state)) return blockMap.get(state);

        List<ItemStack> blockDrops = Block.getDrops(state, world, pos, null, player, heldItem);
        if (createDropT) createDrop(state, blockDrops);

        return blockDrops;
    }

    public static boolean isSeed(Item item) {
        return WeightedItems.cropSeeds.contains(item);
    }

    public static void dropItem(ItemStack itemStack, ServerLevel world, int blockx, int blocky, int blockz) {
        ItemEntity itemEntity = new ItemEntity(world, blockx, blocky, blockz, itemStack);
        world.addFreshEntity(itemEntity);
    }

    // TODO: Going to clean this up later
    public static void listenBlockBreak(BlockEvent.BreakEvent breakEvent) {
        Player player = breakEvent.getPlayer();
        Level playerWorld = player.level;
        ServerLevel serverWorld = (ServerLevel) playerWorld;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;

        BlockState state = breakEvent.getState();
        Block block = state.getBlock();
        BlockPos pos = breakEvent.getPos();

        int replenishLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.REPLENISH.get(), heldItem);
        int bountifulHarvestLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.BOUNTIFUL_HARVEST.get(), heldItem);
        int autoSmeltLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.AUTO_SMELT.get(), heldItem);
        int blastMasteryLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.BLAST_MASTERY.get(), heldItem);
        int farmersDelightLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.FARMERS_DELIGHT.get(), heldItem);
        int magnetismLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.MAGNETISM.get(), heldItem);
        int cropCompressorLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.CROP_COMPRESSOR.get(), heldItem);

        int quakeLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.QUAKE.get(), heldItem);
        int momentumLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.MOMENTUM.get(), heldItem);

        int wisdomLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.WISDOM.get(), heldItem);
        int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, heldItem);
        int silkTouchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, heldItem);

        NetherWartBlock netherWartBlock = block instanceof NetherWartBlock ? (NetherWartBlock) block : null;
        CropBlock cropBlock = block instanceof CropBlock ? (CropBlock) block : null;
        if (wisdomLevel >= 1) {
            if (block instanceof OreBlock) {
                int extraXp = block.getExpDrop(state, playerWorld, pos, fortuneLevel, silkTouchLevel) * wisdomLevel;
                player.giveExperiencePoints(extraXp);
            } else if (cropBlock != null && cropBlock.isMaxAge(state)) {
                player.giveExperiencePoints((int) (0.5f + (wisdomLevel * 0.1)));
            }
        }

        int blockX = (int) (pos.getX() + 0.5);
        int blockY = (int) (pos.getY() + 0.5);
        int blockZ = (int) (pos.getZ() + 0.5);
        if (autoSmeltLevel <= 0 && blastMasteryLevel <= 0 && replenishLevel <= 0 && bountifulHarvestLevel <= 0 && farmersDelightLevel <= 0 && quakeLevel <= 0) {
            if (magnetismLevel >= 1) {
                breakEvent.setCanceled(true);
                for (ItemStack itemDrop : getDrop(state, serverWorld, breakEvent.getPos(), player, heldItem, false)) {
                    if (itemDrop.getCount() > 0) {
                        if (!player.getInventory().add(itemDrop)) {
                            dropItem(itemDrop, serverWorld, blockX, blockY, blockZ);
                        }
                    }
                }

                BlockState newState = blockStateMap.containsKey(state) ? blockStateMap.get(state) : Blocks.AIR.defaultBlockState();
                playerWorld.setBlock(pos, newState, 3);

                if (blockStateMap.containsKey(state)) removeDrop(state);
                if (blockMap.containsKey(state)) removeState(state);
            }

            return;
        }

        if (netherWartBlock != null || cropBlock != null) {
            int currentAge = netherWartBlock != null ? state.getValue(NetherWartBlock.AGE) : 3;
            boolean isMaxAge = netherWartBlock != null ? currentAge == NetherWartBlock.MAX_AGE : cropBlock.isMaxAge(state);

            if (replenishLevel >= 1) {
                if (!isMaxAge) {
                    breakEvent.setCanceled(true);
                    return;
                }
                replenishEnchantment(breakEvent, replenishLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
            }

            if (bountifulHarvestLevel >= 1) {
                bountifulHarvestEnchantment(breakEvent, bountifulHarvestLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
            }

            if (farmersDelightLevel >= 1) {
                farmersDelightEnchantment(breakEvent, farmersDelightLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
            }

            if (cropCompressorLevel >= 1) {
                cropCompressorEnchantment(breakEvent, cropCompressorLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
            }
        }

        if (quakeLevel >= 1) {
            quakeEnchantment(breakEvent, quakeLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
        }

        if (momentumLevel >= 1) {
            momentumEnchantment(breakEvent, momentumLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
        }

        breakEvent.setCanceled(true);
        for (ItemStack itemDrop : getDrop(state, serverWorld, pos, player, heldItem, false)) {
            if (itemDrop.getCount() > 0) {
                if (magnetismLevel >= 1) {
                    if (!player.getInventory().add(itemDrop)) {
                        dropItem(itemDrop, serverWorld, blockX, blockY, blockZ);
                    }
                } else {
                    dropItem(itemDrop, serverWorld, blockX, blockY, blockZ);
                }
            }
        }

        BlockState newState = blockStateMap.containsKey(state) ? blockStateMap.get(state) : Blocks.AIR.defaultBlockState();
        playerWorld.setBlock(pos, newState, 3);

        if (blockMap.containsKey(state)) removeDrop(state);
        if (blockStateMap.containsKey(state)) removeState(state);
    }
}
