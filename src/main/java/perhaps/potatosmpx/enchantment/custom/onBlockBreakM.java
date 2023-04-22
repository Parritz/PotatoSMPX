package perhaps.potatosmpx.enchantment.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import perhaps.potatosmpx.api.registry.PlayerSkillBase;
import perhaps.potatosmpx.enchantment.ModEnchantments;

import java.util.*;

public class onBlockBreakM {
    private static final Map<BlockState, List<ItemStack>> blockMap = new HashMap<>();
    private static final Map<BlockState, BlockState> blockStateMap = new HashMap<>();

    private static void changeState(BlockState state, BlockState newState) {
        blockStateMap.put(state, newState);
    }

    private static void removeState(BlockState state) {
        blockStateMap.remove(state);
    }

    private static void createDrop(BlockState state, List<ItemStack> drops) {
        blockMap.put(state, drops);
    }

    private static void removeDrop(BlockState state) {
        blockMap.remove(state);
    }

    private static List<ItemStack> getDrop(BlockState state, ServerLevel world, BlockPos pos, Player player, ItemStack heldItem) {
        if (blockMap.containsKey(state)) return blockMap.get(state);

        List<ItemStack> tempDrops = Block.getDrops(state, world, pos, null, player, heldItem);
        createDrop(state, tempDrops);

        return tempDrops;
    }

    private static final List<Item> cropSeeds = Arrays.asList(
            Items.WHEAT_SEEDS,
            Items.CARROT,
            Items.POTATO,
            Items.BEETROOT_SEEDS,
            Items.PUMPKIN_SEEDS,
            Items.MELON_SEEDS
    );

    private static final Map<Item, Double> rareDrops = Map.of(
            Items.COAL, 0.25,
            Items.IRON_INGOT, 0.25,
            Items.GOLD_INGOT, 0.15,
            Items.REDSTONE, 0.15,
            Items.LAPIS_LAZULI, 0.15,
            Items.DIAMOND, 0.1,
            Items.EMERALD, 0.08,
            Items.NETHERITE_SCRAP, 0.02
    );

    public static boolean isSeed(Item item) {
        return cropSeeds.contains(item);
    }

    public static void dropItem(ItemStack itemStack, ServerLevel world, int blockx, int blocky, int blockz) {
        ItemEntity itemEntity = new ItemEntity(world, blockx, blocky, blockz, itemStack);
        world.addFreshEntity(itemEntity);
    }

    private static boolean isStoneBased(Block block) {
        // Add your own criteria for determining if a block is stone-based
        // Example: return block instanceof StoneBlock || block instanceof CobblestoneBlock;
        return true;
    }

