package perhaps.potatosmpx.api.onBlockBreak;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.config.CropHandler;
import perhaps.potatosmpx.api.config.WeightedItems;
import perhaps.potatosmpx.api.onBlockBreak.listeners.EnchantmentData;
import perhaps.potatosmpx.api.onBlockBreak.listeners.EnchantmentFunction;
import perhaps.potatosmpx.api.registry.EnchantmentBase;

import java.util.*;

import static perhaps.potatosmpx.api.onBlockBreak.listeners.EnchantmentData.onBlockBreakEnchantments;

public class OnBlockBreak {
    private static final Map<Enchantment, EnchantmentData> enchantmentMapPriority = new HashMap<>();
    private static final Map<Enchantment, EnchantmentData> enchantmentMap = new HashMap<>();
    private static final Map<Enchantment, EnchantmentData> afterDropMap = new HashMap<>();

    private static final Map<Enchantment, EnchantmentData> combinedMap = new LinkedHashMap<>();

    private static final Map<BlockPos, List<ItemStack>> blockMap = new HashMap<>();
    private static final Map<BlockPos, BlockState> blockStateMap = new HashMap<>();

    public static void changeState(BlockPos pos, BlockState newState) {
        blockStateMap.put(pos, newState);
    }

    public static void removeState(BlockPos pos) {
        blockStateMap.remove(pos);
    }

    public static void createDrop(BlockPos pos, List<ItemStack> drops) {
        blockMap.put(pos, drops);
    }

    public static void removeDrop(BlockPos pos) {
        blockMap.remove(pos);
    }

    public static List<ItemStack> getDrop(BlockState state, ServerLevel world, BlockPos pos, Player player, ItemStack heldItem, boolean createDropT) {
        if (blockMap.containsKey(pos)) return blockMap.get(pos);

        List<ItemStack> blockDrops = Block.getDrops(state, world, pos, null, player, heldItem);
        if (createDropT) createDrop(pos, blockDrops);

        return blockDrops;
    }

    public static boolean isSeed(Item item) {
        return WeightedItems.cropSeeds.contains(item);
    }

    public static void dropItem(ItemStack itemStack, ServerLevel world, int blockx, int blocky, int blockz) {
        ItemEntity itemEntity = new ItemEntity(world, blockx, blocky, blockz, itemStack);
        world.addFreshEntity(itemEntity);
    }

    public static void addItems(List<ItemStack> drops, Player player, boolean magnetism, ServerLevel serverWorld, int blockX, int blockY, int blockZ) {
        for (ItemStack itemDrop : drops) {
            if (itemDrop.getCount() > 0) {
                boolean success = magnetism ? player.getInventory().add(itemDrop) : false;
                if (success) {
                    dropItem(itemDrop, serverWorld, blockX, blockY, blockZ);
                }
            }
        }
    }
    public static void listenBlockBreak(BlockEvent.BreakEvent breakEvent) {
        Player player = breakEvent.getPlayer();
        Level playerWorld = player.level;
        ServerLevel serverWorld = (ServerLevel) playerWorld;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;
        if (enchantmentMap.isEmpty()) {
            for (EnchantmentData data : onBlockBreakEnchantments) {
                int priority = data.getPriority();
                if (priority == 0) continue;

                Enchantment enchantment = data.getEnchantment();

                if (priority == 1) {
                    enchantmentMapPriority.put(enchantment, data);
                } else if (priority == 2) {
                    enchantmentMap.put(enchantment, data);
                } else {
                    afterDropMap.put(enchantment, data);
                }
            }

            combinedMap.putAll(enchantmentMapPriority);
            combinedMap.putAll(enchantmentMap);
        }

        BlockState state = breakEvent.getState();
        Block block = state.getBlock();
        BlockPos pos = breakEvent.getPos();

        int replenishLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.REPLENISH.get(), heldItem);
        if (replenishLevel >= 1 && CropHandler.isCrop(block) && !CropHandler.isMaxAge(state)) {
            breakEvent.setCanceled(true);
            return;
        }

        int enchantmentsPassed = 0;
        for (Map.Entry<Enchantment, EnchantmentData> entry : combinedMap.entrySet()) {
            Enchantment enchantment = entry.getKey();
            EnchantmentData data = entry.getValue();

            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, heldItem);
            if (enchantmentLevel == 0) continue;

            List<Class<? extends Block>> ValidBlocks = data.getValidBlocks();

            boolean isValidBlock = ValidBlocks.isEmpty();
            for (Class<? extends Block> validBlockClass : ValidBlocks) {
                if (validBlockClass.isInstance(block)) {
                    isValidBlock = true;
                    break;
                }
            }

            if (!isValidBlock) continue;
            enchantmentsPassed++;

            EnchantmentFunction function = data.getFunction();
            function.apply(breakEvent, enchantmentLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
        }

        int magnetismLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentBase.MAGNETISM.get(), heldItem);

        if (enchantmentsPassed <= 0) return;
        int blockX = (int) (pos.getX() + 0.5);
        int blockY = (int) (pos.getY() + 0.5);
        int blockZ = (int) (pos.getZ() + 0.5);

        breakEvent.setCanceled(true);
        addItems(getDrop(state, serverWorld, pos, player, heldItem, false), player, magnetismLevel >= 1, serverWorld, blockX, blockY, blockZ);

        BlockState newState = blockStateMap.containsKey(pos) ? blockStateMap.get(pos) : Blocks.AIR.defaultBlockState();
        playerWorld.setBlock(pos, newState, 3);

        if (blockMap.containsKey(pos)) removeDrop(pos);
        if (blockStateMap.containsKey(pos)) removeState(pos);

        for (Map.Entry<Enchantment, EnchantmentData> entry : afterDropMap.entrySet()) {
            Enchantment enchantment = entry.getKey();
            EnchantmentData data = entry.getValue();

            int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, heldItem);
            if (enchantmentLevel == 0) continue;

            List<Class<? extends Block>> ValidBlocks = data.getValidBlocks();

            boolean isValidBlock = ValidBlocks.isEmpty();
            for (Class<? extends Block> validBlockClass : ValidBlocks) {
                if (validBlockClass.isInstance(block)) {
                    isValidBlock = true;
                    break;
                }
            }

            if (!isValidBlock) continue;

            EnchantmentFunction function = data.getFunction();
            function.apply(breakEvent, enchantmentLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
        }
    }
}
