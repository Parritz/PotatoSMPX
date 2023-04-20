package perhaps.potatosmpx.enchantment.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import perhaps.potatosmpx.enchantment.EnchantmentRarity;
import perhaps.potatosmpx.enchantment.ModEnchantments;

public class ReplenishEnchantment extends Enchantment {
    public ReplenishEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.COMMON.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.COMMON.getMaxCost(level); }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) return;

        int level = EnchantmentHelper.getItemEnchantmentLevel(this, heldItem);
        int bountifulHarvestLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.BOUNTIFUL_HARVEST.get(), heldItem);
        int greenThumbLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.GREEN_THUMB.get(), heldItem);
        if (level <= 0 || world.isClientSide) return;

        BlockState state = event.getState();
        Block block = state.getBlock();

        // Check if the block is a CropBlock and fully grown
        if (!(block instanceof CropBlock cropBlock)) return;
        if (!cropBlock.isMaxAge(state)) {
            event.setCanceled(true);
            return;
        }

        List<ItemStack> blockDrops = Block.getDrops(state, (ServerLevel) world, event.getPos(), null, player, heldItem);

        boolean foundSeeds = false;
        boolean removedSeed = false;
        Item seed = null;

        // Iterate through the drops to find the seed item
        for (ItemStack itemDrop : blockDrops) {
            if (itemDrop.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof CropBlock) {
                seed = itemDrop.getItem();
                foundSeeds = true;
                break;
            }
        }

        if (!foundSeeds) {
            return;
        }

        event.setCanceled(true); // Cancel the default block breaking event

        // Remove the original block without dropping items
        world.setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);

        // Spawn the drops except for one seed
        BlockPos blockPosition = event.getPos();
        int blockX = (int) (blockPosition.getX() + 0.5);
        int blockY = (int) (blockPosition.getY() + 0.5);
        int blockZ = (int) (blockPosition.getZ() + 0.5);

        for (ItemStack itemDrop : blockDrops) {
            if (itemDrop.getItem() == seed) {
                if (!removedSeed && world.getRandom().nextFloat() > (0.1f * level)) {
                    removedSeed = true;
                    itemDrop.setCount(itemDrop.getCount() - 1);
                }
            } else if (bountifulHarvestLevel > 0) {
                itemDrop.setCount((int) (itemDrop.getCount() + (double) (itemDrop.getCount() * bountifulHarvestLevel)));
            }

            if (itemDrop.getCount() > 0) {
                ItemEntity itemEntity = new ItemEntity(world, blockX, blockY, blockZ, itemDrop);
                world.addFreshEntity(itemEntity);
            }
        }

        // Plant the seed back
        BlockPos pos = event.getPos();
        BlockState seedBlockState = block.defaultBlockState();

        if (greenThumbLevel >= 1) {
            int maxAge = cropBlock.getMaxAge();
            int stageBoost = (int) (0.5 * level * maxAge);
            int newAge = Math.min(stageBoost, maxAge - 1);
            seedBlockState = seedBlockState.setValue(cropBlock.getAgeProperty(), newAge);
        }

        world.setBlock(pos, seedBlockState, 3);
    }
}