    private static void breakBlocksInArea(Level world, BlockPos center, Player player) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);
                    Block block = state.getBlock();

                    if (isStoneBased(block)) {
                        world.destroyBlock(pos, true, player);
                    }
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

        BlockState state = breakEvent.getState();
        Block block = state.getBlock();
        BlockPos pos = breakEvent.getPos();

        int replenishLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.REPLENISH.get(), heldItem);
        int bountifulHarvestLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.BOUNTIFUL_HARVEST.get(), heldItem);
        int autoSmeltLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.AUTO_SMELT.get(), heldItem);
        int blastMasteryLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.BLAST_MASTERY.get(), heldItem);
        int farmersDelightLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.FARMERS_DELIGHT.get(), heldItem);
        int magnetismLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.MAGNETISM.get(), heldItem);
        int quakeLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.QUAKE.get(), heldItem);

        int wisdomLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.WISDOM.get(), heldItem);
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
                for (ItemStack itemDrop : getDrop(state, serverWorld, breakEvent.getPos(), player, heldItem)) {
                    if (itemDrop.getCount() > 0) {
                        if (!player.getInventory().add(itemDrop)) {
                            dropItem(itemDrop, serverWorld, blockX, blockY, blockZ);
                        }
                    }
                }

                removeDrop(state);
            }

            return;
        }

        if (netherWartBlock != null || cropBlock != null) {
            int currentAge = netherWartBlock != null ? state.getValue(NetherWartBlock.AGE) : 3;
            boolean isMaxAge = netherWartBlock != null ? currentAge == NetherWartBlock.MAX_AGE : cropBlock.isMaxAge(state);

            if (replenishLevel >= 1) {
                if (isMaxAge) {
                    replenishEnchantment(breakEvent, replenishLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
                } else {
                    breakEvent.setCanceled(true);
                    return;
                }
            }

            if (bountifulHarvestLevel >= 1) {
                bountifulHarvestEnchantment(breakEvent, bountifulHarvestLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
            }

            if (farmersDelightLevel >= 1) {
                farmersDelightEnchantment(breakEvent, farmersDelightLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
            }
        }

        if (quakeLevel >= 1) {
            quakeEnchantment(breakEvent, quakeLevel, heldItem, state, block, serverWorld, playerWorld, pos, player);
        }

        breakEvent.setCanceled(true);
        for (ItemStack itemDrop : getDrop(state, serverWorld, breakEvent.getPos(), player, heldItem)) {
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

        if (blockStateMap.containsKey(state)) removeDrop(state);
        if (blockMap.containsKey(state)) removeState(state);
    }

    private static void replenishEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
        int greenThumbLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.GREEN_THUMB.get(), heldItem);

        List<ItemStack> blockDrops = getDrop(state, serverWorld, pos, player, heldItem);

        NetherWartBlock netherWartBlock = block instanceof NetherWartBlock ? (NetherWartBlock) block : null;
        CropBlock cropBlock = block instanceof CropBlock ? (CropBlock) block : null;

        BlockState seedBlockState = block.defaultBlockState();
        if (greenThumbLevel >= 1) {
            int maxAge = netherWartBlock != null ? NetherWartBlock.MAX_AGE : cropBlock != null ? cropBlock.getMaxAge() : 0;
            int stageBoost = (int) (0.5 * greenThumbLevel * maxAge);
            int newAge = Math.min(stageBoost, maxAge - 1);
            if (maxAge > 0) {
                seedBlockState = seedBlockState.setValue(netherWartBlock != null ? NetherWartBlock.AGE : cropBlock.getAgeProperty(), newAge);
            }
        }

        changeState(state, seedBlockState);
    }

    private static void bountifulHarvestEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
        List<ItemStack> blockDrops = getDrop(state, serverWorld, pos, player, heldItem);

        for (ItemStack drop : blockDrops) {
            if (drop.getItem() != Items.NETHER_WART && isSeed(drop.getItem())) { continue; } // Skip any seeds
            int additionalCount = (drop.getCount() * level);
            drop.setCount(drop.getCount() + additionalCount);
        }
    }

    private static void farmersDelightEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
        List<ItemStack> blockDrops = getDrop(state, serverWorld, pos, player, heldItem);

        float runChance = 0.05f * level * (PlayerSkillBase.getLuck(player) / 100.0f);
        if (playerWorld.getRandom().nextFloat() > 0.999999) return;

        double randomValue = playerWorld.getRandom().nextDouble();
        double totalWeight = rareDrops.values().stream().mapToDouble(Double::doubleValue).sum();
        Item selectedItem = null;

        for (Map.Entry<Item, Double> entry : rareDrops.entrySet()) {
            double baseDropRate = entry.getValue();
            double adjustedDropRate = PlayerSkillBase.getAdjustedDropRate(player, baseDropRate) * totalWeight;
            if (randomValue < adjustedDropRate / totalWeight) {
                selectedItem = entry.getKey();
                break;
            }
            randomValue -= adjustedDropRate / totalWeight;
        }

        // Create an ItemStack with the random ore
        ItemStack oreStack = new ItemStack(selectedItem);
        oreStack.setCount(1);
        System.out.println(oreStack.getItem());

        // Add the ore to the drops
        blockDrops.add(oreStack);
    }

    private static void quakeEnchantment(BlockEvent.BreakEvent event, int level, ItemStack heldItem, BlockState state, Block block, ServerLevel serverWorld, Level playerWorld, BlockPos pos, Player player) {
        if (playerWorld.getRandom().nextFloat() > (0.1f * level)) return;

        if (isStoneBased(block)) {
            breakBlocksInArea(playerWorld, pos, player);
        }
    }
}
